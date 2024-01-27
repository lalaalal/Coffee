document.addEventListener('DOMContentLoaded', () => {
    let buttons = $('.add-button');
    for (const button of buttons) {
        let value = button.getAttribute('value');
        button.addEventListener('click', () => {
            window.location.href = `/reservation/make?date=${value}`;
        })
    }
})