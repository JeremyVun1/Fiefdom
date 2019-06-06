package user;

import dto.ArmyDTO;
import dto.FiefdomShortDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import stateless.ArmyBeanRemote;
import stateless.DiplomacyBeanRemote;
import stateless.WarBeanRemote;

@Named(value = "warMBean")
@RequestScoped
public class WarMBean implements Serializable {

	@EJB
	ArmyBeanRemote armyBean;
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	@EJB
	WarBeanRemote warBean;
	
	@Inject
	FiefdomInfoMBean fiefdomBean;
	
	private int target;
	private ArrayList<FiefdomShortDTO> targets;
	
	private int archersToSend;
	private int spearsToSend;
	private int knightsToSend;
	private int wizardsToSend;
	
	private ArmyDTO armyDTO;
	
	@PostConstruct
	private void init() {
		armyDTO = armyBean.getArmy();
		targets = diplomacyBean.getWarTargets();
	}
	
	public String attack() {
		if (archersToSend == 0 && spearsToSend == 0 && knightsToSend == 0 && wizardsToSend == 0) {
			return "Failure";
		}
		
		ArmyDTO attackingArmy = new ArmyDTO(armyDTO.getAid(), knightsToSend, archersToSend, spearsToSend, wizardsToSend);
		
		int brID = warBean.attack(target, attackingArmy);
		
		//error attacking
		if (brID == -1) {
			return "Failure";
		}
		
		//attack was processed, redirect to battle report
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Fiefdom-war/user/report.xhtml?id=" + brID);
		} catch(Exception ex) {}
		
		return "/Fiefdom-war/user/report.xhtml?id=" + brID;
	}
	
	//VALIIDATORS
	//AJAX LISTENERS
	public void archersChanged(AjaxBehaviorEvent event) {
		archersToSend = Math.min(archersToSend, armyDTO.getArchers());
	}
	
	public void spearsChanged(AjaxBehaviorEvent event) {
		spearsToSend = Math.min(spearsToSend, armyDTO.getSpears());
	}
	
	public void knightsChanged(AjaxBehaviorEvent event) {
		knightsToSend = Math.min(knightsToSend, armyDTO.getKnights());
	}
	
	public void wizardsChanged(AjaxBehaviorEvent event) {
		wizardsToSend = Math.min(wizardsToSend, armyDTO.getWizards());
	}
	
	//GETTERS AND SETTER SPAM	
	public int getPeasants() {
		return fiefdomBean.getPeasants();
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public ArrayList<FiefdomShortDTO> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<FiefdomShortDTO> targets) {
		this.targets = targets;
	}

	public int getArchersToSend() {
		return archersToSend;
	}

	public void setArchersToSend(int archersToSend) {
		this.archersToSend = archersToSend;
	}

	public int getSpearsToSend() {
		return spearsToSend;
	}

	public void setSpearsToSend(int spearsToSend) {
		this.spearsToSend = spearsToSend;
	}

	public int getKnightsToSend() {
		return knightsToSend;
	}

	public void setKnightsToSend(int knightsToSend) {
		this.knightsToSend = knightsToSend;
	}

	public int getWizardsToSend() {
		return wizardsToSend;
	}

	public void setWizardsToSend(int wizardsToSend) {
		this.wizardsToSend = wizardsToSend;
	}
	
	public int getArchers() {
		return armyDTO.getArchers();
	}
	
	public int getSpears() {
		return armyDTO.getSpears();
	}
	
	public int getKnights() {
		return armyDTO.getKnights();
	}
	
	public int getWizards() {
		return armyDTO.getWizards();
	}
		
}
