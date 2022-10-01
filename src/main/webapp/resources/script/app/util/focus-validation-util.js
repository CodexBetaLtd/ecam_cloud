/**
 * This JS used to manage the general validation javaScript module for the FOCUS application
 */

var CustomValidation = function () {

    var formatStringWithQuotationMark = function (val) {
        if (val != null && val != undefined) {
            return val.replace(/(['"])/g, "\\$1");
        }
        return val;
    };

    var nullValueReplaceByDefault = function (val, defaultVal) {
        if (val == null || val == undefined || val == "null" || 0 === val.length) {
            return defaultVal
        } else {
            return val;
        }
    };

    var nullValueReplace = function (val) {
        return nullValueReplaceByDefault(val, "");
    };

    var nullValueReplaceByZero = function (val) {
        return nullValueReplaceByDefault(val, 0);
    };

    var validateFieldNull = function (obj, field, value) {
        if (value != null && value != undefined && value != "null") {
            obj[field] = value;
        } else {
            obj[field] = "";
        }
    };

    var isBooleanTrue = function (val) {
        if (val != null && val != undefined) {
            if (val == "true" || val) {
                return true;
            }
        }
        return false;
    };

    var isBooleanFalse = function (val) {
        if (val != null && val != undefined) {
            if (val == "false" || !val) {
                return true;
            }
        }
        return false;
    };

    var validateIsEmpty = function (obj) {
        if (obj != undefined && $(obj).val().trim() === '') {
            return true
        } else {
            return false;
        }
    };

    var validateIsNull = function (obj) {
        if (obj != undefined && $(obj).val() == null) {
            return true
        }
        else {
            return false;
        }
    };

    var validateIsNullAndEmpty = function (obj) {
        if (validateIsEmpty(obj) && validateIsNull(obj)) {
            return true
        }
        else {
            return false;
        }
    };

    var validateIsNullOrEmpty = function (obj) {
        if (validateIsEmpty(obj) || validateIsNull(obj)) {
            return true
        }
        else {
            return false;
        }
    };

    return {

        validateFieldNull: function (obj, field, value) {
            return validateFieldNull(obj, field, value);
        },

        isEmptyValueById: function (obj) {
            return validateIsEmpty(obj);
        },

        validateIsNullById: function (obj) {
            return validateIsNull(obj);
        },

        validateIsNullAndEmpty: function (obj) {
            return validateIsNullAndEmpty(obj);
        },

        validateIsNullOrEmpty: function (obj) {
            return validateIsNullOrEmpty(obj);
        },

        nullValueReplace: function (val) {
            return nullValueReplace(val);
        },

        nullValueReplaceByZero: function (val) {
            return nullValueReplaceByZero(val);
        },

        nullValueReplaceByDefault: function (val, defaultVal) {
            return nullValueReplaceByDefault(val, defaultVal);
        },

        isBooleanTrue: function (val) {
            return isBooleanTrue(val);
        },

        isBooleanFalse: function (val) {
            return isBooleanFalse(val);
        },

        formatStringWithQuotationMark: function (val) {
            return formatStringWithQuotationMark(val);
        }
    };

}();


