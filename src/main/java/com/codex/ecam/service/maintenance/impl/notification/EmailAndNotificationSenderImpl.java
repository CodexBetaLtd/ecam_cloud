package com.codex.ecam.service.maintenance.impl.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.notification.api.NotificationService;
import com.codex.ecam.util.SendGridUtil;

@Service
public class EmailAndNotificationSenderImpl implements EmailAndNotificationSender {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserDao userDao;

    @Override
    public void sendMail(Integer userId, String subject, String msg) {
        try {
            String email = userDao.findOne(userId).getEmailAddress();
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setSubject(subject);
//            message.setText(msg);
//            mailSender.send(message);
            if(email!=null){
        		SendGridUtil.sendSimpleMessae(subject,msg,email);

            }else{
        		SendGridUtil.sendSimpleMessae(subject,msg,"yapaslk93@gmail.com");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendNotification(Integer userId, String subject, String msg) {
        try {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setSubject(subject);
            notificationDTO.setContent(msg);
            notificationDTO.setSystemMessage(Boolean.TRUE);
            notificationDTO.setReceivedUserId(userId);
            notificationService.saveSystemNotification(notificationDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
}
