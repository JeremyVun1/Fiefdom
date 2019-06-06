package user;

import dto.ThreadDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.DiplomacyBeanRemote;

@Named(value = "inboxMBean")
@RequestScoped
public class InboxMBean {
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	private ArrayList<ThreadDTO> threads;
	
	@PostConstruct
	private void init() {
		threads = diplomacyBean.getThreads();
	}

	public ArrayList<ThreadDTO> getThreads() {
		return threads;
	}
}
