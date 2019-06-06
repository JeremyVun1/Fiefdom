package DAFacades;

import dto.FiefdomDTO;
import dto.FiefdomShortDTO;
import dto.RankPowerDTO;
import dto.RelationDTO;
import entity.Fiefdom;
import entity.Relation;
import entity.RelationPK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import singletons.startup.RankPowerBeanLocal;

@Stateless
@LocalBean
public class RelationFacade {

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	@EJB
	RankPowerBeanLocal rankPower;
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	UserFacade userFacade;
	
	public ArrayList<RelationDTO> getRelations(int callingFId) {
		FiefdomDTO f = fiefdomFacade.getFiefdomById(callingFId);
		if (f == null)
			return null;
		
		//query both sides of relationship entities to get all relations which the calling entity is participating in
		ArrayList<RelationDTO> result = new ArrayList<RelationDTO>();
		
		Query q = em.createNamedQuery("Relation.findByFid1").setParameter("fid1", f.getId());
		List<Relation> qResult = q.getResultList();
		
		result.addAll(DAOList2DTOArrayList(qResult, callingFId));			
		
		q = em.createNamedQuery("Relation.findByFid2").setParameter("fid2", f.getId());
		result.addAll(DAOList2DTOArrayList(q.getResultList(), callingFId));
		
		return result;
	}
	
	private ArrayList<RelationDTO> DAOList2DTOArrayList(List<Relation> list, int callingFId) {
		if (list == null)
			return null;
		
		int fId;
		String fName;
		RankPowerDTO rp;
		
		ArrayList<RelationDTO> result = new ArrayList<RelationDTO>();
		for (Relation r : list) {
			try {
				//get details of the other fiefdom in the relationship
				if (callingFId == r.getFiefdom().getId()) {
					fId = r.getFiefdom1().getId();
					fName = r.getFiefdom1().getName();
					rp = rankPower.getRankPower(fId);

				} else {
					fId = r.getFiefdom().getId();
					fName = r.getFiefdom().getName();
					rp = rankPower.getRankPower(fId);
				}
				
				//add to arraylist
				result.add(new RelationDTO(fId, fName, rp.getPower(), rp.getRank(), r.getRelation().toString()));
			} catch (Exception ex) {
				return null;
			}
		}
		
		return result;
	}
	
	private Relation getRelation (int a, int b) {
		//check relationships both ways
		RelationPK rpk1 = new RelationPK(a, b);
		RelationPK rpk2 = new RelationPK(b, a);
		
		Relation r1 = em.find(Relation.class, rpk1);
		Relation r2 = em.find(Relation.class, rpk2);
		
		if (r1 == null) {
			if (r2 == null) {
				return null;
			}
			else return r2;
		} else return r1;
	}
	
	private boolean fiefdomsExist(int a, int b) {
		return (fiefdomFacade.fiefdomExists(a) && fiefdomFacade.fiefdomExists(b));
	}
	
	public boolean setWarBetween(int a, int b) {
		if (!fiefdomsExist(a, b))
			return false;

		//only declare war if no relation between players (at peace)
		if (getRelation(a, b) == null) {
			Fiefdom fiefdomA = fiefdomFacade.getFiefdomEntById(a);
			Fiefdom fiefdomB = fiefdomFacade.getFiefdomEntById(b);
			
			Relation r = new Relation(a, b);
			r.setFiefdom(fiefdomA);
			r.setFiefdom1(fiefdomB);
			r.setRelation('W');
			
			em.persist(r);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setPeaceBetween(int a, int b) {
		if (!fiefdomsExist(a, b))
			return false;
		
		//only go to peace (remove relation) if already at war
		Relation r = getRelation(a, b);
		if (r == null || r.getRelation().equals('A')) {
			return false;
		} else {
			em.remove(r);
			return true;
		}
	}
	
	public boolean setAllianceBetween(int a, int b) {
		if (!fiefdomsExist(a, b))
			return false;
		
		if (getRelation(a, b) == null) {
			Fiefdom fiefdomA = fiefdomFacade.getFiefdomEntById(a);
			Fiefdom fiefdomB = fiefdomFacade.getFiefdomEntById(b);
			
			Relation r = new Relation(a, b);
			r.setFiefdom(fiefdomA);
			r.setFiefdom1(fiefdomB);
			r.setRelation('A');
			
			em.persist(r);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean breakAllianceBetween(int a, int b) {
		if (!fiefdomsExist(a, b))
			return false;
		
		Relation r = getRelation(a, b);
		if (r == null || r.getRelation().equals('W')) {
			return false;
		} else {
			em.remove(r);
			return true;
		}
	}
	
	public ArrayList<FiefdomShortDTO> getAllAtWarWith(int fId) {
		//query both sides of relationship entities to get all relations which the calling entity is participating in
		ArrayList<FiefdomShortDTO> result = new ArrayList<FiefdomShortDTO>();
		
		Query q = em.createNamedQuery("Relation.findByFid1").setParameter("fid1", fId);
		List<Relation> qResult = q.getResultList();
		
		result.addAll(DAO2FiefdomBasicDTO(qResult, fId));
		
		q = em.createNamedQuery("Relation.findByFid2").setParameter("fid2", fId);
		result.addAll(DAO2FiefdomBasicDTO(q.getResultList(), fId));
		
		return result;
	}
	
	private ArrayList<FiefdomShortDTO> DAO2FiefdomBasicDTO(List<Relation> list, int fId) {
		ArrayList<FiefdomShortDTO> result = new ArrayList<FiefdomShortDTO>();
		
		for (Relation r : list) {
			if (r.getRelation().equals('W')) {
				Fiefdom f1 = r.getFiefdom();
				Fiefdom f2 = r.getFiefdom1();
				
				if (f1.getId() == fId)
					result.add(new FiefdomShortDTO(f2.getId(), f2.getName()));
				else result.add(new FiefdomShortDTO(f1.getId(), f1.getName()));
			}
		}
		
		return result;
	}
}
