package stateless;

import DAFacades.BattleReportFacade;
import DAFacades.FiefdomFacade;
import DAFacades.UserFacade;
import dto.ArmyDTO;
import dto.BattleReportDTO;
import dto.BattleReportShortDTO;
import dto.FiefdomDTO;
import entity.Army;
import entity.Buildings;
import entity.Fiefdom;
import entity.Userinfo;
import gamelogic.internal.GameConstants;
import gamelogic.internal.WarFunctionsLocal;
import java.util.ArrayList;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;
import structs.BattleArmy;

@DeclareRoles({"ADMIN", "USER"})
@Stateless
public class WarBean implements WarBeanRemote {
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserFacade userFacade;
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	BattleReportFacade brFacade;
	
	@EJB
	WarFunctionsLocal warFunctions;
	
	@EJB
	GameConstants constants;

	@Override
	@RolesAllowed("USER")
	public int attack(int targetId, ArmyDTO aArmy) {
		//get user context of attacker
		String ctxUsername = userinfoBean.getUsername();
		Userinfo aUser = userFacade.fetchUserByUsername(ctxUsername);
		Fiefdom aFiefdom = fiefdomFacade.getFiefdomByUser(aUser);

		//get defender
		Fiefdom dFiefdom = fiefdomFacade.getFiefdomEntById(targetId);
		if (aUser == null || aFiefdom == null || dFiefdom == null)
			return -1;
		
		//resolve battle result
		BattleReportDTO br = resolve(aFiefdom, dFiefdom, aArmy);
		if (br == null)
			return -1;

		//update fiefdom entities with battle results
		updateFiefdoms(aFiefdom, dFiefdom, br);

		updateBattleReport(br);
		
		//log battle event
		String attackerString = attackerEventString(br);
		String defenderString = defenderEventString(br);
		
		System.out.println("Attacker Str: " + attackerString);
		System.out.println("Defender Str: " + defenderString);

		eventLogger.logEvent(aFiefdom.getId(), attackerString);
		eventLogger.logEvent(targetId, defenderString);
		
		//return battlereport id
		return br.getId();
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public BattleReportDTO getReport(String brIdStr) {
		int brId;
		
		try {
			brId = Integer.parseInt(brIdStr);
		} catch (Exception ex) {
			return null;
		}
		
		BattleReportDTO result = brFacade.getReport(brId);
		if (result == null)
			return null;
		
		//check that we can view the report
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO fDto = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		if (userinfoBean.isAdmin() || result.getAttId() == fDto.getId() || result.getDefId() == fDto.getId())
			return result;
		else return null;
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<BattleReportShortDTO> fetchReportsShort() {
		String ctxUsername = userinfoBean.getUsername();
		Userinfo u = userFacade.fetchUserByUsername(ctxUsername);
		Fiefdom f = fiefdomFacade.getFiefdomByUser(u);
		if (f == null)
			return null;
		
		return brFacade.fetchReportsShort(f);
	}
	
	private BattleReportDTO resolve(Fiefdom a, Fiefdom d, ArmyDTO aArmySent) {
		int bId = brFacade.create(a, d);

		double towerPower = (d.getBid().getTowers() / d.getLand()) * constants.TOWER_STRENGTH;
		
		//resolve army conflict
		BattleReportDTO br = warFunctions.resolve(bId, a.getId(), a.getName(), d.getId(), d.getName(),
				ArmyDTO2BattleArmy(aArmySent), ArmyDAO2BattleArmy(d.getAid()),
				towerPower);
		
		//resolve resource gains
		captureResources(a, d, br);
		
		//destroy defender towers
		destroyTowers(d.getBid(), br);
		
		return br;
	}
	
	private void updateBattleReport(BattleReportDTO br) {
		brFacade.update(br); //update battlereport
	}
	
	private void updateFiefdoms(Fiefdom a, Fiefdom d, BattleReportDTO br) {
		updateArmy(a.getAid(), br.getAttArcherLoss(), br.getAttSpearLoss(), br.getAttKnightLoss(), br.getAttWizardLoss());
		updateArmy(d.getAid(), br.getDefArcherLoss(), br.getDefSpearLoss(), br.getDefKnightLoss(), br.getDefWizardLoss());
		
		fiefdomFacade.update(a);
		fiefdomFacade.update(d);
	}
	
	private void updateArmy(Army a, int archersLoss, int spearsLoss, int knightsLoss, int wizardsLoss) {
		a.setArchers(Math.max(a.getArchers() - archersLoss, 0));
		a.setSpears(Math.max(a.getSpears() - spearsLoss, 0));
		a.setKnights(Math.max(a.getKnights() - knightsLoss, 0));
		a.setWizards(Math.max(a.getWizards() - wizardsLoss, 0));
	}
	
	private void destroyTowers(Buildings b, BattleReportDTO br) {
		if (!br.isAttWin())
			return;
		
		int towersLost = b.getTowers() * 10;
		
		br.setDefTowerLoss(br.getDefTowerLoss() + towersLost);
		
		b.setTowers(b.getTowers() - towersLost);
	}
	
	private void captureResources(Fiefdom a, Fiefdom d, BattleReportDTO br) {
		if (!br.isAttWin())
			return;
		
		int goldCaptured = (int)(d.getGold() * 0.1);
		int landCaptured = (int)(d.getLand() * 0.1);
		int peasantsCaptured = (int)(d.getPeasants() * 0.1);
		
		a.setGold(a.getGold() + goldCaptured);
		a.setLand(a.getLand() + landCaptured);
		a.setPeasants(a.getPeasants() + peasantsCaptured);
		
		d.setGold(d.getGold() - goldCaptured);
		d.setLand(d.getLand() - landCaptured);
		d.setPeasants(d.getPeasants() - peasantsCaptured);
		destroyExcessBuildings(d, br); // destroy buildings due to loss of land
		
		br.setGoldGain(goldCaptured);
		br.setLandGain(landCaptured);
		br.setPeasantGain(peasantsCaptured);
	}
	
	private void destroyExcessBuildings(Fiefdom f, BattleReportDTO br) {
		Buildings b = f.getBid();
		
		int landDeficit = (b.getFarms() + b.getMarkets() + b.getMines() + b.getTowers()) - f.getLand();
		if (landDeficit <= 0)
			return;
		
		int towersLost = 0;
		int minesLost = 0;
		int farmsLost = 0;
		int marketsLost = 0;
		
		//destroy towers first
		if (b.getTowers() > landDeficit) {
			towersLost = landDeficit;
			b.setTowers(b.getTowers() - landDeficit);
			landDeficit = 0;
		}
		else {
			towersLost = b.getTowers();
			landDeficit -= b.getTowers();
			b.setTowers(0);
		}
		
		//split damage between buildings
		while (landDeficit > 0) {
			double rand = Math.random();
			if (rand < 0.33) {
				b.setFarms(b.getFarms() - 1);
				farmsLost++;
				landDeficit--;
			}
			else if (rand > 0.66) {
				b.setMines(b.getMines() - 1);
				minesLost++;
				landDeficit--;
			}
			else {
				b.setMarkets(b.getMarkets()- 1);
				marketsLost++;
				landDeficit--;
			}
		}
		
		br.setDefTowerLoss(br.getDefTowerLoss() + towersLost);
		br.setDefFarmLoss(farmsLost);
		br.setDefMarketLoss(marketsLost);
		br.setDefMineLoss(minesLost);
	}
	
	//build event strings
	private String attackerEventString(BattleReportDTO br) {
		String result = "You attacked" + " (#" + br.getDefId() + ")" + br.getDefName();
		
		//x attacks y and captures 0 gold, 0 land, 0 peasants
		if (br.isAttWin()) {
			result += " and captured "
					+ br.getGoldGain() + " gold, "
					+ br.getLandGain() + " land, "
					+ br.getPeasantGain() + " peasants.";
		}
		else {
			result += " and lost";
		}
		
		return result;
	}
	
	private String defenderEventString(BattleReportDTO br) {
		String result = "You were attacked by" + " (#" + br.getAttId() + ")" + br.getAttName();
		
		//x attacks y and captures 0 gold, 0 land, 0 peasants
		if (br.isAttWin()) {
			result += " and lost "
					+ br.getGoldGain() + " gold, "
					+ br.getLandGain() + " land, "
					+ br.getPeasantGain() + " peasants.";
		}
		else {
			result += " and won";
		}
		
		return result;
	}
	
	//UTIL
	private BattleArmy ArmyDAO2BattleArmy(Army a) {
		return new BattleArmy(a.getArchers(), a.getArchers() * constants.ARCHER_HEALTH,
				a.getSpears(), a.getSpears() * constants.SPEAR_HEALTH,
				a.getKnights(), a.getKnights() * constants.KNIGHT_HEALTH,
				a.getWizards(), a.getWizards() * constants.WIZARD_HEALTH);
	}
	
	private BattleArmy ArmyDTO2BattleArmy(ArmyDTO a) {
		return new BattleArmy(a.getArchers(), a.getArchers() * constants.ARCHER_HEALTH,
				a.getSpears(), a.getSpears() * constants.SPEAR_HEALTH,
				a.getKnights(), a.getKnights() * constants.KNIGHT_HEALTH,
				a.getWizards(), a.getWizards() * constants.WIZARD_HEALTH);
	}
}
