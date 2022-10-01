//$(function() {
  $("#accordion").on('show.bs.collapse', function(e) {
//	 console.log(e.target.id);
	 
	  var tableId=$('#'+e.target.id).find('table').attr('id');
//	  console.log(tableId); 
//	  $('#'+tableId).dataTable().draw();
	  
//	  $('table').colResizable({ disable : true });
//	  $('#'+tableId).colResizable({
//     	    liveDrag:true,
//    	    resizeMode:'fit',         	    
// 	  });  
  })
//});