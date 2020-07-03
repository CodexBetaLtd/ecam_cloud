jQuery(document).ready(function () {

    var assetCategoryTable = {};

    Main.init();
    AssetCategoryHome.init();
    TaxHome.init();
    MeterReadingUnitHome.init();
    MaintenanceTypeHome.init();
    PioritiesHome.init();
    CertificationHome.init();
    CurrencyHome.init();
    AssetEventTypeHome.init();
    BussinessTypeHome.init();
    CountryHome.init();
    BusinessClassificationHome.init();
    AccountHome.init();
    ChargeDepartmentHome.init();
    JobTitleHome.init();
    UserSkillLevelHome.init();
    AssetBrandHome.init();
    AssetModelHome.init();
    MiscellaneousExpenseTypeHome.init();
    BackupTab.init();


    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var target = $(e.target).attr("href") // activated tab
        if ((target == '#siteTab')) {
            UserSite.init();
        }
    });

    $("#loadSiteModal").on("click", function () {
        loadFacilityList();
    });

    $(document).on('click', '#asset-event-type-new', function () {
        AssetEventTypeHome.addModal();
    });

    $(document).on('click', '#business-new', function () {
        BusinessClassificationHome.addModal();
    });

    $(document).on('click', '#business-type-new', function () {
        BussinessTypeHome.addModal();
    });
    
    $(document).on('click', '#country-new', function () {
        CountryHome.addModal();
    });
    
    $(document).on('click', '#meter-reading-new', function () {
        MeterReadingUnitHome.addModal();
    });

    $(document).on('click', '#certification-new', function () {
        CertificationHome.addModal();
    });

    $(document).on('click', '#currency-new', function () {
        CurrencyHome.addModal();
    });

    $(document).on('click', '#maintenance-new', function () {
        MaintenanceTypeHome.addModal();
    });

    $(document).on('click', '#priorities-new', function () {
        PioritiesHome.addModal();
    });

    $(document).on('click', '#account-new', function () {
        AccountHome.addModal();
    });

    $(document).on('click', '#charge-new', function () {
    	ChargeDepartmentHome.addModal();
    });
    
    $(document).on('click', '#job-title-new', function () {
    	JobTitleHome.addModal();
    });
    
    $(document).on('click', '#user-skill-level-new', function () {
    	UserSkillLevelHome.addModal();
    });
    
    $(document).on('click', '#brand-new', function () {
    	AssetBrandHome.addModal();
    });
    
    $(document).on('click', '#model-new', function () {
    	AssetModelHome.addModal();
    });
    
    $(document).on('click', '#miscellaneous-expense-type-new', function () {
    	MiscellaneousExpenseTypeHome.addModal();
    });

});