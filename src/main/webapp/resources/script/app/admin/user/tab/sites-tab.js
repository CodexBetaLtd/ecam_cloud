var TabSites = function () {

    var loadSiteModal = function () {
        if ($('#businessId').val() != "") {
            var $modal = $('#common-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../userProfile/sitemodalview?id=' + $('#businessId').val();
                $modal.load(url, '', function () {
                	SiteAddModal.init();
                    Main.runCheckBoxStyle();                    
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert('Please select a business first');
        }
    };


    var resetSiteHtmlTable = function () {
        if (sites.length > 0) {
            var row, site;
            $("#tblUserSite> tbody").html("");

            for (row = 0; row < sites.length; row++) {
				site = sites[row];
				var group = "";
				for (var i = 0; i < site.siteUserGroupDTOList.length; i++) { 
					group += "<input id='userSiteDTOList" + row + ".siteUserGroupDTOList' name='userSiteDTOList[" + row + "].siteUserGroupDTOList[" + i + "].id' value='" + site.siteAssignedUserGroups[i] + "' type='hidden' >"
			    } 
				var html = "<tr id='row_"+ row + "' >" + 
				"<input id='userSiteDTOList" + row + ".siteId' name='userSiteDTOList[" + row + "].siteId' value='" + site.siteId + "' type='hidden'>"+ 
				"<input id='userSiteDTOList" + row + ".siteSiteId' name='userSiteDTOList[" + row + "].siteSiteId' value='" + site.siteSiteId + "' type='hidden'>" + 
				"<input id='userSiteDTOList" + row + ".siteSiteName' name='userSiteDTOList[" + row + "].siteSiteName' value='" + site.siteSiteName + "' type='hidden'>" +
				"<input id='userSiteDTOList" + row + ".siteIndex' name='userSiteDTOList[" + row + "].siteIndex' value='" + site.siteIndex + "' type='hidden'>" +
				group +
				"<td> <span>" + (row + 1) + "</span></td>" + 
				"<td class='hidden-xs'><span>" + site.siteSiteName + "</span></td>" +
				"<td class='center'>" +
                "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                ButtonUtil.getCommonBtnEdit("TabSites.editSite", site.siteIndex) +
                ButtonUtil.getCommonBtnDelete("TabSites.removeSite", site.siteIndex) +
                "</div>" +
                "</td></tr>";

                $('#tblUserSite> tbody:last-child').append(html);
			}
		} else {
            CustomComponents.emptyTableCustomeMessageRow('tblUserSite', 7, "Please Add Sites for the User.");
        }
    };

    var setCommonDataToSiteObj = function (site, siteObj) {
    	CustomValidation.validateFieldNull(siteObj, 'siteId', site.siteId);
    	CustomValidation.validateFieldNull(siteObj, 'siteSiteId', site.siteSiteId);
    	CustomValidation.validateFieldNull(siteObj, 'siteSiteName', site.siteSiteName);
    	CustomValidation.validateFieldNull(siteObj, 'siteUserGroupDTOList', site.siteUserGroupDTOList);
    	CustomValidation.validateFieldNull(siteObj, 'siteAssignedUserGroups', site.siteAssignedUserGroups);
    	CustomValidation.validateFieldNull(siteObj, 'version', site.version);
    };
    
    var getUserGroup = function(site){
    	var group =[];
	    for(var i = 0 ; i < site.siteUserGroupDTOList.length; i++){
	    	if(site.siteUserGroupDTOList[i].id){
	    		group.push(site.siteUserGroupDTOList[i].id);
	    	}else{
	    		group.push(site.siteUserGroupDTOList[i]);
	    	}	    	
	    }
	    site['siteAssignedUserGroups'] = group;
    }

    var getSiteByIndex = function (siteIndex) {
        for (var i = 0; i < sites.length; i++) {
            if (sites[i].siteIndex == siteIndex) {
                return sites[i];
            }
        }
    };
    
    var getCheckedGroups = function () {
        var assignedGroups = [];
        $('input:checkbox.userGroupCls').each(function () {
            if (this.checked) {
                assignedGroups.push($(this).val());
            }
        });
        return assignedGroups;
    }

    var addSite = function () {
    	
    	var site = {};

        site['siteId'] = $('#id').val();
        site['siteIndex'] = $('#siteIndex').val();
        site['siteSiteId'] = $('#siteId').val();
        site['siteSiteName'] = $('#siteId').select2('data')[0].text;
        site['version'] = $('#siteVersion').val();
        site['siteAssignedUserGroups'] = getCheckedGroups();
        site['siteUserGroupDTOList'] = getCheckedGroups();

        if (!isSiteAlreadyAdd(site)) {
            addSiteToList(site);
            resetSiteHtmlTable();
            $('#common-modal').modal('toggle');
        } else {
            alert('Duplicate site !');            
        }
        
    };

    var addSiteToList = function (site) {

        var siteObj = {}

        if (site.siteIndex != null && site.siteIndex != "" && site.siteIndex >= 0) {
            siteObj = getSiteByIndex(site.siteIndex);
            setCommonDataToSiteObj(site, siteObj);
        } else {
            if (sites.length == 0) {
                siteObj['siteIndex'] = 0;
            } else {
            	CustomValidation.validateFieldNull(siteObj, 'siteIndex', sites.length);
            }
            getUserGroup(site);
            setCommonDataToSiteObj(site, siteObj);
            sites.push(siteObj);
        }
    };

    var editSite = function (siteIndex) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
        	var url = '../userProfile/sitemodalview?id=' + $('#businessId').val();        
            $modal.load(url, '', function () {           
                fillSiteEditForm(getSiteByIndex(siteIndex));
                Main.runCheckBoxStyle();
                SiteAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var fillSiteEditForm = function (site) {
        $('#siteIndex').val(site['siteIndex']);
        $('#id').val(site['siteId']);
        $('#siteId').val(site['siteSiteId']);
        $('#siteName').val(site['siteSiteName']);
        $('#siteVersion').val(site['version']);    
        setUserGroup(site['siteAssignedUserGroups']);      
    };

    var setUserGroup = function (userGroupList) {
        for (var j = 0; j < themaleafUserGroup.length; j++) {
            for (var i = 0; i < userGroupList.length; i++) {
                if (userGroupList[i] == themaleafUserGroup[j].id) {
                    $("#group_" + themaleafUserGroup[j].id).iCheck('check');
                }
            }
        }
    };

    var isSiteAlreadyAdd = function (site) {
    	if ( site.siteIndex == null || site.siteIndex == "" || site.siteIndex < 0 ) {
    		
    		for (var i = 0; i < sites.length; i++) {            	
            	if (sites[i].siteSiteId == site.siteSiteId) {
     	        	return true;
     	        }
            }
    		
    		return false;
    	}
    	
    	return false;        
    };

    var removeSite = function (siteIndex) {
        for (var i = 0; i < sites.length; i++) {
            if (sites[i].siteIndex == siteIndex) {
                sites.splice(i, 1);
                updateIndexes();
                resetSiteHtmlTable();
                break;
            }
        }
    };

    var updateIndexes = function () {
        for (var i = 0; i < sites.length; i++) {
            sites[i].siteIndex = i;
        }
    };

    return {
    	
        loadSiteModal: function () {
            loadSiteModal();
        },

        addSite: function () {
            addSite();
        },

        addSiteToList: function (site) {
            addSiteToList(site);
        },

        removeSite: function (index) {
            removeSite(index);
        },

        editSite: function (index) {
            editSite(index);
        },

        resetSiteHtmlTable: function () {
            resetSiteHtmlTable();
        },

        getCheckedGroups: function () {
            getCheckedGroups();
        },
    };
}();