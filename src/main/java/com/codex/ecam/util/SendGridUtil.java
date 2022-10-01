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
		final Email to = new Email(reciver);
		final Content content = new Content("text/plain", contentStr);
		final Mail mail = new Mail(from, subject, to, content);

		final SendGrid sg = new SendGrid(apiKey);
		final Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			final Response response = sg.api(request);
		}catch (final Exception e) {
			// TODO: handle exception
		}

	}




}
