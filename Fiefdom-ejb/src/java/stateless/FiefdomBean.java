package stateless;

import DAFacades.FiefdomFacade;
import dto.FiefdomBasicDTO;
import dto.FiefdomDTO;
import dto.FiefdomShortDTO;
import dto.RankPowerDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.RankPowerBeanLocal;

@DeclareRoles({"ADMIN", "USER", "SUSPENDED"})
@Stateless
public class FiefdomBean implements FiefdomBeanRemote {
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	RankPowerBeanLocal rankPowerBean;

	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public FiefdomDTO getFiefdom() {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO fDTO = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		return (FiefdomDTO)addRankPower(fDTO);
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean fiefdomExists(int fId) {
		if (fiefdomFacade.getFiefdomById(fId) != null)
			return true;
		else return false;
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean isUsersFiefdom(int fId) {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (f == null || f.getId() == fId)
			return false;
		else return true;
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public String getFiefdomName(int fromId) {
		FiefdomDTO f = fiefdomFacade.getFiefdomById(fromId);
		if (f == null)
			return null;
		
		return f.getName();
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<FiefdomShortDTO> fetchFiefdomsShort() {
		return fiefdomFacade.getFiefdomsShort();
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<FiefdomBasicDTO> fetchFiefdomsBasic() {
		ArrayList<FiefdomBasicDTO> result = fiefdomFacade.getFiefdomsBasic();
		
		try {
			for (int i=0; i<result.size(); i++) {
				FiefdomBasicDTO fDTO = result.get(i);

				RankPowerDTO rp = rankPowerBean.getRankPower(fDTO.getId());
				fDTO.setPower(rp.getPower());
				fDTO.setRank(rp.getRank());
			}
			
			//sort by rank and return
			Collections.sort(result, rankComparator);
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	//helper comparator for sorting
	private static Comparator<FiefdomBasicDTO> rankComparator = new Comparator<FiefdomBasicDTO>() {
		@Override
		public int compare(FiefdomBasicDTO a, FiefdomBasicDTO b) {
			return (b.getRank() > a.getRank() ? -1
					: (b.getRank() == a.getRank() ? 0 : 1));
		}
	};
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public FiefdomBasicDTO fetchFiefdomBasicById(int id) {
		FiefdomBasicDTO result = fiefdomFacade.getFiefdomBasicById(id);
		
		return addRankPower(result);
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean fiefdomExists(String fName) {
		return fiefdomFacade.fiefdomNameExists(fName);
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public FiefdomBasicDTO fetchFiefdomBasicByName(String fName) {
		if (fName == null)
			return null;
		
		FiefdomBasicDTO result = fiefdomFacade.getFiefdomBasicByName(fName);
		
		return addRankPower(result);
	}
	
	private FiefdomBasicDTO addRankPower(FiefdomBasicDTO fDTO) {
		if (fDTO == null)
			return null;
		
		try {
			RankPowerDTO rp = rankPowerBean.getRankPower(fDTO.getId());
			fDTO.setPower(rp.getPower());
			fDTO.setRank(rp.getRank());
			
			return fDTO;
		} catch (Exception ex) {
			return null;
		}
	}
}
