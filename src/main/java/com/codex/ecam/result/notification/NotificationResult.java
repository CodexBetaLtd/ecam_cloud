package com.codex.ecam.result.notification;

import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.result.BaseResult;

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
