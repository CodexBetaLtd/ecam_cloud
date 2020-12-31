var TabContact = function () {
	
	var initButtons = function () {

		$('#btn-new-customer-contact').on('click', function(){
			TabContact.addContactModal();
	    });
	    
	};
	
	var addContactModal = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../customer/contactaddmodelview';
            $modal.load(url, '', function () {
            	ContactAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };
	
	var editContactModal = function(index) {
		var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../customer/contactaddmodelview';
			$modal.load(url, '', function() {
				fillContactEditForm(contacts[index]);
				ContactAddModal.init();
				$modal.modal();
			});
		}, 1000);
	};

    var initContactHtmlTable = function () {

        if (contacts.length > 0) {
            var row, contact;
            $("#customer-contact-tbl > tbody").html("");

            for (row = 0; row < contacts.length; row++) {
            	contact = contacts[row];            	
            	contact['index'] = row;
            	
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='contacts" + row + ".id' name='contacts[" + row + "].id' value='" + contact.id + "' type='hidden'>" +
                    "<input id='contacts" + row + ".version' name='contacts[" + row + "].version' value='" + contact.version + "' type='hidden' >" +
                    "<input id='contacts" + row + ".name' name='contacts[" + row + "].name' value='" + contact.name + "' type='hidden' >" +
                    "<input id='contacts" + row + ".designation' name='contacts[" + row + "].designation' value='" + contact.designation + "' type='hidden' >" +
                    "<input id='contacts" + row + ".telephone' name='contacts[" + row + "].telephone' value='" + contact.telephone + "' type='hidden' >" +
                    "<input id='contacts" + row + ".email' name='contacts[" + row + "].email' value='" + contact.email + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + contact.name + "</span></td>" +
                    "<td>" + contact.designation + "</td>" +
                    "<td class='hidden-xs'><span>" + contact.telephone + "</span></td>" +
                    "<td>" + contact.email + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TabContact.editContactModal", contact.index) +
                    ButtonUtil.getCommonBtnDelete("TabContact.removeContact", contact.index) +
                    "</div>" +
                    "</td></tr>";

                $('#customer-contact-tbl > tbody:last-child').append(html);
            }
        } else {
        	CustomComponents.emptyTableCustomeMessageRow('customer-contact-tbl', 6, "Please Add Contact for the Customer.");
        }

    };

    var addContactToList = function (contact) {
    	
    	var contactObj = {}
    	if (contact.index != null && contact.index != "" && contact.index >= 0) { 
    		contactObj = contacts[contact.index];            
    		setCommonDataToContactObj(contact, contactObj);    		
    	} else {
    		setCommonDataToContactObj(contact, contactObj);     
        	contacts.push(contactObj);
    	}
    };
    
    var setCommonDataToContactObj = function (contact, contactObj ){
    	CustomValidation.validateFieldNull( contactObj, 'id', contact.id);
    	CustomValidation.validateFieldNull( contactObj, 'name', contact.name);
    	CustomValidation.validateFieldNull( contactObj, 'designation', contact.designation);
    	CustomValidation.validateFieldNull( contactObj, 'telephone', contact.telephone);
    	CustomValidation.validateFieldNull( contactObj, 'email', contact.email);
    	CustomValidation.validateFieldNull( contactObj, 'version', contact.version);
    };
    
    var addContact = function () {

        var contact = {};
        
        contact['id'] = $('#contactId').val();
        contact['version'] = $('#contactVersion').val();
        contact['index'] = $('#contactIndex').val();
        contact['name'] = $('#contactName').val();
        contact['designation'] = $('#contactDesignation').val();
        contact['telephone'] = $('#contactTelephone').val();
        contact['email'] = $('#contactEmail').val();
    	
        addContactToList(contact);
    	
        initContactHtmlTable();

        $('#common-modal').modal('toggle');
    };
    
    var fillContactEditForm = function (contact) {
    	$('#contactId').val(contact['id']);
    	$('#contactVersion').val(contact['version']);
    	$('#contactIndex').val(contact['index']);
    	$('#contactName').val(contact['name']);
    	$('#contactDesignation').val(contact['designation']);
    	$('#contactTelephone').val(contact['telephone']);
    	$('#contactEmail').val(contact['email']);
    };

	var removeContact = function(index) {
		contacts.splice(index, 1);
		initContactHtmlTable();
	};

    return {
    	
    	init: function () {
    		initButtons();
    		initContactHtmlTable();
    	},

        /***********************************************************************
		 * Contact Add
		 **********************************************************************/
    	addContactModal: function () {
    		addContactModal();
        },
        
        addContact: function () {
        	addContact();
        },

        removeContact: function (index) {
        	removeContact(index);
        },
        
        editContactModal: function (index) {
        	editContactModal(index);
        }
        
    };
}();