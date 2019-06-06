package stateless;

import DAFacades.FiefdomFacade;
import dto.BuildingInfoDTO;
import dto.FiefdomDTO;
import dto.RacesDTO;
import dto.SessionDTO;
import dto.UnitInfoDTO;
import gamelogic.internal.GameConstants;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.RankPowerBeanLocal;

@Stateless
@DeclareRoles({"ADMIN", "USER", "SUSPENDED"})
public class GameInfoBean implements GameInfoBeanRemote {

	@EJB
	FiefdomFacade fiefdomFacade;

	@EJB
	UserinfoManagementBeanRemote userinfoBean;

	@EJB
	RankPowerBeanLocal rankPowerBean;

	@EJB
	GameConstants c;

	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public SessionDTO getSessionInfo() {
		String username = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(username);

		if (f == null) {
			return null;
		}

		return new SessionDTO(username, f.getId(), f.getName(), f.getRace());
	}

	@Override
	public UnitInfoDTO fetchUnitInfo() {
		int archerSplash = (int) (c.ARCHER_DAMAGE * c.ARCHER_SPLASH);
		int spearSplash = (int) (c.SPEAR_DAMAGE * c.ARCHER_SPLASH);
		int knightSplash = (int) (c.KNIGHT_DAMAGE * c.KNIGHT_SPLASH);
		int wizardSplash = (int) (c.WIZARD_DAMAGE * c.WIZARD_SPLASH);

		return new UnitInfoDTO(c.ARCHER_COST, (int) (c.ARCHER_DAMAGE - archerSplash), archerSplash, (int) c.ARCHER_HEALTH,
				c.SPEAR_COST, (int) (c.SPEAR_DAMAGE - spearSplash), spearSplash, (int) c.SPEAR_HEALTH,
				c.KNIGHT_COST, (int) (c.KNIGHT_DAMAGE - knightSplash), knightSplash, (int) c.KNIGHT_HEALTH,
				c.WIZARD_COST, (int) (c.WIZARD_DAMAGE - wizardSplash), wizardSplash, (int) c.WIZARD_HEALTH);
	}

	@Override
	public BuildingInfoDTO fetchBuildingInfo() {
		return new BuildingInfoDTO(c.FARM_COST, c.MINE_COST, c.MARKET_COST, c.TOWER_COST, c.FARM_EFFECT, c.MINE_EFFECT, c.MARKET_EFFECT, c.TOWER_EFFECT);
	}

	@Override
	public RacesDTO getRaces() {
		Map<String, Integer> races = new LinkedHashMap<String, Integer>();
		races.put("Human", 0);
		races.put("Elf", 1);
		races.put("Orc", 2);

		return new RacesDTO(races);
	}

}
