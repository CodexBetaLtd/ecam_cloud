package com.neolith.focus.util;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.neolith.focus.model.admin.User;
import com.neolith.focus.params.VelocityMail;

@Component
public class VelocityEmailSender {

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private JavaMailSender mailSender;

    public void sendEmail(VelocityMail velocityMail) {

        try {
            MimeMessagePreparator preparator = getMessagePreparator(velocityMail);
            mailSender.send(preparator);
            System.out.println("Message has been sent.............................");
        } catch (MailException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }

    public MimeMessagePreparator getMessagePreparator(VelocityMail velocityMail){
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setSubject(velocityMail.getSubject());
                helper.setTo(velocityMail.getTo());
                
                if(velocityMail.getFileName() != null && velocityMail.getAttachmentFile() != null){
                    helper.addAttachment(velocityMail.getFileName(), velocityMail.getAttachmentFile());
                }
                String text = geVelocityTemplateContent(velocityMail.getModel(),velocityMail.getVmTemplate());//Use Freemarker or Velocity
                System.out.println("Template content : " + text);
 
                // use the true flag to indicate you need a multipart message
                helper.setText(text, true);
 
                //Additionally, let's add a resource as an attachment as well.
              //  helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));
 
            }
        };
        return preparator;
    }
    
    @SuppressWarnings({ "deprecation" })
	public String geVelocityTemplateContent(Map<String, Object> model,String template){
        StringBuffer content = new StringBuffer();
        try{
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailtemplate/"+template+".vm", model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing velocity template : " + e.getMessage());
        }
          return "";
    }

    public void sendEmail(String token, User user, String appUrl) {
        VelocityMail mail = createVelocityMail(token, user, appUrl);
        sendEmail(mail);
    }

    private VelocityMail createVelocityMail(String token, User user, String appUrl) {
        VelocityMail velocityMail = new VelocityMail();

        velocityMail.getModel().put("userName", user.getUserCredential().getUserName());
        velocityMail.getModel().put("name", user.getFullName());
        velocityMail.getModel().put("appUrl", appUrl);
        velocityMail.getModel().put("restTokenUrl", token);
        velocityMail.setSubject("Password reset token");
        velocityMail.setTo(user.getEmailAddress());
        velocityMail.setVmTemplate("passwordresettoken");

        return velocityMail;
    }
    
}
