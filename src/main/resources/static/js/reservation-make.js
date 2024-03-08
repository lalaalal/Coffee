$(document).ready(function() {
    let targetDate = new Date($('.title')[0].innerHTML);
    if (targetDate <= Date.now()) {
        alert("신청이 불가능한 날짜입니다. 날짜를 확인해주세요!");
        location.href = '/reservation/view';
    }

    let makeReservationButton = $('#make-reservation-button');
    makeReservationButton.click(function() {
        for (const input of $('input[required]')) {
            if (input.value === '') {
                input.classList.add('red-border');
                alert("빈 칸을 모두 채워주세요!");
                location.href = "#" + input.id;
                return;
            }
        }

        let passwordInput = $('#password-input')[0];
        let passwordConfirm = $('#password-confirm')[0]
        if (passwordInput.value !== passwordConfirm.value) {
            passwordConfirm.classList.add('red-border');
            alert("비밀번호를 확인해주세요!");
            location.href = "#password-input";
            return;
        }
        let params = new URLSearchParams(location.search);
        const date = params.get('date');

        let reservation = {}
        reservation['name'] = $('#name-input')[0].value;
        reservation['password'] = passwordInput.value;
        let selector = $('#time-selector')[0];
        reservation['time'] = date + ' ' + selector.options[selector.selectedIndex].value + ":00";
        reservation['order'] = order;
        reservation['message'] = $('#message-input')[0].value;
        reservation['contact'] = $('#contact-input')[0].value;

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