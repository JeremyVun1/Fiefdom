package stateless;

import dto.BuildingsDTO;
import javax.ejb.Remote;

@Remote
public interface BuildingsBeanRemote {
	
	BuildingsDTO getBuildings();
	
	boolean build(BuildingsDTO toBuild);
}
