$(document).ready(function () {
    //$('#date').val(formatDateToString(new Date()));

    $('#getBoardBtn').click(function () {
//        alert('Ok');
        getBoard();
    });
});

function getBoard() {

    var date = readDateInput($('#date'));
    var stationId = $('#stationList').find('option:selected').val();
    $.post('/SBB/board/get',
        {stationId: stationId, date: date},
        function (response) {
            response = response.trim();
            if (isError(response)) {
                return;
            }
            $('#board').slideDown('fast');
            $('.remove').remove();
            $('#boardTable').append(response);
        }
    );
}