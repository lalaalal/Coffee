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
                }
            };
            request.open('GET', file, true);
            request.send();
        }
    }
});