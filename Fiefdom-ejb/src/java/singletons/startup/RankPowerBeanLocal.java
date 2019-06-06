package singletons.startup;

import dto.ArmyDTO;
import dto.BuildingsDTO;
import dto.FiefdomDTO;
import java.util.Map;
import javax.ejb.Local;
import dto.RankPowerDTO;

@Local
public interface RankPowerBeanLocal {
	void recordNewFiefdom(FiefdomDTO fDTO, ArmyDTO aDTO, BuildingsDTO bDTO);
	void removeFiefdom(int id);
	
	Map<Integer, RankPowerDTO> getRankPowers() throws InterruptedException;
	RankPowerDTO getRankPower(int fId) throws InterruptedException;
	
	void update();
}
