package stateless;

import DAFacades.FiefdomFacade;
import dto.ArmyDTO;
import dto.FiefdomDTO;
import gamelogic.internal.GameConstants;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;

@DeclareRoles({"USER", "SUSPENDED"})
@Stateless
public class ArmyBean implements ArmyBeanRemote {
	
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
	public ArmyDTO getArmy() {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (f == null)
			return null;
		
		return fiefdomFacade.getArmyByFId(f.getId());
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean recruit(ArmyDTO toRecruit) {
		if (toRecruit == null)
			return false;
		
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO fDTO = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (fDTO == null)
			return false;
			

		//check if we have enough resources (gold and peasants)
		int requiredGold = toRecruit.getArchers() * constants.ARCHER_COST
				+ toRecruit.getKnights() * constants.KNIGHT_COST
				+ toRecruit.getSpears() * constants.SPEAR_COST
				+ toRecruit.getWizards() * constants.WIZARD_COST;
		
		if (!enoughGold(requiredGold, fDTO) || !enoughPeasants(toRecruit, fDTO))
			return false;
		
		boolean result = fiefdomFacade.updateArmy(fDTO.getId(), requiredGold * -1, toRecruit.getArmySize() * -1, toRecruit);
		
		if (result) {
			String eventString = buildEventString(toRecruit);
			eventLogger.logEvent(fDTO.getId(), eventString);
			eventLogger.logResourceChange(fDTO.getId(), requiredGold * -1, 0, 0);
		}
		
		return true;
	}
	
	private String buildEventString(ArmyDTO toRecruit) {
		String result = "Recruited: ";
		if (toRecruit.getArchers() > 0)
			result += toRecruit.getArchers() + " Archers ";
		if (toRecruit.getSpears() > 0)
			result += toRecruit.getSpears() + " Spearmen ";
		if (toRecruit.getKnights() > 0)
			result += toRecruit.getKnights() + " Knights ";
		if (toRecruit.getWizards() > 0)
			result += toRecruit.getWizards() + " Wizards ";
		
		return result;
	}
	
	private boolean enoughGold(int requiredGold, FiefdomDTO fDTO) {
		return (requiredGold <= fDTO.getGold());
	}
	
	private boolean enoughPeasants(ArmyDTO aDTO, FiefdomDTO fDTO) {
		return (aDTO.getArmySize() <= fDTO.getPeasants());
	}
}
