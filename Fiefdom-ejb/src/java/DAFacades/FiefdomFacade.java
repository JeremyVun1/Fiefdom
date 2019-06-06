package DAFacades;

import dto.ArmyDTO;
import dto.BuildingsDTO;
import dto.FiefdomBasicDTO;
import dto.FiefdomDTO;
import dto.FiefdomShortDTO;
import dto.RegisterUserDTO;
import dto.UserinfoFull_pwDTO;
import entity.Army;
import entity.Buildings;
import entity.Fiefdom;
import entity.Userinfo;
import gamelogic.internal.GameConstants;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class FiefdomFacade {

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	@EJB
	UserFacade userFacade;
	
	@EJB
	GameConstants gameConst;

	private Fiefdom find(int fId) {
		return em.find(Fiefdom.class, fId);
	}
	
	private Fiefdom find(String fName) {
		try {
			Query q = em.createNamedQuery("Fiefdom.findByName").setParameter("name", fName);
			return (Fiefdom)q.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}
	
	private Fiefdom findByUser(Userinfo u) {
		if (u == null)
			return null;
		
		Query q = em.createNamedQuery("Fiefdom.findByUser").setParameter("u", u);
		return ((Fiefdom)q.getSingleResult());
	}

	public boolean createFiefdom(RegisterUserDTO regDTO) {
		if (regDTO == null)
			return false;
		
		Army a = new Army(1, 5, 5, 1);
		em.persist(a);

		Buildings b = new Buildings(10, 5, 5, 0);
		em.persist(b);

		Fiefdom f = new Fiefdom(regDTO.fiefdomName, regDTO.getRace(), 100, 20, 50, 200);
		f.setAid(a);
		f.setBid(b);
		
		Userinfo u = userFacade.fetchUserByUsername(regDTO.username);
		f.setUid(u);
		em.persist(f);
		
		return true;
	}
	
	public void update(Fiefdom  f) {
		if (f == null)
			return;

		em.merge(f);
	}
	
	public boolean updateFiefdomById(int fId, int goldChange, int landChange, int popChange) {
		Fiefdom f = find(fId);
		
		return updateFiefdom(f, goldChange, landChange, popChange);
	}
	
	public boolean updateFiefdom(Fiefdom f, int goldChange, int landChange, int popChange) {
		if (f == null)
			return false;
		
		f.setGold(f.getGold() + goldChange);
		f.setLand(f.getLand() + landChange);
		f.setPeasants(f.getPeasants() + popChange);
		
		em.persist(f);
		return true;
	}
	
	public boolean updateFiefdomFromUserinfo(UserinfoFull_pwDTO dto) {
		if (dto == null)
			return false;
		
		Userinfo u = userFacade.fetchUserById(dto.getId());		
		Fiefdom f = findByUser(u);	
		if (f == null)
			return false;
		
		//update fiefdom entity
		try {
			f.setName(dto.getFiefdomName());
			f.setRace(gameConst.raceInt(dto.getRace()));

			em.merge(f);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean updateBuildings(int fId, int goldChange, BuildingsDTO toBuild) {
		Fiefdom f = find(fId);
		if (f == null)
			return false;

		try {
			f.setGold(f.getGold() + goldChange);
			em.merge(f);

			Buildings b = f.getBid();
			b.setFarms(b.getFarms() + toBuild.getFarms());
			b.setMarkets(b.getMarkets() + toBuild.getMarkets());
			b.setMines(b.getMines() + toBuild.getMines());
			b.setTowers(b.getTowers() + toBuild.getTowers());
			em.merge(b);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean updateArmy(int fId, int goldChange, int peasantChange, ArmyDTO toRecruit) {
		Fiefdom f = find(fId);
		if (f == null)
			return false;
		
		try {
			f.setGold(f.getGold() + goldChange);
			f.setPeasants(f.getPeasants() + peasantChange);
			em.merge(f);

			Army a = f.getAid();
			a.setArchers(a.getArchers() + toRecruit.getArchers());
			a.setSpears(a.getSpears() + toRecruit.getSpears());
			a.setKnights(a.getKnights() + toRecruit.getKnights());
			a.setWizards(a.getWizards() + toRecruit.getWizards());
			em.merge(a);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean fiefdomNameExists(String fiefdomName) {
		if (fiefdomName == null)
			return false;
		
		Query q = em.createNamedQuery("Fiefdom.findByName").setParameter("name", fiefdomName);
		List<Fiefdom> list = q.getResultList();

		return (!list.isEmpty());
	}
	
	public boolean fiefdomExists(int a) {
		if (find(a) != null)
			return true;
		else return false;
	}
	
	public FiefdomDTO getFiefdomById(int fId) {
		return fiefdomDAO2DTO(find(fId));
	}
	
	public Fiefdom getFiefdomEntById(int fId) {
		return find(fId);
	}

	public FiefdomDTO getFiefdomByUsername(String username) {
		if (username == null)
			return null;
		
		Userinfo u = userFacade.fetchUserByUsername(username);
		Fiefdom f = findByUser(u);
		
		return fiefdomDAO2DTO(f);
	}
	
	public Fiefdom getFiefdomByUser(Userinfo u) {
		return findByUser(u);
	}
	
	public boolean removeFiefdom(Userinfo u) {
		if (u == null)
			return false;
		
		Fiefdom f = findByUser(u);
		
		if(f == null)
			return false;

		em.remove(f.getAid());
		em.remove(f.getBid());
		em.remove(f);
		
		return true;
	}
	
	public ArrayList<Fiefdom> getAllFiefdoms() {
		ArrayList<Fiefdom> result = new ArrayList<Fiefdom>();
		
		List<Fiefdom> qResult = em.createNamedQuery("Fiefdom.findAll").getResultList();
		
		//convert to array list
		for (Fiefdom f : qResult)
			result.add(f);
		
		return result;
	}
	
	public ArmyDTO getArmyByFId(int fId) {
		Fiefdom f = find(fId);
		
		return armyDAO2DTO(f.getAid());
	}
	
	public BuildingsDTO getBuildingsByFId(int fId) {
		Fiefdom f = find(fId);
		
		return buildingsDAO2DTO(f.getBid());
	}
	
	public ArrayList<Army> getAllArmies() {
		ArrayList<Army> result = new ArrayList<Army>();
		
		List<Army> qResult = em.createNamedQuery("Army.findAll").getResultList();
		
		//convert to array list
		for (Army a : qResult)
			result.add(a);
		
		return result;
	}

	public int numOfFiefdoms() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	//DAO to DTO translators
	public ArmyDTO armyDAO2DTO(Army a) {
		if (a == null)
			return null;
		
		return new ArmyDTO(a.getAid(), a.getKnights(), a.getArchers(), a.getSpears(), a.getWizards(),
				gameConst.KNIGHT_COST, gameConst.ARCHER_COST, gameConst.SPEAR_COST, gameConst.WIZARD_COST);
	}
	
	public BuildingsDTO buildingsDAO2DTO(Buildings b) {
		if (b == null)
			return null;
		
		return new BuildingsDTO(b.getBid(), b.getFarms(), b.getMarkets(), b.getMines(), b.getTowers(),
				gameConst.FARM_COST, gameConst.MARKET_COST, gameConst.MINE_COST, gameConst.TOWER_COST);
	}

	public FiefdomBasicDTO getFiefdomBasicById(int id) {
		Fiefdom f = find(id);
		if (f == null)
			return null;
		
		return DAO2BasicDTO(f);
	}
	
	public FiefdomBasicDTO getFiefdomBasicByName(String fName) {
		Fiefdom f = find(fName);
		
		if (f == null)
			return null;
		
		return DAO2BasicDTO(f);
	}

	public ArrayList<FiefdomShortDTO> getFiefdomsShort() {
		Query q = em.createNamedQuery("Fiefdom.findAll");		
		List<Fiefdom> qResult = q.getResultList();
		
		ArrayList<FiefdomShortDTO> result = new ArrayList<FiefdomShortDTO>();
		for (Fiefdom f : qResult) {
			result.add(DAO2ShortDTO(f));
		}
		
		return result;
	}
	
	public ArrayList<FiefdomBasicDTO> getFiefdomsBasic() {
		Query q = em.createNamedQuery("Fiefdom.findAll");		
		List<Fiefdom> qResult = q.getResultList();
		
		ArrayList<FiefdomBasicDTO> result = new ArrayList<FiefdomBasicDTO>();
		for (Fiefdom f : qResult) {
			result.add(DAO2BasicDTO(f));
		}
		
		return result;
	}
	
	public FiefdomDTO fiefdomDAO2DTO(Fiefdom f) {
		if (f == null)
			return null;
		
	
		return new FiefdomDTO(f.getId(), f.getName(), gameConst.raceStr(f.getRace()), f.getGold(),
				f.getLand(), f.getFood(), f.getPeasants());
	}
	
	private FiefdomBasicDTO DAO2BasicDTO(Fiefdom f) {
		if (f == null)
			return null;
		
		return new FiefdomBasicDTO(gameConst.raceStr(f.getRace()), f.getLand(), f.getId(), f.getName());
	}
	
	private FiefdomShortDTO DAO2ShortDTO(Fiefdom f) {
		if (f == null)
			return null;
		
		return new FiefdomShortDTO(f.getId(), f.getName());
	}
}
