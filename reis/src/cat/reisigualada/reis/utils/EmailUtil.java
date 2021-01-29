package cat.reisigualada.reis.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	private static final String FROM_EMAIL_INFO_NAME = "Comissió Reis d'Igualada";
	private static final String FROM_EMAIL_INFO = "info@reisdigualada.cat";
	private static final String PASSWORD_EMAIL_INFO = "knsZ335_";
    private static final String HOSTING_REIS = "reisdigualada.cat";
	
	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(String toEmail, String subject, String body) {
		try {
			Properties props = new Properties();
			 // Setup mail server
			props.put("mail.smtp.host", HOSTING_REIS);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.enable", "false");
			props.put("mail.smtp.auth", "true");
			 
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(FROM_EMAIL_INFO, PASSWORD_EMAIL_INFO);
				}
			};
			
			Session session = Session.getDefaultInstance(props, auth);
			System.out.println("Session created");
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM_EMAIL_INFO, FROM_EMAIL_INFO_NAME));
			msg.setReplyTo(InternetAddress.parse(FROM_EMAIL_INFO, false));
			msg.setSubject(subject);
			msg.setContent(body, "text/HTML; charset=UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			
			System.out.println("Ready to send message to: " + toEmail);
			Transport.send(msg);
			System.out.println("E-mail Sent Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}