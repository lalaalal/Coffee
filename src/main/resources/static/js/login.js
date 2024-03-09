$(document).ready(function() {
    $("#login-button").click(function() {
        let id = $("#id-input")[0].value;
        let password = $("#password-input")[0].value;
        $.ajax({
            url: '/api/login',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: `id=${id}&password=${password}`
        }).done(function(data, status, xhr) {
            alert(data.message);
            location.href ='/';
        }).fail(function(xhr, status, error) {
        });
    })
});