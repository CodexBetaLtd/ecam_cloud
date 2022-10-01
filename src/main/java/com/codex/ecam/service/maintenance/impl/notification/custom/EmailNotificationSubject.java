package com.codex.ecam.service.maintenance.impl.notification.custom;

import java.util.ArrayList;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;

public class EmailNotificationSubject implements Subject {


    private ArrayList<EmailNotificationObserver> emailNotificationObservers = new ArrayList<EmailNotificationObserver>();
    private WorkOrderDTO workOrderDTO;

    public EmailNotificationSubject(WorkOrderDTO workOrderDTO) {
        this.workOrderDTO = workOrderDTO;
    }

    public WorkOrderDTO getWorkOrderDTO() {
        return workOrderDTO;
    }

    public void setWorkOrderDTO(WorkOrderDTO workOrderDTO) {
        this.workOrderDTO = workOrderDTO;
        notifyObservers();
    }

    @Override
    public void registerObserver(EmailNotificationObserver emailNotificationObserver) {
        emailNotificationObservers.add(emailNotificationObserver);
    }

    @Override
    public void removeObserver(EmailNotificationObserver emailNotificationObserver) {
        emailNotificationObservers.remove(emailNotificationObserver);
    }

    @Override
    public void notifyObservers() {
        for (EmailNotificationObserver emailNotificationObserver : emailNotificationObservers) {
            emailNotificationObserver.update(this.workOrderDTO);
        }

    }

}
