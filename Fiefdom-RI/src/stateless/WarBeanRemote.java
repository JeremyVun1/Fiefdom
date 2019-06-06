package stateless;

import dto.ArmyDTO;
import dto.BattleReportDTO;
import dto.BattleReportShortDTO;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface WarBeanRemote {
	
	int attack(int targetId, ArmyDTO attackingArmy);

	BattleReportDTO getReport(String brId);

	ArrayList<BattleReportShortDTO> fetchReportsShort();
}
