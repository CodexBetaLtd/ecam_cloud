package com.neolith.focus.result.notification;

import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.result.BaseResult;

public class NotificationResult extends BaseResult<Notification, NotificationDTO> {

    public NotificationResult(Notification domain, NotificationDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }
}
