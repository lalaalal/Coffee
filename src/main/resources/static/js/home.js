$(document).ready(function () {
    setInterval(function () {
        $.ajax({
            url: '/api/order/current',
            success: function (result) {
                $('#current-order-number').text(result);
            }
        })
    }, 5000);
});