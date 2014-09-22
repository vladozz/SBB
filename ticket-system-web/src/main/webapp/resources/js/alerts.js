function popupError(message) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    toastr.error(message, "Error");
}

function popupSuccess(message) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    toastr.success(message, "Success");
}

function alertError(errorMessage) {
    BootstrapDialog.show({
        title: 'Error',
        message: errorMessage,
        type: BootstrapDialog.TYPE_DANGER
    });
}

function isError(response) {
    if (response.substring(0, 5) === "error") {
        popupError(response.substring(6));
    } else if (response.substring(0, 7) === "outdate") {
        popupError('Your page is irrelevant! Try again.\n' + response.substring(8));
    } else if (response.substring(0, 12) === "Server error") {
        popupError('Server error');
    } else {
        return false;
    }
    findTrips();
    return true;
}

function formatDateToString(date) {
    var dd = date.getDate();
    if (dd < 10) {
        dd = '0' + dd;
    }
    var mm = date.getMonth() + 1;
    if (mm < 10) {
        mm = '0' + mm;
    }
    var yyyy = date.getFullYear();
    return yyyy + '-' + mm + '-' + dd;
}
function formatTimeToString(date) {
    var HH = date.getHours();
    if (HH < 10) {
        HH = '0' + HH;
    }
    var mm = date.getMinutes();
    if (mm < 10) {
        mm = '0' + mm;
    }
    return HH + ':' + mm;
}

function readDateInput($input, defaultDate) {
    var date = $input.val();
    if (date === "") {
        if (defaultDate != undefined) {
            date = defaultDate;
        } else {
            date = formatDateToString(new Date());
        }
        $input.val(date);
    }
    return date;
}

function readTimeInput($input, defaultTime) {
    var time = $input.val();
    if (time === "") {
        if (defaultTime != undefined) {
            time = defaultTime;
        } else {
            time = formatTimeToString(new Date());
        }
        $input.val(time);
    }
    return time;
}