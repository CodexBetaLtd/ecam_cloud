
var AssetTab = function () {
	
	var initButtons = function () {

		$('#btn-new-asset').on('click', function () {
			AssetTab.assetSelectModalView();
		});
		
	    $('#btn-new-assets-from-category').on('click', function () {
	    	AssetTab.assetCategorySelectModal();
	    });
		
	}; 
	
	var assetSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {	 
			var $modal = $('#common-modal');
			CustomComponents.ajaxModalLoadingProgressBar();
			setTimeout(function () {
				var url = '../bomgroup/assetmodelview';
				$modal.load(url, '', function () {
					AssetSelectModal.init('common-modal', bizId);
					$modal.modal();
				});
			}, 1000);
    	} else {
    		alert("Please Select a Business First.");
    	} 
	};
	
    var assetCategorySelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {	 
			var $modal = $('#common-modal');
			CustomComponents.ajaxModalLoadingProgressBar();
			setTimeout(function () {
				var url = '../bomgroup/assetcategorymodelview';
				$modal.load(url, '', function () {
					AssetCategorySelectModal.init('common-modal', bizId);
					$modal.modal();
				});
			}, 1000);
	    } else {
			alert("Please Select a Business First.");
		} 
    };

    var resetAssetHtmlTable = function () {

        if (assets.length > 0) {
            var row, asset;
            $("#bom-group-asset-tbl > tbody").html("");

            for (row = 0; row < assets.length; row++) {
                asset = assets[row]; 
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assets" + row + ".id' name='assets[" + row + "].id' value='" + asset.id + "' type='hidden'>" +
                    "<input id='assets" + row + ".name' name='assets[" + row + "].name' value='" + asset.name + "' type='hidden' >" +
                    "<input id='assets" + row + ".code' name='assets[" + row + "].code' value='" + asset.code + "' type='hidden' >" +
                    "<input id='assets" + row + ".assetCategoryName' name='assets[" + row + "].assetCategoryName' value='" + asset.assetCategoryName + "' type='hidden' >" +
                    "<input id='assets" + row + ".location' name='assets[" + row + "].location' value='" + asset.location + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + asset.name + "</span></td>" +
                    "<td><span>" + asset.code + "</span></td>" +
                    "<td class='hidden-xs'><span>" + asset.assetCategoryName + "</span></td>" +
                    "<td><span>" + asset.location + "</span></td>" +
                    "<td class='center'>" +
	                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
	                    	ButtonUtil.getCommonBtnDelete("AssetTab.removeAsset", asset.id) +
	                    "</div>" +
                    "</td>" +
                    "</tr>";
                
                $('#bom-group-asset-tbl > tbody:last-child').append(html);
            }
        } else {

            $("#bom-group-asset-tbl > tbody").html("<tr><td colspan='6' align='center'>Please Add Assets for the BOM Group.</td></tr>");
        }

    };

    var removeAsset = function (id) {
        for (var i = 0; i < assets.length; i++) {
            if (assets[i].id == id) {
                assets.splice(i, 1);
            }
        }
        resetAssetHtmlTable();
    };
    
    return {
    	
    	init: function() {
    		initButtons();
    		resetAssetHtmlTable();
		}, 
		
		removeAsset: function (id) {
		    removeAsset(id);
		},
		
		resetAssetHtmlTable: function () {
		    resetAssetHtmlTable();
		},
		
		assetSelectModalView: function() {
			assetSelectModal();
		},
		
		assetCategorySelectModal: function() {
			assetCategorySelectModal();
		}
    }; 
	
}();
