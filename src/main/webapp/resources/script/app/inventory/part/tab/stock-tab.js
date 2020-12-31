var StockTab = function () {

    /*********************************************************************
     * Init Value
     *********************************************************************/
    var initBtnStockBusiness = function () {
        $("#stockBusinessName").inputClear({
            placeholder: "Business",
            btnMethod: "StockTab.getBusinessDataTableModal()",
        });
    };
    
    var initBtnStockSite = function () {
    	$("#stockSiteName").inputClear({
    		placeholder: "Site ",
    		btnMethod: "StockTab.getSiteByBizDataTableModal()",
    	});
    };
    
    var initBtnStockWarehouse = function () {
        $("#warehouseName").inputClear({
            placeholder: "Site ",
            btnMethod: "StockTab.getWarehouseByBusiness()",
        });
    };

    var getStockAddModalView = function () {
    	var businessId=$('#businessId').val()
    	if(businessId.length>0){
            var $modal = $('#stock-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../part/stock-add-view';
                $modal.load(url, '', function () {
                	stockNotifications = [];
                	StockAddModal.init();
                	StockNotificationTab.init( $("#stockRowIndex").val() );
                	$('#stockBusinessName').val($('#businessId option:selected').text().trim())
                	$('#stockBusinessId').val($('#businessId option:selected').val())
                    $modal.modal();
                });
            }, 1000);
    	}else{
    		alert('please select a business first')
    	}

    };

    var getStockAddModalEditView = function (index) {
        var $modal = $('#stock-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/stock-add-view';
            $modal.load(url, '', function () {
            	StockAddModal.init();
            	$('#stockBusinessName').val($('#businessId option:selected').text().trim())
            	$('#stockBusinessId').val($('#businessId option:selected').val());
            	fillStockModal(index);
            	StockNotificationTab.init();
                $modal.modal();
            });
        }, 1000);
    };

    var fillStockModal = function (index) {
        $("#stockRowIndex").val(index);
        $("#stockId").val(stockItems[index].id);

        $("#stockBusinessId").val(stockItems[index].businessId);
        $("#stockBusinessName").val(stockItems[index].businessRef);

        $("#stockSiteId").val(stockItems[index].siteId);
        $("#stockSiteName").val(stockItems[index].site);

        $("#txtQtyOnHand").val(stockItems[index].qtyOnHand);
        $("#txtMinQty").val(stockItems[index].minQty);
        $("#warehouseName").val(stockItems[index].warehouseName);
        $("#warehouseId").val(stockItems[index].warehouseId);
        $("#batchNo").val(stockItems[index].batchNo);
        $("#sellingPrize").val(stockItems[index].sellingPrice);
        $("#buyingPrize").val(stockItems[index].buyingPrice);
        $("#description").val(stockItems[index].description);
        stockNotifications = stockItems[index].stockNotificationDTOs;

    };

    /*********************************************************************
     * Set Business Value
     *********************************************************************/
    var getBusinessDataTableView = function () {
    	var $modal = $('#stock-common-modal');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../part/business-select-view';
    		$modal.load(url, '', function () {
    			dtBusiness.dtBusiness("StockTab.addBusiness");
    			$modal.modal();
    		});
    	}, 1000);
    };
    var getWarehouseByBusiness = function () {
        var $modal = $('#stock-common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/warehouse-select-view';
            $modal.load(url, '', function () {
            	dtWarehouse.getWarehouseDataTable();
                $modal.modal();
            });
        }, 1000);
    };
    
    var addBusiness = function (id, name) {
        $("#stockBusinessId").val(id);
        $("#stockBusinessName").val(EncodeDecodeComponent.getBase64().decode(name));
    };

    /*********************************************************************
     * Set Site Value
     *********************************************************************/
    var getSiteByBizDataTableModal = function () {
        var dtURL = "../restapi/asset/getSiteByBusiness?id=" + $('#stockBusinessId').val();
        var $modal = $('#stock-common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/site-select-view';
            $modal.load(url, '', function () {
                dtSite.dtSiteByBusiness(dtURL, "StockTab.addSite");
                $modal.modal();
            });
        }, 1000);
    };
    
    var addSite = function (id, name) {
        $("#stockSiteId").val(id);
        $("#stockSiteName").val(EncodeDecodeComponent.getBase64().decode(name));
    };

    /*********************************************************************
     * Add New Stock
     *********************************************************************/
    var addNewOrUpdatePartStock = function () {
        if (($("#stockRowIndex")).val() !== null && ($("#stockRowIndex")).val() !== "") {
            updatePartStock(stockItems[$("#stockRowIndex").val()]);
        } else {
            addNewPartStock();
        }
        stockLevelDetails();
    };

    var addNewPartStock = function () {
        var newStock = {
            id: CustomComponents.nullValueReplace($("#stockId").val()),
            rowIndex: stockItems.length,
            partName: CustomComponents.nullValueReplace(""),
            warehouseName:CustomComponents.nullValueReplace($("#warehouseName").val()),
            warehouseId:CustomComponents.nullValueReplace($("#warehouseId").val()),
            batchNo:CustomComponents.nullValueReplace($("#batchNo").val()),
            sellingPrice:CustomComponents.nullValueReplace($("#sellingPrize").val()),
            buyingPrice:CustomComponents.nullValueReplace($("#buyingPrize").val()),
            description:CustomComponents.nullValueReplace($("#description").val()),
            stockPartId: CustomComponents.nullValueReplace($("#txtStockPart").val()),
            site: CustomComponents.nullValueReplace($("#stockSiteName").val()),
            siteId: CustomComponents.nullValueReplace($("#stockSiteId").val()),
            businessRef: CustomComponents.nullValueReplace($('#stockBusinessName').val()),
            businessId: CustomComponents.nullValueReplace($("#stockBusinessId").val()),
            qtyOnHand: CustomComponents.nullValueReplace($("#txtQtyOnHand").val()),
            minQty: CustomComponents.nullValueReplace($("#txtMinQty").val()),
            stockNotificationDTOs: stockNotifications,
            other: false
            
        };
        var stockItem = addStockPartItem(newStock)
//        if(!isStockAlreadyAdded(stockItem)){
        	stockItems.push(stockItem);
//        }else{
//        	alert('Stock already added to selected site business')
//        }
        
    };
    
//    var isStockAlreadyAdded=function(stockItem){
//    	for(var i = 0; i < stockItems.length; i++){
//    		if(stockItems[i].rowIndex != stockItem.rowIndex){
//    			if(stockItems[i].businessId == stockItem.businessId){
//        			if(stockItems[i].siteId == stockItem.siteId){
//        				return true;
//        			}
//    			}
//    		}
//    	}
//    	return false;
//    };

    var addStockPartItem = function (obj) {
        var stock = {};
        stock['rowIndex'] = obj.rowIndex;
        stock['id'] = obj.id;
        stock['partName'] = obj.partName;
        stock['warehouseName'] = obj.warehouseName;
        stock['warehouseId'] = obj.warehouseId;
        stock['batchNo'] = obj.batchNo;
        stock['sellingPrice'] = obj.sellingPrice;
        stock['buyingPrice'] = obj.buyingPrice;
        stock['description'] = obj.description;
        stock['stockNo'] = obj.stockNo;
        stock['stockPartId'] = obj.stockPartId;
        stock['site'] = obj.site;
        stock['siteId'] = obj.siteId;
        stock['businessId'] = obj.businessId;
        stock['businessRef'] = obj.businessRef;
        stock['siteId'] = obj.siteId;
        stock['qtyOnHand'] = obj.qtyOnHand;
        stock['minQty'] = obj.minQty;
        stock['other'] = obj.other;
        stock['stockNotificationDTOs'] = obj.stockNotificationDTOs;
        return stock;
    };

    var updatePartStock = function (stockItem) {
        stockItem['site'] = CustomComponents.nullValueReplace($("#stockSiteName").val());
        stockItem['siteId'] = CustomComponents.nullValueReplace($("#stockSiteId").val());

        stockItem['businessId'] = CustomComponents.nullValueReplace($("#stockBusinessId").val());
        stockItem['businessRef'] = CustomComponents.nullValueReplace($("#stockBusinessName").val());

        stockItem['qtyOnHand'] = CustomComponents.nullValueReplace($("#txtQtyOnHand").val());
        stockItem['minQty'] = CustomComponents.nullValueReplace($("#txtMinQty").val());
        stockItem['warehouseName'] = CustomComponents.nullValueReplace($("#warehouseName").val());
        stockItem['warehouseId'] = CustomComponents.nullValueReplace($("#warehouseId").val());
        stockItem['batchNo'] = CustomComponents.nullValueReplace($("#batchNo").val());
        stockItem['sellingPrice'] = CustomComponents.nullValueReplace($("#sellingPrize").val());
        stockItem['buyingPrice'] = CustomComponents.nullValueReplace($("#buyingPrize").val());
        stockItem['description'] = CustomComponents.nullValueReplace($("#description").val());
        stockItem['stockNotificationDTOs'] = stockNotifications;
    };


    //stock details
    var viewStockDetail = function (stockItems, eventObj) {
        var stockId = $(eventObj).closest("tr").find(".clsStockId").val();
        if (stockId != undefined && stockId != null && stockId != "") {
            $.ajax({
                type: "GET",
                url: '../restapi/stock/find?id=' + stockId,
                dataType: 'json',
                success: function (jsonResult) {
                    setToStock(jsonResult, eventObj);
                }
            }).done(function (data) {
                // $('#stockModal').modal('show');
            })
        }
        else {
            var des = ($(eventObj).closest("li")).find(".clsRowIndex").val();
            for (var i = 0; i < stockItems.length; i++) {
                if (des === stockItems[i].description) {
                    setToStock(stockItems[i], eventObj);
                }
            }
        }
    };

    //sites for  business
    var setToStock = function (stockObj, eventObj) {
        $.ajax({
            type: "GET",
            url: '../restapi/asset/getSitesForBusiness?businessId=' + $(eventObj).closest("tr").find(".clsStockBusinessRef").val(),
            dataType: 'json',
            success: function (json) {
                var $au = $("#siteId");
                $au.empty();
                $.each(json, function (index, stockObj) {
                    $au.append($("<option></option>").attr("value", stockObj.id).text(stockObj.name));
                });
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status + " - " + thrownError)
            }
        }).done(function (data) {
            $("#stockId").val(stockObj.id);
            $("#stockRowIndex").val($(eventObj).closest("tr").find(".clsRowIndex").val());
            $("#txtStockPart").val(stockObj.partName);
            $("#siteId").val(stockObj.siteId);
            $("#txtQtyOnHand").val(stockObj.qtyOnHand);
            $("#txtMinQty").val(stockObj.minQty);
            $('#stockModal').modal('show');
            stockHistory(stockObj)
        });
    };

    //stock history details
    var stockHistory = function (stockObj) {
        var $el = $("#tbl_stock_history > tbody");
        $el.empty();
        $.each(stockObj.stockHistoryDTOs, function (index, obj) {
            console.log(obj);
            var $row = "<tr> " +
                "<td>" + (index + 1) + "</td>" +
                "<td>" + $.datepicker.formatDate('yy-mm-dd', new Date()) + "</td>" +
                "<td>" + obj.description + "</td>" +
                "<td>" + obj.userName + "</td>" +
                "<td>" + obj.beforeQuantity + "</td>" +
                "<td>" + obj.afterQuantity + "</td>" +
                "</tr>";
            $el.append($row);
        });
    };

    var loadStockDetail = function (index) {
        getStockAddModalEditView(index)
    };

    /*********************************************************************
     * Stock Table
     *********************************************************************/
    var stockLevelDetails = function () {
        var $el = $("#tbl_location_stock_level > tbody");
        $el.empty();
        console.log(stockItems)
        if( stockItems.length > 0 ){      	
	        $.each(stockItems, function (index, obj) {
	            var $row = "<tr> " +
	                "<input type='hidden' class='clsRowIndex'       name='stockDTOs[" + index + "].stockRowIndex'  value='" + CustomComponents.nullValueReplace(index) + "'> " +
	                "<input type='hidden' class='clsStockId'        name='stockDTOs[" + index + "].id' value='" + CustomComponents.nullValueReplace(obj.id) + "'>  " +
	                "<input type='hidden' class='clsStockPartId'    name='stockDTOs[" + index + "].partId'> " +
	                "<input type='hidden' class='clsStockSiteId'    name='stockDTOs[" + index + "].siteId' value='" + CustomComponents.nullValueReplace(obj.siteId) + "'> " +
	                "<input type='hidden' class='clsStockBusinessId'name='stockDTOs[" + index + "].businessId' value='" + CustomComponents.nullValueReplace(obj.businessId) + "'> " +
	                "<input type='hidden' class='clsStockQtyOnHand' name='stockDTOs[" + index + "].qtyOnHand' value='" + CustomComponents.nullValueReplace(obj.qtyOnHand) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].minQty' value='" + CustomComponents.nullValueReplace(obj.minQty) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].warehouseId' value='" + CustomComponents.nullValueReplace(obj.warehouseId) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].warehouseName' value='" + CustomComponents.nullValueReplace(obj.warehouseName) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].description' value='" + CustomComponents.nullValueReplace(obj.description) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].batchNo' value='" + CustomComponents.nullValueReplace(obj.batchNo) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].sellingPrice' value='" + CustomComponents.nullValueReplace(obj.sellingPrice) + "'> " +
	                "<input type='hidden' class='clsStockMinQty'    name='stockDTOs[" + index + "].buyingPrice' value='" + CustomComponents.nullValueReplace(obj.buyingPrice) + "'> " +
	                "<td>" + (index + 1) + "</td>" +
	                "<td>" + CustomComponents.nullValueReplace(obj.warehouseName) + "</td>" +
	                "<td>" + CustomComponents.nullValueReplace(obj.batchNo) + "</td>" +
	                "<td>" + CustomComponents.nullValueReplace(obj.qtyOnHand) + "</td>" +
	                "<td>" + CustomComponents.nullValueReplace(obj.minQty) + "</td>" +
	                "<td class='center'>" +
		                "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
			                ButtonUtil.getCommonBtnEdit("StockTab.loadStockDetail",  index) +
			                ButtonUtil.getCommonBtnDelete("StockTab.deleteStockItem", index) +
		                "</div>" +
		            "</td>"+
	                "</tr>"
	            $el.append($row);
	            appendStockNotification( index, obj );
	            
	        });
        }else{
        	$el.append("<tr><td colspan='7' ><center> Table Data Not Found </center></td></tr>");
        }
    };
    
    var appendStockNotification = function (rowCount, stock) {
       var stockNotification; 
       if (stock.stockNotificationDTOs != null && stock.stockNotificationDTOs.length > 0) {
           for (var row = 0; row < stock.stockNotificationDTOs.length; row++) {
               var notification = stock.stockNotificationDTOs[row]; 
               stockNotification = 
               	"<tr id='row_" + row + "' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].id' value='" + CustomComponents.nullValueReplace( notification.id ) + "' type='hidden'>" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].version' value='" + CustomComponents.nullValueReplace( notification.version ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].stockId' value='" + CustomComponents.nullValueReplace( notification.stockId ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].userId' value='" + CustomComponents.nullValueReplace( notification.userId ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].version' value='" + CustomComponents.nullValueReplace( notification.version ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].notifyOnStockAdd' value='" + CustomComponents.nullValueReplace( notification.notifyOnStockAdd ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].notifyOnStockRemove' value='" + CustomComponents.nullValueReplace( notification.notifyOnStockRemove ) + "' type='hidden' >" +
	               	"<input name='stockDTOs[" + rowCount + "].stockNotificationDTOs[" + row + "].notifyOnMinQty' value='" + CustomComponents.nullValueReplace( notification.notifyOnMinQty ) + "' type='hidden' >" +
               	"</tr>";
                $('#tbl_location_stock_level > tbody:last-child').append(stockNotification);
           } 
       } 
   };

    var deleteStockItem = function (index) {
        stockItems.splice(index, 1);
        stockLevelDetails();
    };

    return {
        init: function () {
          //  initBtnStockBusiness();
           // initBtnStockSite();
        	initBtnStockWarehouse();
        },
        
        getStockAddModalView: function () {
            getStockAddModalView();
        },
        
        getBusinessDataTableModal: function () {
            getBusinessDataTableView();
        },
        
        addBusiness: function (id, name) {
            addBusiness(id, name);
        },
        
        getSiteByBizDataTableModal: function () {
            getSiteByBizDataTableModal();
        },
        
        addSite: function (id, name) {
            addSite(id, name);
        },

        addNewOrUpdatePartStock: function () {
            addNewOrUpdatePartStock();
        },

        stockLevelDetails: function () {
            stockLevelDetails();
        },
        
        viewStockDetail: function (stockItems, eventObj) {
            viewStockDetail(stockItems, eventObj);
        },
        
        loadStockDetail: function (index) {
            loadStockDetail(index);
        },

        deleteStockItem: function (index) {
            deleteStockItem(index);
        },
        getWarehouseByBusiness:function(){
        	getWarehouseByBusiness();
        }

    };
}();

/***********************************************************
 * init Stock
 * *********************************************************/
var initStock = function () {

    /*init dropdown*/
    /*   var initBusinessDropdown = function () {
        $("#businessId").select2({
            dropdownParent: $("#stockModalBody"),
            placeholder: "Select Business",
            allowClear: true
        });
    };
    var initSiteDropdown = function () {
        $("#siteId").select2({
            dropdownParent: $("#stockModalBody"),
            placeholder: "Select Site",
            allowClear: true
        });
     };*/

    /*business_list*/
    /*    var getBusinessAndSiteList = function () {
        var $bl = $("#businessId");
        $bl.empty();
        $.each(businesses, function (index, bizObj) {
            $bl.append($("<option></option>").attr("value", bizObj.businessId).text(bizObj.businessName));
            if (index == 0) {
                Stock.getSiteByBusiness(bizObj.businessId)
            }
        });
     };*/

    return {
        /* initModalDropdown: function () {
            initBusinessDropdown();
            initSiteDropdown();
         },*/
        /* getBusinessAndSiteList: function () {
            getBusinessAndSiteList();
         }*/
    };
}();

/***********************************************************
 * Stock
 * *********************************************************/
var Stock = function () {

    /*
    var getSiteByBusiness = function (businessId) {
        $.ajax({
            type: "GET",
            url: '../restapi/asset/getSitesForBusiness?businessId=' + businessId,
            dataType: 'json',
            success: function (json) {
                var $au = $("#siteId");
                $au.empty();
                $.each(json, function (index, obj) {
                    $('#tbl_location_stock_level > tbody  > tr .clsStockSiteId').each(function (index, rowObj) {
                        if ($(rowObj).val() == obj.id) {
                            obj = null;
                            return false;
                        }
                    });
                    if (obj != null) {
                        $au.append($("<option></option>").attr("value", obj.id).text(obj.name));
                    }
                });
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status + " " + thrownError)
            }
        }).done(function (data) {
            $("#txtStockPart").val($("#partName").val());
            initStock.initModalDropdown();
        });
    };
     */

    return {
        /* Stock.initModalDropdown: function () {
            initStock.initModalDropdown();
        },
         Stock.getBusinessAndSiteList: function () {
        	initStock.getBusinessAndSiteList();
         },*/
        /* getSiteByBusiness: function (businessId) {
            getSiteByBusiness(businessId);
         },*/

    };
}();


/***********************************************************
 * Add Stock
 * *********************************************************/
var AddStock = function () {
    function addStockItem(obj) {
        var stock = {};
        stock['rowIndex'] = obj.rowIndex;
        stock['id'] = obj.id;
        stock['partName'] = obj.partName;
        stock['stockPartId'] = obj.stockPartId;
        stock['site'] = obj.site;
        stock['siteId'] = obj.siteId;
        stock['businessId'] = obj.businessId;
        stock['businessRef'] = obj.businessRef;
        stock['siteId'] = obj.siteId;
        stock['qtyOnHand'] = obj.qtyOnHand;
        stock['minQty'] = obj.minQty;
        stock['other'] = obj.other;
        return stock;
    };
    var addNewStock = function (stockItems) {
        var newStock = {
            id: $("#stockId").val(),
            rowIndex: stockItems.length,
            partName: "",
            stockPartId: $("#txtStockPart").val(),
            site: $("stockSiteName").val(),
            siteId: $("#stockSiteId").val(),
            businessRef: $('#stockBusinessName').val(),
            businessId: $("#stockBusinessId").val(),
            qtyOnHand: $("#txtQtyOnHand").val(),
            minQty: $("#txtMinQty").val(),
            other: false
        };
        return addStockItem(newStock);
    };
    var updateStock = function (currentRowIndex, stockItems) {
        for (var i = 0; i < stockItems.length; i++) {
            if (currentRowIndex == i) {
                console.log("This is saved Stocks");
                stockItems.splice(i, 1);
                return addStockItem(stockItems[i]);
            }
        }

    }; 

    return {
    	addNewStock: function (stockItems) {
            return addNewStock(stockItems);
        },
        updateStock: function (currentRowIndex, stockItems) {
            updateStock(currentRowIndex, stockItems);
        }
    };
}();


/***********************************************************
 * View Stock
 * *********************************************************/
var ViewStock = function () {

    //stock details
    var viewStockDetail = function (stockItems, eventObj) {
        var stockId = $(eventObj).closest("tr").find(".clsStockId").val();
        if (stockId != undefined && stockId != null && stockId != "" )
            {
            $.ajax({
                type: "GET",
                url: '../restapi/stock/find?id=' + stockId,
                dataType: 'json',
                success: function (jsonResult) {
                    setToStock(jsonResult, eventObj);
                }
            }).done(function (data) {
                // $('#stockModal').modal('show');
            })
        }
        else
            {
                var des = ($(eventObj).closest("li")).find(".clsRowIndex").val();
                for (var i = 0; i < stockItems.length; i++) {
                    if (des === stockItems[i].description) {
                        setToStock(stockItems[i], eventObj);
                    }
                }
            }
    };

    //sites for  business
    var setToStock = function (stockObj, eventObj) {
        $.ajax({
            type: "GET",
            url: '../restapi/asset/getSitesForBusiness?businessId=' + $(eventObj).closest("tr").find(".clsStockBusinessRef").val(),
            dataType: 'json',
            success: function (json) {
                var $au = $("#siteId");
                $au.empty();
                $.each(json, function (index, stockObj) {
                    $au.append($("<option></option>").attr("value", stockObj.id).text(stockObj.name));
                });
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status + " - " + thrownError)
            }
        }).done(function (data) {
            $("#stockId").val(stockObj.id);
            $("#stockRowIndex").val($(eventObj).closest("tr").find(".clsRowIndex").val());
            $("#txtStockPart").val(stockObj.partName);
            $("#siteId").val(stockObj.siteId);
            $("#txtQtyOnHand").val(stockObj.qtyOnHand);
            $("#txtMinQty").val(stockObj.minQty);
            $('#stockModal').modal('show');
            stockHistory(stockObj)
            // $.each(json, function (index, stock_obj) {
            //     $au.append($("<option></option>").attr("value", stock_obj.id).text(stock_obj.name));
            // });
        });


    };

    //stock history details
    var stockHistory = function  (stockObj) {
        var $el = $("#tbl_stock_history > tbody");
        $el.empty();
        $.each(stockObj.stockHistoryDTOs, function (index, obj) {
            console.log(obj);
            var $row = "<tr> " +
                "<td>" + (index + 1) + "</td>" +
                "<td>" + $.datepicker.formatDate('yy-mm-dd', new Date()) + "</td>" +
                "<td>" + obj.description + "</td>" +
                "<td>" + obj.userName + "</td>" +
                "<td>" + obj.beforeQuantity + "</td>" +
                "<td>" + obj.afterQuantity + "</td>" +
                "</tr>";
            $el.append($row);
        });

    }


    return {
        viewStockDetail: function (stockItems, obj) {
            initStock.initModalDropdown();
            // _init_stock._init_business_list();
            viewStockDetail(stockItems, obj);
        }

    };
}();