package user;

import dto.ArmyDTO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import stateless.ArmyBeanRemote;

@Named(value = "recruitMBean")
@ConversationScoped
public class RecruitMBean implements Serializable {
	
	@Inject
	private Conversation conversation;
	
	@EJB
	ArmyBeanRemote armyBean;
	
	@Inject
	FiefdomInfoMBean fiefdomBean;
	
	private int archersToRecruit;
	private int spearsToRecruit;
	private int knightsToRecruit;
	private int wizardsToRecruit;
	
	private int totalArchersCost;
	private int totalSpearsCost;
	private int totalKnightsCost;
	private int totalWizardsCost;
	
	private int requiredGold;
	private int requiredPeasants;
	
	private ArmyDTO armyDTO;
	
	@PostConstruct
	private void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		
		armyDTO = armyBean.getArmy();
	}
	
	public String recruit() {
		//public ArmyDTO(int aid, int knights, int archers, int spears, int wizards) {
		ArmyDTO toRecruit = new ArmyDTO(armyDTO.getAid(), knightsToRecruit,
				archersToRecruit, spearsToRecruit, wizardsToRecruit);

		boolean result = armyBean.recruit(toRecruit);
		
		//end the conversation
		if(!conversation.isTransient()){
			conversation.end();
		}
		
		if (result)
			return "Success";
		else return "Failure";
	}
	
	//AJAX LISTENERS
	public void totalArchersCostChanged(AjaxBehaviorEvent event) {
		totalArchersCost =  archersToRecruit * armyDTO.getArcherCost();
	}
	
	public void totalSpearsCostChanged(AjaxBehaviorEvent event) {
		totalSpearsCost = spearsToRecruit * armyDTO.getSpearCost();
	}
	
	public void totalKnightsCostChanged(AjaxBehaviorEvent event) {
		totalKnightsCost = knightsToRecruit * armyDTO.getKnightCost();
	}
	
	public void totalWizardsCostChanged(AjaxBehaviorEvent event) {
		totalWizardsCost = wizardsToRecruit * armyDTO.getWizardCost();
	}
	
	public void requiredGoldChanged(AjaxBehaviorEvent event) {
		requiredGold = totalArchersCost + totalSpearsCost + totalKnightsCost + totalWizardsCost;
	}
	
	public void requiredPeasantsChanged(AjaxBehaviorEvent event) {
		requiredPeasants = archersToRecruit + spearsToRecruit + knightsToRecruit + wizardsToRecruit;
	}
	
	//GETTERS AND SETTER SPAM
	public int getGold() {
		return fiefdomBean.getGold();
	}
	
	public int getPeasants() {
		return fiefdomBean.getPeasants();
	}

	public int getRequiredGold() {
		return requiredGold;
	}

	public void setRequiredGold(int requiredGold) {
		this.requiredGold = requiredGold;
	}
	
	public int getRequiredPeasants() {
		return requiredPeasants;
	}

	public void setRequiredPeasants(int requiredPeasants) {
		this.requiredPeasants = requiredPeasants;
	}
	
	public int getRecruitedArchers() {
		return armyDTO.getArchers();
	}
	
	public int getRecruitedSpears() {
		return armyDTO.getSpears();
	}
	
	public int getRecruitedKnights() {
		return armyDTO.getKnights();
	}
	
	public int getRecruitedWizards() {
		return armyDTO.getWizards();
	}
	
	public int getArcherCost() {
		return armyDTO.getArcherCost();
	}
	
	public int getSpearCost() {
		return armyDTO.getSpearCost();
	}
	
	public int getKnightCost() {
		return armyDTO.getKnightCost();
	}
	
	public int getWizardCost() {
		return armyDTO.getWizardCost();
	}

	public int getArchersToRecruit() {
		return archersToRecruit;
	}

	public void setArchersToRecruit(int archersToRecruit) {
		this.archersToRecruit = archersToRecruit;
	}

	public int getSpearsToRecruit() {
		return spearsToRecruit;
	}

	public void setSpearsToRecruit(int spearsToRecruit) {
		this.spearsToRecruit = spearsToRecruit;
	}

	public int getKnightsToRecruit() {
		return knightsToRecruit;
	}

	public void setKnightsToRecruit(int knightsToRecruit) {
		this.knightsToRecruit = knightsToRecruit;
	}

	public int getWizardsToRecruit() {
		return wizardsToRecruit;
	}

	public void setWizardsToRecruit(int wizardsToRecruit) {
		this.wizardsToRecruit = wizardsToRecruit;
	}

	public int getTotalArchersCost() {
		return totalArchersCost;
	}

	public int getTotalSpearsCost() {
		return totalSpearsCost;
	}

	public int getTotalKnightsCost() {
		return totalKnightsCost;
	}

	public int getTotalWizardsCost() {
		return totalWizardsCost;
	}
}
