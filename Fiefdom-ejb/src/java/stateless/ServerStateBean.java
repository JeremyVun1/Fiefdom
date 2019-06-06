package stateless;

import DAFacades.UserFacade;
import java.util.Date;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.ServerInfoBeanLocal;

@Stateless
@DeclareRoles({"USER", "ADMIN", "SUSPENDED"})
public class ServerStateBean implements ServerStateBeanRemote {
	
	//singleton for basic global server information
	@EJB
	ServerInfoBeanLocal serverInfo;	
	
	@EJB
	UserFacade userFacade;
	
	//manage count of unique players online
	@Override
	@Asynchronous
	@RolesAllowed({"USER", "SUSPENDED"})
	public void recordLogin(String username) {
		if (username == null)
			return;
		
		//if (userFacade.tryRecordLogin(username))
			serverInfo.incPlayersOnline();
	}
	
	@Override
	@Asynchronous
	@RolesAllowed({"USER", "SUSPENDED"})
	public void recordLogout(String username) {
		if (username == null)
			return;
		
		//if (userFacade.tryRecordLogout(username))
			serverInfo.decPlayersOnline();
	}

	//getters and setters
	@Override
	public int getOnlineCount() {
		return serverInfo.getOnlineCount();
	}

	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public int getTurnCount() {
		return serverInfo.getTurnCount();
	}
	
	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public Date getNextTurnDate() {
		return serverInfo.getNextTurnDate();
	}
	
	@Override
	public Date getSystemTime() {
		return new Date();
	}
	
	@Override
	public boolean ping() {
		return true;
	}
	
}
