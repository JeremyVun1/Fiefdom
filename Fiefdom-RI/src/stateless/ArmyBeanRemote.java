package stateless;

import dto.ArmyDTO;
import javax.ejb.Remote;

@Remote
public interface ArmyBeanRemote {
	
	ArmyDTO getArmy();
	
	boolean recruit(ArmyDTO aDTO);
}
