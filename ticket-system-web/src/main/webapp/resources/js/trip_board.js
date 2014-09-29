var $tr = $("<tr/>").attr('class', 'remove');
var $td = $("<td/>");
var $input = $("<input/>").attr('class', 'form-control');
var $hidden = $("<div/>").attr('style', 'display: none');
var idArray = [];
var mutable = ['departureDate', 'departureTime', 'arriveDate', 'arriveTime'];

$(document).ready(
    function () {
        getBoard();
    }
);

function createBoard() {
    var tripId = $('#tripId').text();
    var lci = $('#lci').text();

    var $defaultDate = $('#defaultDate');
    var date = $defaultDate.val();
    if (date === "") {
        date = formatDateToString(new Date());
        $defaultDate.val(date);
    }
    $.post('/SBB/trip/board/create',
        {tripId: tripId, lci: lci, date: date},
        function (response) {
            var board = $.parseJSON(response);
            fillBoardTable(board);
        }
    ).error(function(jdXHR) {
            popupjdXHRError(jdXHR);
        });
}

function fillBoardTable(board) {
    $('.remove').remove();
    idArray = [];
    $.each(board, function (index, boardLine) {
        $('#boardTable').append(createRow(boardLine));
    });
}
function getBoard() {
    var tripId = $('#tripId').text();

    $.post('/SBB/trip/board/select',
        {tripId: tripId},
        function (response) {
            var board = $.parseJSON(response);
            fillBoardTable(board);
        }
    ).error(function(jdXHR) {
            popupjdXHRError(jdXHR);
        });
}

function createRow(boardLine) {
    var boardId = boardLine.boardId;
    var row = $tr.clone().attr("id", boardId);

    idArray.push(boardId);

    $.each(boardLine, function (key, value) {
        var $cell = $td.clone();
        if (key == 'minuteOffset') {
            $cell.text(getTZ(value)).append($hidden.clone().addClass(key).text(value));
        } else {
            $cell.attr('class', key).text(value);
        }
        row.append($cell);
        if ($.inArray(key, mutable) != -1) {
            addClickListener($cell);
        }
    });
    return row;

    function getTZ(value) {
        return 'GMT' + (value >= 0 ? '+' : '') + value / 60;
    }
}

function addClickListener($cell) {

    $cell.unbind();
    $cell.click(function () {
        var $inp = makeInputCell($cell);
        $cell.html($inp);
        $inp.focus();
    });
}


function makeInputCell($cell) {
    var boardId = parseInt($cell.parent().attr('id'));
    var key = $cell.attr('class');
    var memText = $cell.text();
    var $inp = $input.clone().val(memText);
    $cell.unbind();
    var hardHack = 0;
    var depAr;
    var func;
    var isTime = key.indexOf('Time');
    if (isTime > 0) {
        $inp.attr('type', 'time');
        func = syncTime;
        depAr = key.substring(0, isTime);
    }
    var isDate = key.indexOf('Date');
    if (isDate > 0) {
        $inp.attr('type', 'date');
        func = syncDate;
        depAr = key.substring(0, isDate);
    }
    $cell.keypress(function (event) {
        if (event.which == 13) {
            hardHack++;
            func(boardId, depAr, memText);
        }
    });
    $cell.focusout(function () {
            if (hardHack == 0) {
                func(boardId, depAr, memText);
            }
        }
    );
    return $inp;
}

var syncDate = function (boardId, depAr, memText) {
    var key = depAr + 'Date';
    var $cell = $('#' + boardId + ' .' + key);
    var $inp = $cell.find('input');
    var newText = $inp.val();
    if (newText == '') {
        popupError("Invalid date");
        newText = memText;
    }
    $cell.text(newText);
    addClickListener($cell);
    $('#' + boardId + " ." + depAr + "Time").click();
};

var syncTime = function (boardId, depAr, memText) {
    var key = depAr + 'Time';
    var $cell = $('#' + boardId + ' .' + key);
    var $inp = $cell.find('input');
    var newText = $inp.val();
    if (newText == '') {
        popupError("Invalid time");
        newText = memText;
    }
    $cell.text(newText);
    addClickListener($cell);
    var errMes = validate(boardId, depAr);
    if (errMes !== '') {
        popupNoTimeoutError(errMes);
        $cell.css({'color': 'red'});
//        BootstrapDialog.show({
//            title: 'Error',
//            message: errMes,
//            type: BootstrapDialog.TYPE_DANGER,
//            onhidden: function () {
//                $('#' + boardId + " ." + depAr + "Date").click()
//            }
//        });
    } else {
        $cell.css({'color': 'black'})
    }
};

function validate(boardId, depAr) {
    if (sbb_debug_js_validation_off != undefined) {
        popupInfo("Debug mode on! JS validation is off");
        return '';
    }

    var thisTime = getTime(boardId, depAr);
    var errorMessage = '';
    var isFirst = depAr == 'arrive' && $.inArray(boardId, idArray) === 0;
    if (!isFirst) {
        var prevTime;
        if (depAr == 'departure') {
            prevTime = getTime(boardId, 'arrive');
            if (thisTime < prevTime) {
                errorMessage = "Departure cannot be earlier than arrival";
            }
        } else {
            var prevIndex = $.inArray(boardId, idArray) - 1;
            prevTime = getTime(idArray[prevIndex], 'departure');
            if (thisTime < prevTime) {
                errorMessage = "Departure from the previous station cannot be later than arrival at the current one";
            }
        }
    }
    var isLast = depAr == 'departure' && $.inArray(boardId, idArray) === idArray.length - 1;
    if (!isLast) {
        var nextTime;
        if (depAr == 'arrive') {
            nextTime = getTime(boardId, 'departure');
            if (thisTime > nextTime) {
                errorMessage = "Arrival cannot be later than departure";
            }
        } else {
            var nextIndex = $.inArray(boardId, idArray) + 1;
            nextTime = getTime(idArray[nextIndex], 'arrive');
            if (thisTime > nextTime) {
                errorMessage = "Arrival at the next station cannot be earlier than departure from the current one";
            }
        }
    }
    if (true || errorMessage.length == 0) {
        var standTime = depAr == 'arrive' ? nextTime - thisTime : thisTime - prevTime;
        standTime /= 60000;
        $('#' + boardId + ' .standTime').text(standTime);
    }
    return errorMessage;
}

function getTime(boardId, depAr) {
    var idPtr = '#' + boardId;
    var ptr = idPtr + ' .' + depAr;
    return cellsToDate($(ptr + 'Date').text(), $(ptr + 'Time').text(), $(idPtr + ' .minuteOffset').text())
}

function editBoard() {
    if (!validateAll()) {
        return;
    }

    var board = [];
    $('#boardTable').find('.remove').each(function () {
        var boardString = {};
        boardString.boardId = parseInt($(this).attr('id'));

        $(this).find('td').each(function () {
            var clazz = $(this).attr('class');
            if ($.inArray(clazz, mutable) != -1) {
                boardString[clazz] = $(this).text();
            }
        });
        board.push(boardString);
    });
    board = JSON.stringify(board);
    $.post('/SBB/trip/board/edit',
        {board: board},
        function (response) {
                popupSuccess(response.trim());
                getBoard();
        }
    ).error(function (jdXHR) {
            popupjdXHRError(jdXHR);
        });
}

function validateAll() {
    if (sbb_debug_js_validation_off != undefined) {
        popupInfo("Debug mode on! JS validation is off");
        return true;
    }
    var errorCount = 0;
    $.each(idArray, function (id) {
        $.each(['departure', 'arrive'], function (depAr) {
            var errMes = validate(id, 'depAr');
            if (errMes !== '') {
                errorCount++;
                popupNoTimeoutError('Lne ' + id + ': ' + errMes);
            }
        })
    });
    return errorCount == 0;
}



