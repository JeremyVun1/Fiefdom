package email;

import dto.EmailDTO;
import java.util.Date;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
@Asynchronous
public class EmailingBean implements EmailingBeanRemote {
	
	private String smtpServer = "smtp.gmail.com";
	private int port = 587;
	private String from = "fiefdomgame@gmail.com";
	private String password = "password!@#";

	@Override
	public void sendEmail(EmailDTO email) {
		System.out.println("attempting to send email to " + email.getTo());
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);

			Auth myPA = new Auth(from, password);
			Session session = Session.getInstance(props, myPA);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo(), false));
			msg.setSubject(email.getSubject());
			msg.setText(email.getBody());
			msg.setHeader("X-Mailer", "Gmail");
			msg.setSentDate(new Date());

			Transport.send(msg);
			Transport.send(msg, from, password);
			
			System.out.println("email sent to " + email.getTo());
			
		} catch (Exception ex) {
			System.out.println("email could not be sent to " + email.getTo());
			ex.printStackTrace();
		}
	}
	
}