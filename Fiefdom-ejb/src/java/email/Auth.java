package email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {

	PasswordAuthentication mypa;

	public Auth(String username, String password) {
		mypa = new PasswordAuthentication(username, password);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return mypa;
	}
}
