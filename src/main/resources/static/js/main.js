document.addEventListener('DOMContentLoaded', () => {
    const parts = $('.html-part');
    for (const part of parts) {
        part.innerHTML = 'loading...';
        const file = part.dataset.includeFile;
        if (file) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                    part.innerHTML = this.responseText;
                    if (part.id)
                        init('#' + part.id);
                }
            };
            request.open('GET', file, true);
            request.send();
        }
    }
    init();
});

function init(prefix) {
    const observers = $(`${prefix} .observer`);
    observers.change(function() {
        const inputGroup = $(`input[name=${this.name}]`);
        for (const input of inputGroup) {
            let linkedLabel = $(`label[for=${input.id}]`);
            if (this.id === input.id)
                linkedLabel.css('background-color', '#eee');
            else
                linkedLabel.css('background-color', 'transparent');
        }
    });
    const selected = $(`${prefix} .observer:checked`)[0];
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