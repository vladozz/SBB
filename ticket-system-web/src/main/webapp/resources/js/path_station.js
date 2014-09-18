/**
 * Created by Vlad on 13.09.2014.
 */
function addStationToPath() {
    var pathId = $('#pathSelect').find('option:selected').attr('value');
    var stationId = $('#inputLogin').find('option:selected').attr('value');
    var stationBeforeInsertId = $('#inputIndex').find('option:selected').attr('value');
    var lci = $('#lci').text();
    $.post('/SBB/path/stations',
    {
        pathId: pathId,
        stationId: stationId,
        stationBeforeInsertId: stationBeforeInsertId,
        lci: lci
    },
        function (data) {
            if (data == "false") {
                BootstrapDialog.show({
                    title: 'Update error!',
                    message: 'You have irrelevant version of page. Please reload the page and try again',
                    buttons: [
                        {
                            label: 'Reload',
                            action: function () {
                                location.reload();
                            }
                        }
                    ]
                });
            } else {
                BootstrapDialog.show({
                    title: 'Update success!',
                    message: 'Update success',
                    buttons: [
                        {
                            label: 'OK',
                            action: function (dialog) {
                                moveOptionAdd(stationId, stationBeforeInsertId);
                                dialog.close();
                            }
                        }
                    ]
                });
            }
        });
}

function go() {
    var pathId = $("#pathSelect").find("option:selected").attr('value');
    location.href = '/SBB/path/stations/' + pathId;
}

function toggleAddForm() {
    $('#addForm').slideToggle('fast');
}

function removeStationFromPath(pathId, stationId) {
    var lci = $('#lci').text();
    $.post('/SBB/path/stations/remove', { pathId: pathId, stationId: stationId, lci: lci }, function (data) {
        if (data == "false") {
            BootstrapDialog.show({
                title: 'Update error!',
                message: 'You have irrelevant version of page. Please reload the page and try again',
                buttons: [
                    {
                        label: 'Reload',
                        action: function () {
                            location.reload();
                        }
                    }
                ]
            });
        } else {
            BootstrapDialog.show({
                title: 'Update success!',
                message: 'Update success',
                buttons: [
                    {
                        label: 'OK',
                        action: function (dialog) {
                            moveOptionDelete(stationId);
                            dialog.close();
                        }
                    }
                ]
            });
        }
    });
}

function confirmRemoveStationFromPath(pathId, station, stationId) {
    BootstrapDialog.show({
        title: 'Confirm removing',
        message: 'Do you confirm removing station ' + station + ' from path with id: ' + pathId + '?',
        buttons: [
            {
                label: 'Confirm',
                action: function (dialog) {
                    removeStationFromPath(pathId, stationId);
                    dialog.close();
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

function moveOptionAdd (stationId, stationBeforeInsertId) {
    var option = $('#inputLogin').find("[value='" + stationId + "']");
    var optionBefore = $('#inputIndex').find("[value='" + stationBeforeInsertId + "']");
    optionBefore.before(option.clone());
    var stationTitle = option.text();
    option.remove();
    $('#inputIndex').find("[value='0']").attr('selected', true);
    var newRow = createRow(stationId, stationTitle);
    if (stationBeforeInsertId == 0) {
        $('#listOfStations').append(newRow);
    } else {
        $('#' + stationBeforeInsertId).before(newRow);
    }
    $('#lci').text(parseInt($('#lci').text()) + 1);
}

function createRow(stationId, stationTitle) {
    var pathId = $('#pathId').text();
    var html = "<tr id=\"" + stationId + "\"><td class=\"id\">" + stationId + "</td><td class=\"title\">" + stationTitle + "</td>" +
        "<td class=\"remove\"><button class=\"btn btn-warning\" " +
        "onclick=\"confirmRemoveStationFromPath("  + pathId + ", '" + stationTitle + "' , " + stationId +
        ");\">Remove</button></td></tr>";
    return html;
}

function moveOptionDelete(stationId) {
    var option = $('#inputIndex').find("[value='" + stationId + "']");

    $('#inputLogin').find("option").each(function () {
        if ($(this).text() > option.text()) {
            $(this).before(option.clone());
            return false;
        }
    });
    option.remove();
    $('#' + stationId).remove();

    $('#lci').text(parseInt($('#lci').text()) + 1);

}