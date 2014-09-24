
var $tr = $("<tr/>").attr('class', 'remove');
var $td = $("<td/>");
var $fg = $("<div/>").attr('class', 'form-group');
var $lbl = $("<label/>").attr('class', 'col-sm-3 control-label');
var $disInp = $("<input/>").attr('class', 'form-control').attr('type', 'text').attr('readonly', 'true').attr('id', 'inputId');
var $btnDelete = $("<button/>").attr('class', 'btn btn-danger').attr('type', 'button').text('Delete');
var $btnEdit = $("<button/>").attr('class', 'btn btn-warning').attr('type', 'button').text('Edit');
var $btnBoard = $("<button/>").attr('class', 'btn btn-primary').attr('type', 'button').text('Board');
var $btnPassenger = $("<button/>").attr('class', 'btn btn-primary').attr('type', 'button').text('Passengers');
var $div = $('<div/>');

function findTrips() {
    var pathId = $('#pathSelect').find("option:selected").val();
    var trainId = $('#trainSelect').find("option:selected").val();
    $.post('/SBB/trip/select',
        { pathId: pathId, trainId: trainId},
        function (response) {
            if (isError(response)) {
                findTrips();
                return;
            }
            var trips = $.parseJSON(response);
            $('.remove').remove();
            $.each(trips, function (index, trip) {
                appendRow(trip);
            });
        }
    );
}

function checkFields(trainId, pathId) {
    if (sbb_debug_js_validation_off != undefined) {
        return true;
    }

    function showError(addedText) {
        BootstrapDialog.alert({
            title: 'Error',
            message: 'You must choose ' + addedText + ' !',
            type: BootstrapDialog.TYPE_DANGER
        });
    }

    if (trainId == 0 && pathId == 0) {
        showError('train and path');
    } else if (trainId == 0) {
        showError('train');
    } else if (pathId == 0) {
        showError('path');
    } else {
        return true;
    }
    return false;
}
function addTrip() {
    var pathId = $('#pathSelect').find("option:selected").val();
    var trainId = $('#trainSelect').find("option:selected").val();
    if (!checkFields(trainId, pathId)) {
        return;
    }
    $.post('/SBB/trip/add',
        { pathId: pathId, trainId: trainId},
        function (response) {
            if (isError(response)) {
                findTrips();
                return;
            }
            var trip = $.parseJSON(response);
            appendRow(trip);
        }
    );
}

function createRow(trip) {
    var row = $tr.clone().attr("id", trip.id);
    $.each(trip, function (key, value) {
        row.append($td.clone().attr('class', key).text(value));
    });
    row.append($td.clone().html($btnBoard.clone().click(function () {
        toBoard(trip.id);
    })));
    row.append($td.clone().html($btnPassenger.clone().click(function () {
        toPassengers(trip.id);
    })));
    row.append($td.clone().html($btnEdit.clone().click(function () {
        editTrip(trip.id);
    })));
    row.append($td.clone().html($btnDelete.clone().click(function () {
        removeTrip(trip.id);
    })));
    return row;
}

function appendRow(trip) {
    var row = createRow(trip);
    $('#listOfTrips').append(row);
}

function removeTrip(tripId) {
    BootstrapDialog.confirm("Do you confirm removing trip with id " + tripId + "?", function (result) {
        if (result) {
            var lci = $('#' + tripId + ' .lastChange').text();
            $.post('/SBB/trip/delete',
                {tripId: tripId, lci: lci},
                function (response) {
                    //dialog.close();
                    if (isError(response)) {
                        findTrips();
                        return;
                    }
                    $('#' + tripId).remove();
                });
        }
    });
}

function editTrip(tripId) {
    var $content = $div.clone();
    initEditForm($content, tripId);
    BootstrapDialog.show({
        title: 'Edit trip',
        message: $content,
        buttons: [
            {
                label: 'Edit',
                action: function (dialog) {
                    var trip = {};
                    trip.id = tripId;
                    trip.pathId = $('#modalPS').find("option:selected").val();
                    trip.trainId = $('#modalTS').find("option:selected").val();
                    trip.lastChange = $('#' + tripId + ' .lastChange').text();
                    $.post('/SBB/trip/edit',
                        {trip: JSON.stringify(trip)},
                        function (response) {
                            dialog.close();
                            if (isError(response)) {
                                findTrips();
                                return;
                            }
                            BootstrapDialog.alert('Update success!');
                            $('#pathSelect').find("[value='" + trip.pathId + "']").attr('selected', true);
                            $('#trainSelect').find("[value='" + trip.trainId + "']").attr('selected', true);
                            findTrips();
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

function initEditForm($content, tripId) {
    var $tmpInp = createROInput(tripId);
    var $tmpLbl = createLabel('inputId', 'Trip ID');
    $content.append($fg.clone().append($tmpLbl).append($tmpInp));
    var trainId = $('#' + tripId + ' .trainId').text();
    $tmpLbl = createLabel('modalTS', 'Train');
    var $tmpSel = copySelect('trainSelect', 'modalTS');
    selectOption($tmpSel, trainId);
    $content.append($fg.clone().append($tmpLbl).append($tmpSel));
    var pathId = $('#' + tripId + ' .pathId').text();
    $tmpLbl = createLabel('modalPS', 'Path');
    $tmpSel = copySelect('pathSelect', 'modalPS');
    selectOption($tmpSel, pathId);
    $content.append($fg.clone().append($tmpLbl).append($tmpSel));
}

function createROInput(innerText) {
    return $disInp.clone().val(innerText);
}

function createLabel(attrFor, innerText) {
    return $lbl.clone().attr('for', '#' + attrFor).text(innerText);
}

function selectOption($selectObj, value) {
    $selectObj.find("[value='" + value + "']").attr('selected', true);
}

function copySelect(selectId, newId) {
    return $('#' + selectId).clone().attr('id', newId).attr('onchange', '');
}

function toBoard(tripId) {
    location.href = '/SBB/trip/board/' + tripId;
}

function toPassengers(tripId) {
    location.href = '/SBB/trip/passengers/' + tripId;
}
