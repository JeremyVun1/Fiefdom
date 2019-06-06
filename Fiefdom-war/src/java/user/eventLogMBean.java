package user;

import dto.TurnDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.EventsBeanRemote;

@Named(value = "eventLogMBean")
@RequestScoped
public class eventLogMBean {
	@EJB
	EventsBeanRemote eventsBean;
	
	private ArrayList<TurnDTO> logs;
	
	@PostConstruct
	private void init() {
		logs = eventsBean.getEventLogs().getLogs();
	}

	public ArrayList<TurnDTO> getLogs() {
		return logs;
	}

}
