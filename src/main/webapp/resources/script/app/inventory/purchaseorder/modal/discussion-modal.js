/**
 * 
 */
var DiscussionAddModal = function () {
	
	var initDescriprion=function(){

		CKEDITOR.editorConfig = function (config) {
		    config.language = 'es';
		    config.uiColor = '#F7B42C';
		    config.height = 300;
		    config.toolbarCanCollapse = true;

		};
		CKEDITOR.replace('poDiscussionComment');
	}
    return {
        init: function () {
            initDescriprion();
        }

    }
}();