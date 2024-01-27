document.addEventListener('DOMContentLoaded', () => {
    let weekControlButtons = $('.week-control-button');
    for (const button of weekControlButtons) {
        let offset = button.getAttribute('value');
        button.addEventListener('click', () => {
            window.location.href = `/reservation/view?offset=${offset}`;
        })
    }

    let addButtons = $('.add-button');
    for (const button of addButtons) {
        let value = button.getAttribute('value');
        button.addEventListener('click', () => {
            window.location.href = `/reservation/make?date=${value}`;
        })
    }

    let reservations = $('.reservation-container');
    for (const reservation of reservations) {
        let input = reservation.getElementsByTagName('input')[0];
        let reservationId = input.getAttribute('value');
        reservation.addEventListener('click', () => {
            window.location.href = `/reservation/${reservationId}`;
        })
    }
})