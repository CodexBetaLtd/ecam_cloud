var ValueTab = function () {  

	var initButtons = function () {
		$('#new-value-btn').on('click', function () {
			ValueTab.valueAddModal();
        });
		
	};

    var valueAddModal = function () {
        var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../tax/value-add-modal-view';
            $modal.load(url, '', function () {
            	ValueAddModal.init("cmms-setting-add-modal");
                $modal.modal();
            });
        }, 1000);
    };
    
    var valueEditModal = function (index) {
        var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../tax/value-add-modal-view';
            $modal.load(url, '', function () {
            	ValueAddModal.init('cmms-setting-add-modal');
            	ValueAddModal.fillScheduledTask(taxValues[index]);
                $modal.modal();
            });
        }, 1000);
    };

    var initTaskTable = function () {
    	
        $('#taxValueTbl > tbody').html("");
        if (taxValues.length > 0) {
            $.each(taxValues, function (index, taxtValue) {
                taxtValue.index=index;
                var row = "<tr> " +

                    "<input type='hidden' name='taxValueDTOs[" + index + "].id' value='" + CustomComponents.nullValueReplace(taxtValue.id) + "'> " +
                    "<input type='hidden' name='taxValueDTOs[" + index + "].version' value='" + CustomComponents.nullValueReplace(taxtValue.version) + "'> " +
                    "<input type='hidden' name='taxValueDTOs[" + index + "].startDate' value='" + CustomComponents.nullValueReplace(taxtValue.startDate) + "'> " +
                    "<input type='hidden' name='taxValueDTOs[" + index + "].endDate' value='" + CustomComponents.nullValueReplace(taxtValue.endDate) + "'> " +
                    "<input type='hidden' name='taxValueDTOs[" + index + "].isCurrentRate' value='" + CustomComponents.nullValueReplace(taxtValue.isCurrentRate) + "'> " +
                    "<input type='hidden' name='taxValueDTOs[" + index + "].value' value='" + CustomComponents.nullValueReplace(taxtValue.value) + "'> " +
                    "<td> " + ( index + 1 ) + "</td>" +
                    "<td> " + taxtValue.startDate + "</td>" +
                    "<td> " + taxtValue.endDate + "</td>" +
                    "<td> " + taxtValue.value + "</td>" +
                    "<td> " + taxtValue.isCurrentRate + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("ValueTab.valueEditModal", index) +
                    ButtonUtil.getCommonBtnDelete("ValueTab.removeTask", index) +
                    "</div>" +
                    "</td></tr>";
                
                $('#taxValueTbl > tbody:last-child').append(row);
            });
        } else {
            CustomComponents.emptyTableRow("taxValueTbl", 6, "Please Add Values for the Tax.");
        }
    };
    
    var removeTask = function (index) {
    	taxValues.splice(index, 1);
    	initTaskTable();
    };

    return {
    	
    	init : function () {
    		initButtons();
    		initTaskTable();
    	},

    	initTaskTable: function () {
    		initTaskTable();
        },
        
        valueAddModal: function () {
        	valueAddModal();
        },
        
        valueEditModal: function (index) {
        	valueEditModal(index);
        },
        
        removeTask: function (index) {
        	removeTask(index);
        }
    };

}();