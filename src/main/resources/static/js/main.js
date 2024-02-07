document.addEventListener('DOMContentLoaded', () => {
    const parts = $('.html-part');
    for (const part of parts) {
        const file = part.dataset.includeFile;
        if (file) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                    part.innerHTML = this.responseText;
                }
            };
            request.open('GET', file, true);
            request.send();
        }
    }
    $(document.body).on('click', '.observer', loadRadioButtons);
});

const loadRadioButtons = function () {
    const observers = $('.observer');
    observers.change(function() {colorElement(this)});
    const selected = $('.observer:checked')[0];
    if (selected)
        colorElement(selected);
}

function colorElement(element) {
    const inputGroup = $(`input[name=${element.name}]`);
    for (const input of inputGroup) {
        let linkedLabel = $(`label[for=${input.id}]`);
        if (element.id === input.id)
            linkedLabel.css('background-color', '#eee');
        else
            linkedLabel.css('background-color', 'transparent');
    }
}