package stateless;

import dto.BuildingInfoDTO;
import dto.FiefdomDTO;
import dto.RacesDTO;
import dto.SessionDTO;
import dto.UnitInfoDTO;
import javax.ejb.Remote;

@Remote
public interface GameInfoBeanRemote {
	RacesDTO getRaces();
	
	SessionDTO getSessionInfo();

	UnitInfoDTO fetchUnitInfo();

	BuildingInfoDTO fetchBuildingInfo();
}
