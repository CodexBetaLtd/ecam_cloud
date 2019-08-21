/**
 * Created by neo89 on 12/7/16.
 */
var Setting = function () {

    var userSiteListSelect = function () {
        $("#siteSelector").select2({
            placeholder: "Select Site",
            allowClear: true
        });

    };

    return {
        init: function () {
        },

        loadUserSiteList: function () {
            userSiteListSelect()
        }
    };
}();

function loadFacilityList(){
    var url = 'site';
    $.get(url, function( data ) {
        $("#siteModalBody").html(data);
    });
}



