package com.codex.ecam.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;


public class SendGridUtil {

	private static final String apiKey = "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" ;
	private static Email from = new Email("wasanthabr93@gmail.com");

	
    public static void sendSimpleMessae(String subject,String contentStr,String reciver){

    	   /// String subject = "Sending with Twilio SendGrid is Fun";
    	    Email to = new Email(reciver);
    	    Content content = new Content("text/plain", contentStr);
    	    Mail mail = new Mail(from, subject, to, content);

    	    SendGrid sg = new SendGrid(apiKey);
    	    Request request = new Request();
    	    try {
    	      request.setMethod(Method.POST);
    	      request.setEndpoint("mail/send");
    	      request.setBody(mail.build());
    	      Response response = sg.api(request);
    	    }catch (Exception e) {
				// TODO: handle exception
			}

    }

   


}
