
function get(url) {
    window.location.href=url;
}

function post(path, params) {
    var method='post';

    var form = document.createElement('form');
    form.method = method;
    form.action = path;

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = params[key];

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}