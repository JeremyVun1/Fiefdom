package stateless;

import DAFacades.FiefdomFacade;
import dto.EventLogsDTO;
import dto.FiefdomDTO;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;

@Stateless
@DeclareRoles({"ADMIN", "USER", "SUSPENDED"})
public class EventsBean implements EventsBeanRemote {
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;

	@Override
	@RolesAllowed({"ADMIN", "USER", "SUSPENDED"})
	public EventLogsDTO getEventLogs() {
		String ctxUsername = userinfoBean.getUsername();
		
		FiefdomDTO fDTO = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (fDTO == null)
			return null;
		
		return eventLogger.getEventLogs(fDTO.getId());
	}
}
