package singletons.startup;

import dto.ArmyDTO;
import dto.BuildingsDTO;
import dto.FiefdomDTO;
import entity.Fiefdom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import DAFacades.FiefdomFacade;
import structs.FIdPower;
import dto.RankPowerDTO;
import gamelogic.internal.GameConstants;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Startup;

//use singleton to store power and rank of all fiefdoms for cheaper sorting
//only need to update/sort power and rankings every turn interval
//otherwise we are only serving read requests
@ConcurrencyManagement(CONTAINER)
@Startup
@Singleton
public class RankPowerBean implements RankPowerBeanLocal {

	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	GameConstants constants;

	//hashmap for fast reading
	private HashMap<Integer, RankPowerDTO> rankPowers;

	@PostConstruct
	@Lock(WRITE)
	private void init() {
		rankPowers = new HashMap<Integer, RankPowerDTO>();

		update();
		System.out.println("SINGLETON INITIALIZED: RankPowers");
	}

	@Override
	@Lock(WRITE)
	public void update() {
		ArrayList<Fiefdom> fiefdoms = fiefdomFacade.getAllFiefdoms();

		//create intemediary list of (fiefdom id, power)
		ArrayList<FIdPower> powersArr = new ArrayList<FIdPower>();
		
		for (Fiefdom f : fiefdoms) {
			FiefdomDTO fDTO = fiefdomFacade.fiefdomDAO2DTO(f);
			ArmyDTO aDTO = fiefdomFacade.armyDAO2DTO(f.getAid());
			BuildingsDTO bDTO = fiefdomFacade.buildingsDAO2DTO(f.getBid());
			
			int power = calcPower(fDTO, aDTO, bDTO);
			powersArr.add(new FIdPower(fDTO.getId(), power));
		}

		Collections.sort(powersArr, FIdPower.powerComparator);

		//rebuild the rank powers hashmap
		rankPowers.clear();
		for (int i = 0; i < powersArr.size(); i++) {
			rankPowers.put(powersArr.get(i).getfId(),
					new RankPowerDTO(powersArr.get(i).getPower(), i + 1));
		}
	}

	//shortcut for new fiefdoms, otherwise rankPowers should be updated every turn
	@Override
	@Lock(WRITE)
	public void recordNewFiefdom(FiefdomDTO fDTO, ArmyDTO aDTO, BuildingsDTO bDTO) {
		rankPowers.put(fDTO.getId(), new RankPowerDTO(calcPower(fDTO, aDTO, bDTO), rankPowers.size() + 1));
	}

	@Override
	@Lock(WRITE)
	public void removeFiefdom(int id) {
		try {
			rankPowers.remove(id);
		} catch (Exception ex) {
		}
	}

	private int calcPower(FiefdomDTO fDTO, ArmyDTO aDTO, BuildingsDTO bDTO) {
		int result = 0;

		result += fDTO.getLand() * constants.LAND_MULTI;
		result += fDTO.getPeasants() * constants.PEASANTS_MULTI;

		result += aDTO.getArchers() * constants.ARCHERS_MULTI;
		result += aDTO.getKnights() * constants.KNIGHTS_MULTI;
		result += aDTO.getSpears() * constants.SPEARS_MULTI;
		result += aDTO.getWizards() * constants.WIZARD_MULTI;
		
		result += bDTO.getFarms() * constants.FARMS_MULTI;
		result += bDTO.getMarkets() * constants.MARKETS_MULTI;
		result += bDTO.getMines() * constants.MINES_MULTI;
		result += bDTO.getTowers() * constants.TOWERS_MULTI;

		return result;
	}

	@Override
	@Lock(READ)
	public Map<Integer, RankPowerDTO> getRankPowers() throws InterruptedException {
		Map<Integer, RankPowerDTO> result;

		result = rankPowers;

		return result;
	}

	@Override
	@Lock(READ)
	public RankPowerDTO getRankPower(int fId) throws InterruptedException {
		RankPowerDTO result;

		result = rankPowers.get(fId);

		return result;
	}
}
