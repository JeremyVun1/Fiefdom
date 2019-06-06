package email;

import dto.EmailDTO;
import javax.ejb.Remote;

@Remote
public interface EmailingBeanRemote {
	
	void sendEmail(EmailDTO email);
}
