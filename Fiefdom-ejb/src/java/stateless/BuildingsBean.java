package stateless;

import DAFacades.FiefdomFacade;
import dto.BuildingsDTO;
import dto.FiefdomDTO;
import gamelogic.internal.GameConstants;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;

@DeclareRoles({"USER", "SUSPENDED"})
@Stateless
public class BuildingsBean implements BuildingsBeanRemote {
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	GameConstants constants;

	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public BuildingsDTO getBuildings() {
		String ctxUsername = userinfoBean.getUsername();
		
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (f == null)
			return null;
		
		return fiefdomFacade.getBuildingsByFId(f.getId());
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean build(BuildingsDTO toBuild) {
		if (toBuild == null)
			return false;
		
		String ctxUsername = userinfoBean.getUsername();		
		FiefdomDTO fDTO = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (fDTO == null)
			return false;
		
		//check if user has enough resources (gold and free land)
		int requiredGold = toBuild.getFarms() * constants.FARM_COST + toBuild.getMarkets() * constants.MARKET_COST
				+ toBuild.getMines() * constants.MINE_COST + toBuild.getTowers() * constants.TOWER_COST;
		
		if (!enoughGold(requiredGold, fDTO) || !enoughLand(toBuild, fDTO))
			return false;
		
		boolean result = fiefdomFacade.updateBuildings(fDTO.getId(), requiredGold * -1, toBuild);
		
		if (result) {
			String eventString = buildEventString(toBuild);
			eventLogger.logEvent(fDTO.getId(), eventString);
			eventLogger.logResourceChange(fDTO.getId(), requiredGold * -1, 0, 0);
		}
			
		return result;
	}
	
	private String buildEventString(BuildingsDTO toBuild) {
		String result = "Built: ";
		if (toBuild.getFarms() > 0)
			result += toBuild.getFarms() + " Farms ";
		if (toBuild.getMarkets() > 0)
			result += toBuild.getMarkets() + " Markets ";
		if (toBuild.getMines() > 0)
			result += toBuild.getMines() + " Mines ";
		if (toBuild.getTowers() > 0)
			result += toBuild.getTowers() + " Towers ";
		
		return result;
	}
	
	private boolean enoughGold(int totalCost, FiefdomDTO fDTO) {
		return (totalCost <= fDTO.getGold());
	}
	
	private boolean enoughLand(BuildingsDTO toBuild, FiefdomDTO fDTO) {
		BuildingsDTO currBuildings = fiefdomFacade.getBuildingsByFId(fDTO.getId());
		int freeLand = fDTO.getLand() - currBuildings.getTotalBuildings();
		
		return (toBuild.getTotalBuildings() <= freeLand);
	}
}
