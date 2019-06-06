package gamelogic.internal;

import dto.BattleReportDTO;
import javax.ejb.Local;
import structs.BattleArmy;

@Local
public interface WarFunctionsLocal {
	
	BattleReportDTO resolve(int bId, int aId, String aName, int dId, String dName, BattleArmy a, BattleArmy d, double towerPower);
	
}
