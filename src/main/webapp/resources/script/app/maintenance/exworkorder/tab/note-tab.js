var woNoteTab = function() {
	
    /*********************************************************************
     * Init Buttons
     *********************************************************************/
	
	var initButtons = function() {
	    $('#btn-wo-note-add').on('click', function (event) {
	        event.preventDefault();
	        woNoteTab.woNoteAddView();
	    });
	};
	
    /*********************************************************************
     * Init Modals
     *********************************************************************/
	
	var woNoteAddView = function() {
		var $modal = $("#master-modal-datatable");
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function () {
			var url = '../workorder/wonoteaddmodalview';
			$modal.load(url, '', function () {
				NoteAddModal.init();
				$modal.modal();
			});
		}, 1000);
	};
	
	var woNoteEditView = function(index) {
		var $modal = $("#master-modal-datatable");
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function () {
			var url = '../workorder/wonoteaddmodalview';
			$modal.load(url, '', function () {
				NoteAddModal.init();
				fillNoteView(index);
				$modal.modal();
			});
		}, 1000);
	};
	
    var fillNoteView = function (index) {
    	
    	var note = woNoteList[index]; 
    	
        $('#woNoteIndex').val(note['index']);
        $('#woNoteVersion').val(note['version']);
        $('#woNoteId').val(note['id']);
        $('#woNoteDate').val(note['woNoteDate']);
        $('#woNote').val(note['woNote']); 
        $('#woNoteStatus').val(note['workOrderStatus']); 
	};
	
    /*********************************************************************
     * Set Data
     *********************************************************************/
	
	var woNote = function() {
		var note = {};
		note['index'] = $('#woNoteIndex').val();
		note['version'] = $('#woNoteVersion').val();
		note['id'] = $('#woNoteId').val();
		note['woNoteDate'] = $('#woNoteDate').val();
		note['woNote'] = $('#woNote').val();
		note['workOrderStatus'] = $('#woNoteStatus').val();
		
        if (note.index != null && note.index != "") {
        	setCommonDataToNoteObj(note, woNoteList[note.index]);
        } else {
        	addNoteToList(note);
        }

        populateTable();
        $('#master-modal-datatable').modal('toggle'); 
		
	};

    var addNoteToList = function (woNote) {
        var woNoteObj = {};
        if (woNote.index != null && woNote.index != "" && woNote.index >= 0) {
        	woNoteObj = woNoteList[woNote.index];
        	setCommonDataToNoteObj(woNote, woNoteObj);
        } else { 
        	if (woNoteList.length == 0) {
	        	woNoteObj['index'] = 0;
	        } else {
	        	CustomValidation.validateFieldNull(woNoteObj, 'index', woNoteList.length);
	        }
        }
        setCommonDataToNoteObj(woNote, woNoteObj);
        woNoteList.push(woNoteObj);
    };
    
    var setCommonDataToNoteObj = function (woNote, woNoteObj) {

    	CustomValidation.validateFieldNull(woNoteObj, 'id', woNote.id);
    	CustomValidation.validateFieldNull(woNoteObj, 'woNoteDate',  woNote.woNoteDate);
    	CustomValidation.validateFieldNull(woNoteObj, 'woNote', woNote.woNote);
    	CustomValidation.validateFieldNull(woNoteObj, 'workOrderStatus', woNote.workOrderStatus);
    	CustomValidation.validateFieldNull(woNoteObj, 'version', woNote.version);

    };
    
    /*********************************************************************
     * Populate Table Date
     *********************************************************************/
	function populateTable() {
        $('#tbl-wo-note >tbody').html("");
        if (woNoteList.length > 0) {
            $.each(woNoteList, function (index, result) {
            	if( result.index == null){
            		result['index'] = index;
            	}  
               var newRow = "<tr> " +
               				"<input type='hidden' name='workOrderNoteDTOs[" + index + "].id' value='" + result.id + "'> " +
               				"<input type='hidden' name='workOrderNoteDTOs[" + index + "].woNoteDate' value='" + result.woNoteDate + "'> " +
               				"<input type='hidden' name='workOrderNoteDTOs[" + index + "].woNote' value='" + result.woNote + "'> " +
               				"<input type='hidden' name='workOrderNoteDTOs[" + index + "].workOrderStatus' value='" + CustomValidation.nullValueReplace( result.workOrderStatus ) + "'> " +
		                    "<input type='hidden' name='workOrderNoteDTOs[" + index + "].version' value='" + result.version + "'> " +
		                    "<td> " + (index + 1) + "</td>" + 
		                    "<td> " + CustomValidation.nullValueReplace(result.woNoteDate) + "</td>" +
		                    "<td> " + CustomValidation.nullValueReplace(result.woUserName) + "</td>" +
		                    "<td> " + CustomValidation.nullValueReplace(result.woNote) + "</td>" +
		                    "<td> " +
		                    	setTableButtons(result) +
		                	"</td>" +
		                    "</tr>";
                $("#tbl-wo-note > tbody").append(newRow);
            });
        } else {
            CustomComponents.emptyTableRow("tbl-wo-note", 4);
        }
    };
    
    var setTableButtons = function(result) { 
    	if (result.workOrderStatus != "" && result.workOrderStatus != null) {
    		return "";
		} else {			
			var buttons = "<div align='center'>" +
				ButtonUtil.getCommonBtnEdit("woNoteTab.editListItem", result.index) +
				ButtonUtil.getCommonBtnDelete("woNoteTab.deleteListItem", result.index) +
			"</div>";
			return buttons;
		}
	};
    
    var deleteRequestNote = function(index) {
    	woNoteList.splice(index, 1);
    	woNoteTab.populateNote();
	};
	
	var formatDate = function(data) {
        if (data === null) return "";
        return moment(moment(data,'DD-MM-YYYY')).format('DD-MM-YYYY');
	};

	return{
		
		init: function() {
			initButtons();
			populateTable();
		},
		
		woNoteAddView: function() {
			woNoteAddView();
		},
		
		populateNote: function() {
			populateTable();
		},
		
		addWoNote: function() {
			woNote();
		},
		
		editListItem: function(index) {
			woNoteEditView(index);
		},
		
		deleteListItem: function(index) {
			deleteRequestNote(index);
		}
		
	};
}();