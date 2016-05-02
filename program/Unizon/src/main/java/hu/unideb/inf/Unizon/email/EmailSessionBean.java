package hu.unideb.inf.Unizon.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

@Stateless
public class EmailSessionBean {

	@Inject
	private Logger log;

	public static final String E_MAIL = "unizonwebshop@gmail.com";

	private static final String PASSWORD = "szrt2016";

	private static final Properties PROPS;

	static {
		PROPS = new Properties();
		PROPS.put("mail.smtp.auth", "true");
		PROPS.put("mail.smtp.starttls.enable", "true");
		PROPS.put("mail.smtp.host", "smtp.gmail.com");
		PROPS.put("mail.smtp.port", "587");
		PROPS.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	}

	public void sendEmail(String from, String subject, String text, String name) {
		sendEmail(from, E_MAIL, subject, text, name);
	}

	public void sendEmail(String from, String to, String subject, String text, String name) {
		Session session = Session.getInstance(PROPS, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(E_MAIL, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);

			InternetAddress internetAddress = new InternetAddress(E_MAIL);
			internetAddress.setPersonal(name + " <" + from + ">");
			message.setFrom(internetAddress);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
	}

}
