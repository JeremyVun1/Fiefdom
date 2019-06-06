package DAFacades;

import dto.BattleReportDTO;
import dto.BattleReportShortDTO;
import dto.BattleTurnDTO;
import entity.Battlereport;
import entity.Battleturn;
import entity.Fiefdom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class BattleReportFacade {

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	private Battlereport find(int id) {
		return em.find(Battlereport.class, id);
	}
	
	public int create(Fiefdom attacker, Fiefdom defender) {
		Battlereport result = new Battlereport(new Date(), attacker, defender);
		System.out.println("trying to persist br attF: " + attacker.getName() + " defF: " + defender.getName());
		
		em.persist(result);
		em.flush();
		
		System.out.println("br has been persisted id: " + result.getId());
		
		return result.getId();
	}
	
	public BattleReportDTO getReport(int brId) {
		Battlereport br = find(brId);
		if (br == null)
			return null;
		
		//get battlereport events
		Query q = em.createNamedQuery("Battleturn.findByBrid").setParameter("brid", br);
		List<Battleturn> turnsList = q.getResultList();
		
		ArrayList<BattleTurnDTO> turns = new ArrayList<BattleTurnDTO>();
		for (Battleturn bTurn : turnsList) {
			turns.add(bTurnDAO2DTO(bTurn));
		}
		
		return DAO2DTO(br, turns);
	}
	
	public void update(BattleReportDTO dto) {
		System.out.println("br updating win: " + dto.isAttWin());
		if (dto == null)
			return;

		Battlereport br = find(dto.getId());
		if (br == null || br.getAttacker().getId() != dto.getAttId() || br.getDefender().getId() != dto.getDefId())
			return;
		
		//update battle report entity
		br.setAttackerwin(dto.isAttWin());
		br.setInitialArmies(dto.getAarchers(), dto.getAspears(), dto.getAknights(), dto.getAwizards(), dto.getDarchers(), dto.getDspears(), dto.getDknights(), dto.getDwizards());
		
		br.setAlossarchers(dto.getAttArcherLoss());
		br.setAlossspears(dto.getAttSpearLoss());
		br.setAlossknights(dto.getAttKnightLoss());
		br.setAlosswizards(dto.getAttWizardLoss());
		
		br.setDlossarchers(dto.getDefArcherLoss());
		br.setDlossspears(dto.getDefSpearLoss());
		br.setDlossknights(dto.getDefKnightLoss());
		br.setDlosswizards(dto.getDefWizardLoss());
		
		br.setDlosstowers(dto.getDefTowerLoss());
		br.setDlossmines(dto.getDefMineLoss());
		br.setDlossfarms(dto.getDefFarmLoss());
		br.setDlossmarkets(dto.getDefMarketLoss());
		
		br.setGoldgain(dto.getGoldGain());
		br.setLandgain(dto.getLandGain());
		br.setPeasantgain(dto.getPeasantGain());
		
		em.merge(br);
		
		//store battlereport turns
		for (BattleTurnDTO turnDTO : dto.getTurns()) {
			Battleturn turnEnt = new Battleturn(turnDTO.getTurnCount(), br, turnDTO.isInitiatorIsAttacker(), turnDTO.getInitiatingUnit(), turnDTO.getInitiatingSize(),
					turnDTO.getArcherLoss(), turnDTO.getSpearLoss(), turnDTO.getKnightLoss(), turnDTO.getWizardLoss());

			em.persist(turnEnt);
		}
		
		System.out.println("br updated");
	}
	
	public ArrayList<BattleReportShortDTO> fetchReportsShort(Fiefdom f) {
		//, @NamedQuery(name = "Battlereport.findByFiefdom", query = "SELECT b FROM Battlereport b WHERE b.attacker = :fiefdom OR b.defender = :fiefdom")
		Query q = em.createNamedQuery("Battlereport.findByFiefdom").setParameter("fiefdom", f);
		
		List<Battlereport> resultList = q.getResultList();
		
		ArrayList<BattleReportShortDTO> result = new ArrayList<BattleReportShortDTO>();
		for (Battlereport br : resultList) {
			result.add(DAO2ShortDTO(br));
		}
		
		return result;
	}
	
	private BattleReportShortDTO DAO2ShortDTO(Battlereport ent) {
		if (ent == null)
			return null;

		return new BattleReportShortDTO(ent.getId(), ent.getBattledate(),
				ent.getAttacker().getId(), ent.getAttacker().getName(), ent.getDefender().getId(), ent.getDefender().getName(),
				ent.getAttackerwin());
	}
	
	private BattleTurnDTO bTurnDAO2DTO(Battleturn bTurn) {
		if (bTurn == null)
			return null;
		
		return new BattleTurnDTO(bTurn.getTurnCount(), bTurn.getIsattacker(), bTurn.getAttackingunit(), bTurn.getAttackingsize(), bTurn.getArcherloss(), bTurn.getSpearloss(), bTurn.getKnightloss(), bTurn.getWizardloss());
	}
	
	private BattleReportDTO DAO2DTO(Battlereport ent, ArrayList<BattleTurnDTO> turns) {
		if (ent == null)
			return null;
		
		return new BattleReportDTO(ent.getId(), ent.getBattledate(),
				ent.getAttacker().getId(), ent.getAttacker().getName(), ent.getDefender().getId(), ent.getDefender().getName(),
				ent.getAttackerwin(), ent.getGoldgain(), ent.getLandgain(), ent.getPeasantgain(),
				ent.getAarchers(), ent.getAspears(), ent.getAknights(), ent.getAwizards(),
				ent.getDarchers(), ent.getDspears(), ent.getDknights(), ent.getDwizards(),
				ent.getAlossarchers(), ent.getAlossspears(), ent.getAlossknights(), ent.getAlosswizards(),
				ent.getDlossarchers(), ent.getDlossspears(), ent.getDlossknights(), ent.getDlosswizards(),
				ent.getDlosstowers(), ent.getDlossmines(), ent.getDlossmarkets(), ent.getDlossfarms(),
				turns);
	}
}
