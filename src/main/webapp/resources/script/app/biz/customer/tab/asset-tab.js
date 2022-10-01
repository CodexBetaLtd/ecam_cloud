var TabAsset = function () {
	
	var initButtons = function () {
		
		$('#btn-add-customer-asset').on('click', function(){
	    	TabAsset.assetSelectModal();
	    });
		
	};
	
	var assetSelectModal = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../customer/assetmodelview';
            $modal.load(url, '', function () {
            	AssetSelectModel.init();
                $modal.modal();
            });
        }, 1000);
    };
    
	var addAsset = function (id, name, code, assetCategoryName, location) {
		var asset = []
		asset['id'] = id;
		asset['name'] = name;
		asset['code'] = code;
		asset['assetCategoryName'] = assetCategoryName;
		asset['location'] = location;
		assets.push(asset);
		initHtmlTable();	
	};

    var removeAsset = function (id) {
        for (var i = 0; i < assets.length; i++) {
            if (assets[i].id == id) {
            	assets.splice(i, 1);
            }
        }
        initHtmlTable();
    };
    
    var isAssetAlreadyAdd = function (id) {
        for (var i = 0; i < assets.length; i++) {
        	if (assets[i].id == id) {
                return true;
            }
        }
        return false;
    };    
 
    var initHtmlTable = function () {
    	console.log(assets.length);
        if (assets.length > 0) {
            var row, asset;
            $("#asset_tbl > tbody").html("");
            for (row = 0; row < assets.length; row++) {
            	asset = assets[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='asset" + row + ".id' name='assets[" + row + "].id' value='" + asset['id'] + "' type='hidden'>" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + asset['name']+ "</span></td>" +
                    "<td><span>" + asset['code'] + "</span></td>" +
                    "<td class='hidden-xs'><span>" + asset['assetCategoryName'] + "</span></td>" +
                    "<td><span>" + asset['location'] + "</span></td></tr>";
                
                $('#asset_tbl > tbody:last-child').append(html);
            }
        } else {
            $("#asset_tbl > tbody").html("<tr><td colspan='5' align='center'>Please Add Customer for the Asset.</td></tr>");
        }
    };

    return {
    	
    	init : function () {
    		initButtons();
    		initHtmlTable();
    	},
    	
    	assetSelectModal : function () {
    		assetSelectModal();
    	},
    	
    	addAsset : function(id, name, code, assetCategoryName, location){
    		addAsset(id, name, code, assetCategoryName, location);
    	},
    	
    	removeAsset : function(id){
    		removeAsset(id);
    	}
    	
    };

}();