jQuery(document).ready(function () {
    Main.init();
    UserGroupAdd.init();
    TabUsers.init();
    
//     var userGroupId=$("#id").val();
//    tree = $('#tree').tree({
//      hasChildrenField: 'anyChildren',
//      dataSource: "../restapi/userGroups/getMenuPermmision?id="+userGroupId,
//    checkboxes: true,
//    cascadeSelection: true,
//    border: true,
//    primaryKey: 'id',
//    lazyLoading: true,
//    checkedField: 'checkedFieldName',
//    selectionType: 'multiple'
//    });
//    $(document).on("click", "#add_permmisions", function () {
//        var checkedIds = tree.getCheckedNodes();
//        console.log(checkedIds)
//        return false;
//    });
//    tree.on('expand', function (e, $node, id) {
//      var i, children, record = tree.getDataById(id);
//      console.log(record)
//
//    });
//    tree.on('expand', function (e, $node, id) {
//        var i, children, record = tree.getDataById(id);
//        if (tree.getChildren($node).length === 0) {
//            if (record.type === 'continent') {
//                children = $.grep(countries, function (i) { return i.continent === record.text; });
//                for (i = 0; i < children.length; i++) {
//                    tree.addNode(children[i], $node);
//                }
//            } else if (record.type === 'country') {
//                children = $.grep(states, function (i) { return i.country === record.text; });
//                for (i = 0; i < children.length; i++) {
//                    tree.addNode(children[i], $node);
//                }
//            }
//        }
//    });
//    tree = $('#tree').tree({
//    	primaryKey: 'id',
//        hasChildrenField: 'anyChildren',
//        dataSource: "../restapi/userGroups/getAllMenu",
//        dataSource: "../restapi/userGroups/getAllMenu",
//        checkboxes: true,
//        cascadeSelection: true,
//        border: true,
//    });
    
//    tree.on('expand', function (e, $node, id) {
//        var i, children, record = tree.getDataById(id);
//        console.log(tree.getChildren($node))
//        if (tree.getChildren($node).length === 0) {
//            if (record.type === 'MENU') {
//            	 $.ajax({
//                async : true,
//                type : "GET",
//                url : "../restapi/userGroups/getAllSubMenuById?id="+id,
//                dataType : "json",    
//                success : function(json) {
//                    children = $.grep(json, function (i) { 
//                    	console.log(json)
//                    	return i.continent === record.text; 
//                    	});
//                    for (i = 0; i < children.length; i++) {
//                        tree.addNode(children[i], $node);
//                    }
//                },    
//                
//
//                error : function(xhr, ajaxOptions, thrownError) {
//                    alert(xhr.status);
//                    alert(thrownError);
//                }
//               
//            });
//
//            } else if (record.type === 'SUBMENU') {
//            	$.ajax({
//            		async : true,
//            		type : "GET",
//            		url : "../restapi/userGroups/getAllPageById?id="+id,
//            		dataType : "json",    
//            		success : function(json) {
//            			children = $.grep(json, function (i) { 
//                        	console.log(json)
//            				return i.continent === record.text; 
//            			});
//            			for (i = 0; i < children.length; i++) {
//            				console.log(children)
//            				tree.addNode(children[i], $node);
//            			}
//            		},    
//            		
//            		
//            		error : function(xhr, ajaxOptions, thrownError) {
//            			alert(xhr.status);
//            			alert(thrownError);
//            		}
//            		
//            	});
//            }
//            else if (record.type === 'PAGE') {
//              	 $.ajax({
//                    async : true,
//                    type : "GET",
//                    url : "../restapi/userGroups/getAllPagePermissionById?id="+id,
//                    dataType : "json",    
//                    success : function(json) {
//                        children = $.grep(json, function (i) { 
//                        	console.log(json)
//                        	return i.continent === record.text; 
//                        	});
//                        for (i = 0; i < children.length; i++) {
//                        	console.log(children)
//                            tree.addNode(children[i], $node);
//                        }
//                    },    
//                    
//
//                    error : function(xhr, ajaxOptions, thrownError) {
//                        alert(xhr.status);
//                        alert(thrownError);
//                    }
//                   
//                });
//               }
//            }
//        
//    });



});



