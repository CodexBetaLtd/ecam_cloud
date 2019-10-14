jQuery(document).ready(function () {

    Main.init();
    AppAdd.init();
    AppMenuTab.init();
    RelatedAppTab.init();
    AppWigetTab.init();

    $.fn.modalmanager.defaults.resize = true;
    $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
        '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">'
        + '<div class="progress progress-striped active">'
        + '<div class="progress-bar" style="width: 100%;"></div></div></div>';

});
