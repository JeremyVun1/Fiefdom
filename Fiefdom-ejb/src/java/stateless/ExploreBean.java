package stateless;

import DAFacades.FiefdomFacade;
import dto.FiefdomDTO;
import gamelogic.internal.GameConstants;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;

@DeclareRoles({"ADMIN", "USER"})
@Stateless
public class ExploreBean implements ExploreBeanRemote {
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	GameConstants constants;

	@Override
	@RolesAllowed("USER")
	public boolean explore(int toExplore) {
		String ctxUsername = userinfoBean.getUsername();		
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);		
		if (f == null)
			return false;
		
		//check if user has enough gold
		int requiredGold = getLandCost() * toExplore;
		if (requiredGold > f.getGold())
			return false;
		
		//update fiefdom info
		boolean result = fiefdomFacade.updateFiefdomById(f.getId(), requiredGold * -1, toExplore, 0);
		
		if (result) {
			String eventString = "Explored new land: " + toExplore;
			eventLogger.logEvent(f.getId(), eventString);
			eventLogger.logResourceChange(f.getId(), requiredGold * -1, toExplore, 0);
		}
		
		return result;
	}
	
	@Override
	@RolesAllowed("USER")
	public int getLandCost() {
		return constants.EXPLORE_COST;
	}
}
