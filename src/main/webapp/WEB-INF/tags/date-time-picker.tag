<link rel="stylesheet" href="${base}/webjars/bootstrap-datetimepicker/2.4.4/css/bootstrap-datetimepicker.min.css">
<script src="${base}/webjars/bootstrap-datetimepicker/2.4.4/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var pickerInputs = $('.date-picker');
        pickerInputs.each(function () {
            var pickerInput = $(this);
            var icon = pickerInput.attr('icon');
            var format = pickerInput.attr('format');
            var time = pickerInput.attr('time') != null;
            var date = pickerInput.attr('date') != null;
            if (!icon && time) {
                icon = 'fa-clock';
            }

            var decorations = '<div class="input-group-append">' +
                '<button class="btn input-group-text picker-toggle" type="button">' +
                '<i class="far fa-calendar-alt"></i></button></div>';
            if (icon) {
                decorations = decorations.replace('fa-calendar-alt', icon)
            }

            pickerInput.wrap('<div class="input-group date"></div>');
            pickerInput.parent().append(decorations);

            var options = {
                container: pickerInput.parent(),
                autoclose: true,
                todayHighlight: true,
                todayBtn: "linked",
                format: format ? format : 'mm/dd/yyyy hh:ii',
                date: new Date(),
                defaultDate: new Date()
            };
            if (time) {
                options.startView = 1;
                options.maxView = 1;
                options.todayBtn = null;
                options.pickDate = false;
                if (!format) {
                    options.format = 'hh:ii';
                }
            }
            if (date) {
                options.minView = 2;
                if (!format) {
                    options.format = 'mm/dd/yyyy';
                }
            }

            options.container.find(".picker-toggle").click( function () {
                pickerInput.focus();
            });

            var value = pickerInput.attr('value');
            pickerInput.attr('value', '');
            var datetimepicker = pickerInput.datetimepicker(options);
            pickerInput.attr('value', value);
            pickerInput.attr('autocomplete', 'off');
            if (!pickerInput.attr('placeholder')) {
                pickerInput.attr('placeholder', options.format);
            }

            if (time) {
                datetimepicker.on("show", function () {
                    options.container.find(".table-condensed .prev").css('display', 'none');
                    options.container.find(".table-condensed .next").css('display', 'none');
                    options.container.find(".table-condensed .switch").css('width', '200px').text("Pick Time");
                });
            }
        });
    });
</script>