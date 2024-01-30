document.addEventListener('DOMContentLoaded', () => {
    const parts = $('.html-part');
    for (const part of parts) {
        const file = part.dataset.includeFile;
        if (file) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                console.log(this.status);
                console.log(this.responseText);
                if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                    console.log(this.responseText);
                    part.innerHTML = this.responseText;
                }
            };
            request.open('GET', file, true);
            request.send();
        }
    }
});