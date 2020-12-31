var FileTab = function () {
	
	var initButtons = function () {		

		$('#btn-file-details').on('click', function () {
	    	if($('#code').val()){
	    		FileTab.fileAddModal();
	    	}else{
	    		alert('Please Insert Workorder Code Before Upload File')
	    	}
    	});
	};

    var fileAddModal = function () {
        var $modal = $('#master-modal-datatable');       
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/filemodelview';
            $modal.load(url, '', function () {
            	FileAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var initFileTable = function () {
        if (files.length > 0) {
            $("#file-tbl > tbody").html("");
            for (var row = 0; row < files.length; row++) {
            	var file = files[row];
                var html = "<tr id='row_" + row + "' >" +
                "<input id='files_" + row + ".id' name='files[" + row + "].id' value='" + file.id + "' type='hidden' >" +
                "<input id='files_" + row + ".itemDescription' name='files[" + row + "].itemDescription' value='" + file.itemDescription + "' type='hidden' >" +
                "<input id='files_" + row + ".fileLocation' name='files[" + row + "].fileLocation' value='" + file.fileLocation+ "' type='hidden' >" +
                "<input id='files_" + row + ".fileType' name='files[" + row + "].fileType' value='" + file.fileType+ "' type='hidden' >" +
                "<input id='files_" + row + ".fileDate' name='files[" + row + "].fileDate' value='" + moment(file.fileDate).format('DD-MM-YYYY')+ "' type='hidden' >" +
                "<input id='files_" + row + ".version' name='files[" + row + "].version' value='" +file.version+ "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + file.itemDescription + "</td>" +
                    "<td>" + moment(file.fileDate).format('YYYY-MM-DD') + "</td>" +
                    "<td>" + 
                    	"<div align='center'>" +
            				ButtonUtil.getCommonBtnDownloadFile('workorder/download-file?fileId=',file.id) +
	                    	ButtonUtil.getCommonBtnDelete("FileTab.deleteFile", row) +
                    	"</div>" +
                    "</tr>";
                $('#file-tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("file-tbl", 4, "Please Upload File For Work Order.");
        }
        
    };
      
    var addFile = function () {
        var file = {
        	id: '',
        	version: '',
            itemDescription: $('#fileDescription').val(),
            fileLocation: fileLocation,
            fileDate: new Date(),
            fileType: fileType,
        };
        
        files.push(file)
        initFileTable()
        $('#master-modal-datatable').modal('toggle');
    };
    
    var deleteFile = function (index) {
    	files.splice(index, 1);
    	initFileTable();
    };

    return {   	
    	init: function () {
    		initButtons();
    		initFileTable();
    	},

        /***********************************************************************
         * Part Consuming ref Add
		 **********************************************************************/

    	addFile:function(){
    		addFile();
    	},
        /**********************************************************
         * Initialize Modals
         *********************************************************/
    	fileAddModal:function(){
    		fileAddModal();
    	},

    	deleteFile:function(index){
    		deleteFile(index)
    	}
    };
}();