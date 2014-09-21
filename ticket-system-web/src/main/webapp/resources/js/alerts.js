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
    }else if (response.substring(0, 12) === "Server error"){
        popupError('Server error');
    } else {
        return false;
    }
    findTrips();
    return true;
}