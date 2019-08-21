package com.neolith.focus.util.search.biz;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class NotificationSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static NotificationSearchPropertyMapper instance = null;

    private NotificationSearchPropertyMapper() {
    }

    public static NotificationSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new NotificationSearchPropertyMapper();
        }

        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {
        switch (column) {
            case "subject":
                addColumns("subject");
                break;
            case "content":
                addColumns("content");
                break;
            default:
                break;
        }
    }

}
