var GeneralTab = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearAssignedUser();
        initInputClearCompletedUser();
    };
    
    function initInputClearAssignedUser() {
        $("#requestedByUserName").inputClear({
            placeholder: "Select assigned User",
            btnMethod: "GeneralTab.addAssignedUser()",
        });
    };

    function initInputClearCompletedUser() {
        $("#completedByUserName").inputClear({
            placeholder: "Select completed User",
            btnMethod: "GeneralTab.addCompletedUser()"
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function setAssignedUser() {
        initModalUserSelect('setAssignedUser');
    };

    function setCompletedUser() {
        initModalUserSelect('setCompletedUser');
    };
    
    function initModalUserSelect(func) {
        
        var businessId = $("#businessId option:selected").val(); 
        
        if (businessId != null && businessId != "" && businessId != undefined) { 
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../workorder/view/modal/users';
                $modal.load(url, '', function () {
                    DatatableModalUsers.init(
                            "master-modal-datatable",
                            "tbl_users",
                            "../restapi/users/usersbybusinessid?id=" + businessId,
                            func);
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
        
        addAssignedUser: function () {
            setAssignedUser(); 
        },
        
        addCompletedUser: function () {
            setCompletedUser(); 
        },
    };
    
}();