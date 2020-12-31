var AssetModelSelectModal = function() {	
    
    var setAssetModel  = function(id,name){
    	$('#modelId').val(id);
    	$('#modelName').val(name);
    }

    var assetModelView = function () {
    	var $modal = $('#common-modal');
    	var brandId = $('#brandId').val();
    	if(brandId != null && brandId !="" && brandId != undefined ){
            CustomComponents.ajaxModalLoadingProgressBar();
        	setTimeout(function () {
        		var url = '../part/model-select-view';
        		$modal.load(url, '', function () {
        			AssetModelDataTable.runDataTable();
        			$modal.modal();
        		});
        	}, 1000);
    	}else{
    		alert("Please select a brand");
    	}

    };
    
    var assetModelAddView = function () {
        var $modal = $('#model-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/model-add-view';
            $modal.load(url, '', function (){
            	$('#modelBrandId').val($('#brandId').val());
            	$('#modelBrandName').val($('#brandName').val());
            	AssetModelAdd.init();
                $modal.modal();
            });
        }, 1000);
    };
    
	var saveAssetModel = function() {

		var $modal = $('#model-add-modal');
		var form = $('#assetModelAddForm');
		if (form.valid()) {
			$.ajax({
				url : form.attr('action') + '?module=asset',
				type : 'POST',
				data : form.serialize(),
				success : function(response) {
					$modal.empty().append(response);
	    			AssetModelDataTable.runDataTable();
					$modal.modal();
				},
				error : function(response) { }
			});
		}
	};
	
	return {

        assetModelView: function(){
        	assetModelView();
	    },
	    
	    assetModelAddView: function(){
	    	assetModelAddView();
	    },
	    
        setAssetModel: function(id, name){
        	setAssetModel(id,name);
        	$('#common-modal').modal('toggle');
        },
        
        saveAssetModel: function() {
        	saveAssetModel();
		}
		
	};
	
}();