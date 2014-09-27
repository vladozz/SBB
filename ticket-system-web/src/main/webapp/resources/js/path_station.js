/**
 * Created by Vlad on 13.09.2014.
 */
function addStationToPath() {
    var $inputStation = $('#inputStation');
    if ($inputStation.html().trim() === '') {
        popupError('No station to add');
        return;
    }

    var pathId = $('#pathSelect').find('option:selected').attr('value');
    var stationId = $inputStation.find('option:selected').attr('value');
    var stationBeforeInsertId = $('#inputIndex').find('option:selected').attr('value');
    var lci = $('#lci').text();

    $.ajax({
        type: "post",
        url: '/SBB/path/stations',
        data: {
            pathId: pathId,
            stationId: stationId,
            stationBeforeInsertId: stationBeforeInsertId,
            lci: lci
        },

        success: function (response) {
            operSuccess();
            if (stationBeforeInsertId == 0) {
                $('#listOfStations').find('tbody').append(response);
            } else {
                $('#' + stationBeforeInsertId).before(response);
            }
            moveOptionAdd(stationId, stationBeforeInsertId);
        },
        error: function (jdXHR) {
            if (jdXHR.status != 400) {
                $('.close').click();
                $('#close').click();
            }
            popupError(jdXHR.status + " " + jdXHR.statusText);
        }});
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
    $.ajax({
        type: 'post',
        url: '/SBB/path/stations/remove',
        data: { pathId: pathId, stationId: stationId, lci: lci },
        success: function () {
            operSuccess();
            moveOptionDelete(stationId);
        },
        error: function (jdXHR) {
            popupError(jdXHR.status + " " + jdXHR.statusText);
        }});
}

function confirmRemoveStationFromPath(stationId) {
    var pathId = $('#pathId').text();
    var station = $('#' + stationId).find('.title').val();
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

function moveOptionAdd(stationId, stationBeforeInsertId) {
    var option = $('#inputStation').find("[value='" + stationId + "']");
    var $inputIndex = $('#inputIndex');
    var optionBefore = $inputIndex.find("[value='" + stationBeforeInsertId + "']");
    optionBefore.before(option.clone());
    var stationTitle = option.text();
    option.remove();
    $inputIndex.find("[value='0']").attr('selected', true);
    var newRow = createRow(stationId, stationTitle);

    var $lci = $('#lci');
    $lci.text(parseInt($lci.text()) + 1);
}

function createRow(stationId, stationTitle) {
//    var pathId = $('#pathId').text();
    var html = "<tr id=\"" + stationId + "\"><td class=\"id\">" + stationId + "</td><td class=\"title\">" + stationTitle + "</td>" +
        "<td><button class=\"btn btn-warning\" " +
        "onclick=\"confirmRemoveStationFromPath(" + stationId + ");\">Remove</button></td></tr>";
    return html;
}

function moveOptionDelete(stationId) {

    var option = $('#inputIndex').find("[value='" + stationId + "']");

    var $inputStation = $('#inputStation');
    if ($inputStation.html().trim() === '') {
        $inputStation.append(option.clone());
    } else {
        var added = false;
        $inputStation.find("option").each(function () {
            if ($(this).text() > option.text()) {
                $(this).before(option.clone());
                added = true;
                return false;
            }
            if (!added) {
                $inputStation.append(option.clone());
            }
        });
    }
    option.remove();
    $('#' + stationId).remove();

    var $lci = $('#lci');
    $lci.text(parseInt($lci.text()) + 1);

}