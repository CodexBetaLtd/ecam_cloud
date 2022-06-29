var ReportViewComponent = function () {

    var runSelect2 = function () {
        $(".select2cls").select2({
            placeholder: "Please Select",
            allowClear: true
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            container: '#picker-container',
            autoclose: true
        });
    };

    var runCheckboxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%'
        });
    };

    return {

        init: function () {
            runSelect2();
            runDatePicker();
            runCheckboxes();          
        }
    };
}();
