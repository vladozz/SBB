$(document).ready(function () {
        $('#dateBegin').val(formatDateToString(new Date()));
        $('#dateEnd').val(formatDateToString(new Date()));
        $('#timeBegin').val("00:00");
        $('#timeEnd').val("23:59");

        $('#getBoardBtn').click(function () {
            getTrips();
        });
    }
);


function getTrips() {
    var dateBegin = readDateInput($('#dateBegin'));
    var dateEnd = readDateInput($('#dateEnd'));
    var timeBegin = readTimeInput($('#timeBegin'), "00:00");
    var timeEnd = readTimeInput($('#timeEnd'), "23:59");
    var stationBegin = $('#stationBegin').find('option:selected').val();
    var stationEnd = $('#stationEnd').find('option:selected').val();
    if (stationBegin == stationEnd) {
        popupError('You choose different departure and arrive stations ');
        return;
    }

    $.post('/SBB/reqtrip/get',
        {departureStationId: stationBegin, departureDate: dateBegin, departureTime: "00:00",
            arriveStationId: stationEnd, arriveDate: dateEnd, arriveTime: "23:59"},
        function (response) {
            response = response.trim();
            if (isError(response)) {
                return;
            }
            $('.remove').remove();
            if (response === '') {
                popupInfo('No trips found');
            } else {
                $('#tripTable').append(response);
            }
        }
    );
}

function buyTicketDialog(departureId, arriveId, freePlaces) {
    if (freePlaces == 0) {
        popupError("This trip is out of free places!");
        return;
    }
    var $passengerForm = $('#passengerForm').clone().removeAttr('id');
    BootstrapDialog.show({
        title: 'Buy a ticket',
        message: $passengerForm,
        buttons: [
            {
                label: 'OK',
                action: function (dialog) {
                    var passenger = makeData($passengerForm);
                    $.post('/SBB/ticket/buy',
                        {firstName: passenger.firstName, lastName: passenger.lastName, birthDate: passenger.birthDate,
                            departureBoardId: departureId, arriveBoardId: arriveId, email: passenger.email},
                        function(response) {
                            if (!isError(response)) {
                                dialog.setMessage(response);
                                popupSuccess('You\'ve successfully bought ticket!');
                            }
                        }
                    ).error(function(jdXHR) {
                            popupjdXHRError(jdXHR);
                        }
                    );

                }
            },
            {
                label: 'Cancel',
                action: function (dialog) {
                    dialog.close();
                }
            }
        ]
    });
}

function makeData($passengerForm) {
    var boardTripDTO = {};
    var firstName = $passengerForm.find('#inputFN').val();
    var lastName = $passengerForm.find('#inputLN').val();
    var dateInput = $passengerForm.find('#inputDate');
    var inputEmail = $passengerForm.find('#inputEmail');
    var email = inputEmail.val();

    var birthDate = dateInput.val();
    if (birthDate === '') {
        popupError('Invalid date of birth');
        dateInput.focus();
        return;
    }

    if (email.length > 0 && !emailRegex.test(email)) {
        popupError("Invalid email");
        inputEmail.val('');
        inputEmail.focus();
        return;
    }

    birthDate = cellsToDate(birthDate);
    boardTripDTO.firstName = firstName;
    boardTripDTO.lastName = lastName;
    boardTripDTO.birthDate = birthDate;
    boardTripDTO.email = email;
    return boardTripDTO;
}
