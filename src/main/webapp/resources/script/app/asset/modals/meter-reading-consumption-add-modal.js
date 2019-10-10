var MeterReadingConsumptionAddModal = function () {
	
	var initButtons = function () {		
		$('#btn-add-meter-reading-consumption').on('click', function () {
			MeterReadingConsumptionAddModal.addAssetMeterReadingConsumption();			
	    });	
	};	
	
	var addAssetMeterReadingConsumption = function() {		
		if ( $('#meter-reading-consumption-add-frm').valid() ) {
			MeterReadingConsumptionAddModal.addMeterReadingConsumption();	
		}		
	};	
    
    var initValidator = function () {
        var form = $('#meter-reading-consumption-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
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
                    maxlength:1
                },
                consumptionValue: {
                    required: true,
                    number:true
                }
            },
            messages: {
            	consumptionVariable:{ 
                    required: "Please Specify Varible Name",
                    maxlength:"Maximum limit exceed"	
            	},
            	consumptionValue: {
                    required: "Please Insert Consumption Value",
                    number:"Please Insert Numeric Value"
            	}
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
   
    
    var resetConsumptionTable= function () {
        if (meterReadingConsumptionList.length > 0) {
            var row, meterReadingConsumption;
            $("#meter-reading-consumption-tbl > tbody").html("");
            for (row = 0; row < meterReadingConsumptionList.length; row++) {
            	meterReadingConsumption = meterReadingConsumptionList[row];
                var html = "<tr id='consumption_row_" + row + "' >" +
                "<input id='meterReadingConsumptionList_" + row + "_variable'  value='" + meterReadingConsumption.variable + "' type='hidden' >" +
                "<input id='meterReadingConsumptionList_" + row + "_value'  value='" + meterReadingConsumption.value + "' type='hidden' >" +
                "<td><span>" + ( row + 1 ) + "</span></td>" +
                "<td>" +meterReadingConsumption.variable+"</td>"+
                "<td>" +meterReadingConsumption.value+"</td>"+
                "<td>" +
	                "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
	                	ButtonUtil.getCommonBtnDelete("MeterReadingConsumptionAddModal.removeMeterReadingConsumption", row) +
	                "</div>" +
                "</td>" +
                "</tr>";
            $('#meter-reading-consumption-tbl > tbody:last-child').append(html);
            $('#formula').prop('readonly', false);
            getValueList();
            checkParamId();
            $("consumptionList").val(meterReadingConsumptionList) ;
            }
        } else {
        	$('#formula').prop('readonly', true);
            $("#meter-reading-consumption-tbl  > tbody").html("<tr><td colspan='6' align='center'>Please Add Asset Meter Reading for the Consumption.</td></tr>");
        }
       

    };    
    var meterReadingConsumptionList=[];


    var addMeterReadingConsumption=function(){
	  var meterReadingConsumption={
			  id:$('#cosumptionId').val(),
			  index:meterReadingConsumptionList.length,
			  version:$('#cosumptionVersion').val(),
			  variable:$('#consumptionVariable').val(),
			  value:$('#consumptionValue').val(),
	  }
	  if(checkVariableDuplicate(meterReadingConsumption)){
		  meterReadingConsumptionList.push(meterReadingConsumption);
		  $('#meter-reading-consumption-modal').modal("toggle")
		  resetConsumptionTable();  
	  }else{
		  alert("Variable Name Already Assinged");
	  }

    }
    var checkVariableDuplicate=function(meterReadingConsumption){
        for (var i = 0; i < meterReadingConsumptionList.length; i++) {
            if (meterReadingConsumptionList[i].variable == meterReadingConsumption.variable) {
            	return false;
            }
        }
    	return true;

    }
    var removeMeterReadingConsumption=function(index){
        for (var i = 0; i < meterReadingConsumptionList.length; i++) {
            if (meterReadingConsumptionList[i].index == index) {
            	if(!entry.includes(meterReadingConsumptionList[i].variable)){
                	meterReadingConsumptionList.splice(i, 1);
            	}else{
            		alert("Please remove variable from formula before variable definition remove")
            	}
            }
        }
        resetConsumptionTable();
    }

    var checkParamId=function(){   
        var entry=$("#formula").val();
        var value=0;
    	//if(!charcterNotDefined()){
    		value=math.evaluate(entry, scope);
    	//}else{
    		//alert("Not defined variable included in formula")
    	//} 	
    	$("#value").val(value)
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
        	resetConsumptionTable();
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
        removeMeterReadingConsumption:function(index){
        	removeMeterReadingConsumption(index);
        },
        resetConsumptionTable:function(){
        	resetConsumptionTable();
        }
        
   };

}();