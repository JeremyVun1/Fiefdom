package user;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import stateless.ExploreBeanRemote;

@Named(value = "exploreMBean")
@ConversationScoped
public class ExploreMBean implements Serializable {
	
	@Inject
	private Conversation conversation;
	
	@EJB
	ExploreBeanRemote exploreBean;
	
	@Inject
	FiefdomInfoMBean fiefdomBean;
	
	private int landToExplore;
	private int requiredGold;
	
	private int explorationCost;
	
	@PostConstruct
	private void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
			
		explorationCost = exploreBean.getLandCost();
	}
	
	public String explore() {		
		boolean result = exploreBean.explore(landToExplore);
		
		//end the conversation
		if(!conversation.isTransient()){
			conversation.end();
		}
		
		if (result)
			return "Success";
		else return "Failure";
	}
	
	//AJAX LISTENERS
	public void totalCostChanged(AjaxBehaviorEvent event) {
		requiredGold = landToExplore * explorationCost;
	}
	
	//GETTERS AND SETTER SPAM
	public int getGold() {
		return fiefdomBean.getGold();
	}

	public int getRequiredGold() {
		return requiredGold;
	}

	public void setRequiredGold(int requiredGold) {
		this.requiredGold = requiredGold;
	}

	public int getLandToExplore() {
		return landToExplore;
	}

	public void setLandToExplore(int landToExplore) {
		this.landToExplore = landToExplore;
	}
	
	public int getExplorationCost() {
		return explorationCost;
	}
	
}
