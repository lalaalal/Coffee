$(document).ready(function () {
    $('#set-order-number-button').on('click', function () {
        let number = $('#order-number-input')[0].value;
        console.log(number);
        $.ajax({
            url: `/api/order/set/${number}`
        });
    });
})