$(document).ready(function () {
    $('#getBoardBtn').click(function () {
//        alert('Ok');
        getBoard();
    });
});

function getBoard() {
    var stationId = $('#stationList').find('option:selected').val();
    var date = $('#date').val();
    if (date === "") {
        date = formatDateToString(new Date());
    }
    $.post('/SBB/board/get',
        {stationId: stationId, date: date},
        function (response) {
            response = response.trim();
            if (isError(response)) {
                return;
            }
            $('#board').slideDown('fast');
            $('#boardTable').append(response);
        }
    );
}