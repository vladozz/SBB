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

    $.post('/SBB/reqtrip/get',
        {departureStationId: stationBegin, departureDate: dateBegin, departureTime: "00:00",
            arriveStationId: stationEnd, arriveDate: dateEnd, arriveTime: "23:59"},
        function (response) {
            response = response.trim();
            if (isError(response)) {
                return;
            }
            $('.remove').remove();
            $('#tripTable').append(response);
        }
    );
}