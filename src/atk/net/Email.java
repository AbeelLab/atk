package atk.net;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private Properties ps;

	public Email(String smtp){
		Properties ps = new Properties();
		ps.setProperty("mail.smtp.host", smtp);
		ps.put("mail.debug", "true");
		this.ps=ps;
	}
	
	public void sendEmail(String aFromEmailAddr, String aToEmailAddr, String aSubject, String aBody) {
		// Here, no Authenticator argument is used (it is null).
		// Authenticators are used to prompt the user for user
		// name and password.
		

		Session session = Session.getDefaultInstance(ps);
		MimeMessage message = new MimeMessage(session);
		try {
			// the "from" address may be set in code, or set in the
			// config file under "mail.from" ; here, the latter style is used
			message.setFrom(new InternetAddress(aFromEmailAddr));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(aToEmailAddr));
			message.setSubject(aSubject);
			message.setText(aBody);
			Transport.send(message);
		} catch (MessagingException ex) {
			System.err.println("Cannot send email. " + ex);
		}
	}
}
