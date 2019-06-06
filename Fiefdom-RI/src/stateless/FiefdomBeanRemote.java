package stateless;

import dto.FiefdomBasicDTO;
import dto.FiefdomDTO;
import dto.FiefdomShortDTO;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface FiefdomBeanRemote {	
	boolean fiefdomExists(int fId);
	boolean fiefdomExists(String fName);
	boolean isUsersFiefdom(int fId);

	ArrayList<FiefdomShortDTO> fetchFiefdomsShort();
	ArrayList<FiefdomBasicDTO> fetchFiefdomsBasic();
	
	FiefdomBasicDTO fetchFiefdomBasicById(int id);
	FiefdomBasicDTO fetchFiefdomBasicByName(String fName);
	
	String getFiefdomName(int fromId);
	
	//no params to force return of the current logged in users fiefdom
	FiefdomDTO getFiefdom();
}
