var MeterReadingConsumptionVariableAddModal = function () {

    /********************************************
     * Initialize buttons
     ********************************************/

    function initInputClearComponents(){
        initInputClearMeterReadingUnit()
    };
    
    function initInputClearMeterReadingUnit(){
        $("#consumptionMeterReadingUnitName").inputClear({
            placeholder:"Please specify a unit",
            btnMethod:"MeterReadingConsumptionVariableAddModal.addMeterReadingUnit()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalViewMeterReadingUnitSelect() {
        var $modal = $('#consumption-child-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/view/modal/meter-reading-units';
            $modal.load(url, '', function () {
                DataTableModalMeterReadingUnits.init(
                        "consumption-child-modal",
                        "tbl_meter_reading_units",
                        "../../restapi/meterreading-units/tabledata",
                        "setCunsumptionData"
                        );
                $modal.modal();
            });
        }, 1000);
    };

	var initButtons = function () {		
		$('#btn-add-meter-reading-consumption-variable').on('click', function () {
			MeterReadingConsumptionVariableAddModal.addAssetMeterReadingConsumption();			
	    });	
	};	
	
	var addAssetMeterReadingConsumption = function() {		
		if ( $('#meter-reading-consumption-variable-add-frm').valid() ) {
			MeterReadingConsumptionVariableAddModal.addMeterReadingConsumption();	
		}		
	};
    
    var initValidator = function () {
        
        var form = $('#meter-reading-consumption-variable-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        
        jQuery.validator.addMethod("alphanumeric", function(value, element) {
            return this.optional(element) || /^\w+$/i.test(value);
        }, "Letters, numbers, and underscores only please");
        
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
            	if (element.attr("type") == "radio" || element.attr("type") == "checkbox" ) { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length ){
                	error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
            	consumptionVariable: {
                    required: true,
                    alphanumeric:true
                },
                consumptionMeterReadingUnitName: {
                    required: true,
                }
            },
            messages: {
            	consumptionVariable:{ 
                    required: "Please Specify Varible Name",
                    alphanumeric:"Please enter alphanumeric chanters only"	
            	},
            	consumptionMeterReadingUnitName:  "Please Insert Meter Reading Unit",
        
            	
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };
    
    let scope = {}
    
	var meterReadingVariableList = [];
    
	var resetVariableTable = function() {
		if (meterReadingVariableList.length > 0) {
			var row, meterReadingVariable;
			$("#meter-reading-variable-tbl > tbody").html("");
			for (row = 0; row < meterReadingVariableList.length; row++) {
				meterReadingVariable = meterReadingVariableList[row];
				var html = "<tr id='consumption_row_"+ row+ "' >"+ "<input id='meterReadingVariableList_"+ row+ "_variableName'  value='"+ meterReadingVariable.variable
						+ "' type='hidden' >"+ "<input id='meterReadingVariableList_"+ row+ "_version'  value='"+ meterReadingVariable.version+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_meteReadingUnitName'  value='"+ meterReadingVariable.meteReadingUnitName+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_meteReadingUnitId'  value='"+ meterReadingVariable.meteReadingUnitId+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_id'  value='"+ meterReadingVariable.id+ "' type='hidden' >"
						+ "<td><span>"+ (row + 1)+ "</span></td>"
						+ "<td>"+ meterReadingVariable.variable+ "</td>"
						+ "<td>"+ meterReadingVariable.meteReadingUnitName+ "</td>"
						+ "<td>"
						+ "<div class='visible-md visible-lg hidden-sm hidden-xs'>"
						+ ButtonUtil.getCommonBtnDelete("MeterReadingConsumptionVariableAddModal.removeMeterReadingConsumptionVariable",row) 
						+"</div>" 
						+ "</td>"
						+ "</tr>";
				$('#meter-reading-variable-tbl > tbody:last-child').append(html);
			}
		} else {
			$("#meter-reading-variable-tbl  > tbody")
					.html(
							"<tr><td colspan='6' align='center'>Please Define Variable for Meter reading.</td></tr>");
		}

	};
	
    var addMeterReadingConsumption=function(){
		var meterVariable = {
				id : $('#variableId').val(),
				index : meterReadingVariableList.length,
				version : $('#variableVersion').val(),
				variable : $('#consumptionVariable').val(),
				meteReadingUnitId : $('#consumptionMeterReadingUnitId').val(),
				meteReadingUnitName : $('#consumptionMeterReadingUnitName').val()
			}
		
		if(!isVariableAlreadyDefined(meterVariable)){
			meterReadingVariableList.push(meterVariable);
			 $('#meter-reading-consumption-variable-modal').modal('toggle');
			resetVariableTable()
		}else{
			alert("Variable Already defined")
		}
    }
    
    var loadMeterReadingVariable=function(variables){
    	meterReadingVariableList=variables;
    	resetVariableTable();
    }
    
    var isVariableAlreadyDefined=function(consumptionVariable){
        for (var i = 0; i < meterReadingVariableList.length; i++) {
        	if(consumptionVariable.variable==meterReadingVariableList[i].variable){
            	return true;
        	}
        }
    	return false;

    }

    var removeMeterReadingConsumptionVariable=function(index){
        for (var i = 0; i < meterReadingVariableList.length; i++) {
        	if(index==i){
            	meterReadingVariableList.splice(i, 1);
        	}
        }
        resetVariableTable();
    }
    
    var removeMeterReadingAllConsumptionVariable=function(){
        for (var i = 0; i < meterReadingVariableList.length; i++) {
            	meterReadingVariableList.splice(i, 1);
        }
        resetVariableTable();
    }


    var checkParamId = function(){   
        var entry = $("#formula").val();
        var value = 0;      
   		value = math.evaluate(entry, scope);	
    	$("#value").val(value)
    }
    
    var initScope = function(){
    	   var entry=$("#formula").val();
        for(var i=0;i<entry.length;i++){
        	if(isLetter(entry.charAt(i))){
        	scope[entry.charAt(i)]=0
        	}
        }
    }
    
    var getValueList=function(){
        for(var i=0;i<meterReadingConsumptionList.length;i++){
        	scope[meterReadingConsumptionList[i].variable]=meterReadingConsumptionList[i].value
        }
    }
    
    function charcterNotDefined(){
        var entry=$("#formula").val();
    	for(var i=0;i<entry.length;i++){
    		if(isLetter(entry.charAt(i))){
    			if(!getInVariable(entry.charAt(i))){ 				
    				return true;
    			}
    		}
    	}
    }
    function getInVariable(name){
        for(var i=0;i<meterReadingConsumptionList.length;i++){
        	if(meterReadingConsumptionList[i].variable==name){
        		return true;
        	}
        }
    }
    
    function isLetter(c) {
    	  return c.toLowerCase() != c.toUpperCase();
    }
    
   return {

        init: function () {
        	initButtons();
        	initValidator();
        	initInputClearComponents();
        	resetVariableTable();
        },
        
        addAssetMeterReading: function () {
        	addAssetMeterReading();
        },
        
        addMeterReadingConsumption:function(){
        	addMeterReadingConsumption();
        },
        
        addAssetMeterReadingConsumption:function(){
        	addAssetMeterReadingConsumption();
        },
        
        checkParamId:function(){
        	checkParamId();
        },
        
        removeMeterReadingConsumptionVariable:function(index){
        	removeMeterReadingConsumptionVariable(index);
        },
        
        resetConsumptionTable:function(){
        	resetConsumptionTable();
        },
        
        resetVariableTable:function(){
        	resetVariableTable()
        },
        
        loadMeterReadingVariable:function(variables){
        	loadMeterReadingVariable(variables)
        },
        
        removeMeterReadingAllConsumptionVariable:function(){
        	removeMeterReadingAllConsumptionVariable();
        },

        
        addMeterReadingUnit: function () {
            initModalViewMeterReadingUnitSelect();
        }
        
   };

}();