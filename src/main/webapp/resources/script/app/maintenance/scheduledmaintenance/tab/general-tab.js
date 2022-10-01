var TabGeneral = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearAccount();
        initInputClearDepartment();
        initInputClearUser();
    };
    
    function initInputClearAccount(){
        $("#accountName").inputClear({
            placeholder:"Please specify a account",
            btnMethod:"TabGeneral.addAccount()",
        });
    };
    
    function initInputClearDepartment(){
        $("#chargeDepartmentName").inputClear({
            placeholder:"Please specify a department",
            btnMethod:"TabGeneral.addDepartment()",
        });
    };   
    
    function initInputClearUser() {
        $("#requestorName").inputClear({
            placeholder: "Select a Assigned User",
            btnMethod: "TabGeneral.addUser()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalAccountSelect() {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/view/modal/accounts';
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
            var url = '../scheduledmaintenance/view/modal/departments';
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
    
    function initModalUserSelect() {
        
        var businessId = $("#businessId option:selected").val(); 
        
        if (businessId != null && businessId != "" && businessId != undefined) { 
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../scheduledmaintenance/view/modal/users';
                $modal.load(url, '', function () {
                    DatatableModalUsers.init(
                            "master-modal-datatable",
                            "tbl_users",
                            "../restapi/users/usersbybusinessid?id=" + businessId,
                            "setAssignedUser");
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Bisuness first");
        }

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
        
        addUser: function () {
            initModalUserSelect(); 
        }
    };
    
}();