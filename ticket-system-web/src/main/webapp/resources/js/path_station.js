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
    var version = $('#version').text();

    $.ajax({
        type: "post",
        url: '/SBB/path/stations',
        data: {
            pathId: pathId,
            stationId: stationId,
            stationBeforeInsertId: stationBeforeInsertId,
            version: version
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
    var version = $('#version').text();
    $.ajax({
        type: 'post',
        url: '/SBB/path/stations/remove',
        data: { pathId: pathId, stationId: stationId, version: version },
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

    var $version = $('#version');
    $version.text(parseInt($version.text()) + 1);
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

    var $version = $('#version');
    $version.text(parseInt($version.text()) + 1);

}