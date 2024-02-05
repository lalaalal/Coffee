$(document).ready(function() {
    let makeReservationButton = $('#make-reservation-button');
    makeReservationButton.click(function() {
        for (const input of $('input[required]')) {
            if (input.value == '') {
                input.classList.add('red-border');
                alert("빈 칸을 모두 채워주세요");
                return;
            }
        }

        let params = new URLSearchParams(location.search);
        const date = params.get('date');

        let reservation = {}
        reservation['name'] = $('#name-input')[0].value;
        reservation['password'] = $('#password-input')[0].value;
        let selector = $('#time-selector')[0];
        reservation['time'] = date + ' ' + selector.options[selector.selectedIndex].value + ":00";
        reservation['order'] = order;
        reservation['message'] = $('#message-input')[0].value;

        console.log(reservation);
        $.ajax({
            url: '/api/reservation/make',
            type: 'POST',
            contentType : "application/json",
            data: JSON.stringify(reservation)
        }).done(function(data, status, xhr) {
            alert(data.message);
            location.href = "/reservation/view";
        })
    });
});