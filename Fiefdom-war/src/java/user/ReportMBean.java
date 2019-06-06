package user;

import dto.BattleReportDTO;
import dto.BattleTurnDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import stateless.WarBeanRemote;

@Named(value = "brMBean")
@RequestScoped
public class ReportMBean {
	
	@EJB
	WarBeanRemote warBean;
	
	private BattleReportDTO br;
	private boolean canView;

	@PostConstruct
	private void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String brId = req.getParameter("id");

		br = warBean.getReport(brId);
		if (br == null)
			canView = false;
		else canView = true;
	}

	//GETTERS
	public boolean isCanView() {
		return canView;
	}
	
	public String getDate() {
		return br.getBattleDate().toString();
	}

	public String getTitle() {
		return "Battle Report " + "(#" + br.getId() + ")";
	}
	
	public String getResultString() {
		if (br.isAttWin()) {
			return "Attacker " + getAttacker() + " wins";
		} else return "Defender " + getDefender() + " wins";
	}
	
	public String getAttacker() {
		return "(#" + br.getAttId() + ")" + br.getAttName();
	}
	
	public String getDefender() {
		return "(#" + br.getDefId() + ")" + br.getDefName();
	}
	
	public String getBattleDate() {
		return br.getBattleDate().toString();
	}
	
	public String getAttackerArmy() {
		String result = "";

		if (br.getAarchers() > 0) {
			result += " Archers: " + br.getAarchers();
		}
		
		if (br.getAspears() > 0) {
			result += " Spears: " + br.getAspears();
		}
		
		if (br.getAknights() > 0) {
			result += " Knights: " + br.getAknights();
		}
		
		if (br.getAwizards() > 0) {
			result += " Wizards: " + br.getAwizards();
		}
		
		return result;
	}
	
	public String getDefenderArmy() {
		String result = "";
		
		if (br.getDarchers() > 0) {
			result += " Archers: " + br.getDarchers();
		}
		
		if (br.getDspears() > 0) {
			result += " Spears: " + br.getDspears();
		}
		
		if (br.getDknights() > 0) {
			result += " Knights: " + br.getDknights();
		}
		
		if (br.getDwizards() > 0) {
			result += " Wizards: " + br.getDwizards();
		}
		
		return result;
	}
	
	public String getAttackerLosses() {
		String result = "";

		if (br.getAarchers() > 0) {
			result += " Archers: " + br.getAttArcherLoss();
		}
		
		if (br.getAspears() > 0) {
			result += " Spears: " + br.getAttSpearLoss();
		}
		
		if (br.getAknights() > 0) {
			result += " Knights: " + br.getAttKnightLoss();
		}
		
		if (br.getAwizards() > 0) {
			result += " Wizards: " + br.getAttWizardLoss();
		}
		
		return result;
	}
	
	public String getDefenderLosses() {
		String result = "";
		
		if (br.getAarchers() > 0) {
			result += " Archers: " + br.getDefArcherLoss();
		}
		
		if (br.getAspears() > 0) {
			result += " Spears: " + br.getDefSpearLoss();
		}
		
		if (br.getAknights() > 0) {
			result += " Knights: " + br.getDefKnightLoss();
		}
		
		if (br.getAwizards() > 0) {
			result += " Wizards: " + br.getDefWizardLoss();
		}
		
		return result;
	}
	
	public String getResourcesCaptured() {
		if (!canView)
			return "";
		
		String result = "";
		
		if (br.getGoldGain() > 0) {
			result += " Gold: " + br.getGoldGain();
		}
		
		if (br.getLandGain() > 0) {
			result += " Land: " + br.getLandGain();
		}
		
		if (br.getPeasantGain() > 0) {
			result += " Peasants: " + br.getPeasantGain();
		}
		
		return result;
	}
	
	public boolean isAttackerWin() {
		if (!canView)
			return false;

		return br.isAttWin();
	}
	
	public ArrayList<BattleTurnDTO> getTurns() {
		return br.getTurns();
	}
}
