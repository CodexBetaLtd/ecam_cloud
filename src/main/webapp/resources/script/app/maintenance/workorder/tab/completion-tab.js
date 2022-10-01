var CompletionTab = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearAccount();
        initInputClearDepartment();
    };
    
    function initInputClearAccount(){
        $("#accountName").inputClear({
            placeholder:"Please specify a account",
            btnMethod:"CompletionTab.addAccount()",
        });
    };
    
    function initInputClearDepartment(){
        $("#chargeDepartmentName").inputClear({
            placeholder:"Please specify a department",
            btnMethod:"CompletionTab.addDepartment()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalAccountSelect() {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/view/modal/accounts';
            $modal.load(url, '', function () {
                DatatableModalAccounts.init(
                        "master-modal-datatable",
                        "tbl_accounts",
                        "../restapi/account/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalDepartmentSelect() {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/view/modal/departments';
            $modal.load(url, '', function () {
                DatatableModalDepartments.init(
                        "master-modal-datatable",
                        "tbl_departments",
                        "../restapi/charge-department/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };

    return {
        init: function () {
            initInputClearComponents();
        },
        
        addAccount: function () {
            initModalAccountSelect(); 
        },
        
        addDepartment: function () {
            initModalDepartmentSelect(); 
        },
    };
    
}();