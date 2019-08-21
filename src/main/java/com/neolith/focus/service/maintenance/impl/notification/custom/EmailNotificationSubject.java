package com.neolith.focus.service.maintenance.impl.notification.custom;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;

import java.util.ArrayList;

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
