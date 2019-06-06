package user;

import dto.BuildingsDTO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import stateless.BuildingsBeanRemote;

@Named(value = "buildingsMBean")
@ConversationScoped
public class BuildingsMBean implements Serializable {
	
	@Inject
	private Conversation conversation;
	
	@EJB
	BuildingsBeanRemote buildingsBean;
	
	@Inject
	FiefdomInfoMBean fiefdomBean;
	
	private int farmsToBuild;	
	private int minesToBuild;
	private int towersToBuild;
	private int marketsToBuild;
	
	private int totalFarmsCost;
	private int totalMinesCost;
	private int totalTowersCost;
	private int totalMarketsCost;
	
	private int requiredGold;
	private int requiredFreeLand;
	
	private BuildingsDTO buildingsDTO;
	
	@PostConstruct
	private void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}

		buildingsDTO = buildingsBean.getBuildings();
	}
	
	public String build() {
		BuildingsDTO toBuild = new BuildingsDTO(buildingsDTO.getBid(),
				farmsToBuild, minesToBuild, towersToBuild, marketsToBuild);
		
		boolean result = buildingsBean.build(toBuild);
		
		//end the conversation
		if(!conversation.isTransient()){
			conversation.end();
		}
		
		if (result)
			return "Success";
		else return "Failure";
	}
	
	//AJAX LISTENERS
	public void totalFarmsCostChanged(AjaxBehaviorEvent event) {
		totalFarmsCost = farmsToBuild * buildingsDTO.getFarmsCost();
	}
	
	public void totalMinesCostChanged(AjaxBehaviorEvent event) {
		totalMinesCost = minesToBuild * buildingsDTO.getMinesCost();
	}
	
	public void totalTowersCostChanged(AjaxBehaviorEvent event) {
		totalTowersCost = towersToBuild * buildingsDTO.getTowersCost();
	}
	
	public void totalMarketsCostChanged(AjaxBehaviorEvent event) {
		totalMarketsCost = marketsToBuild * buildingsDTO.getMarketsCost();
	}
	
	public void totalCostChanged(AjaxBehaviorEvent event) {
		requiredGold = totalFarmsCost + totalMinesCost + totalMarketsCost + totalTowersCost;
	}
	
	public void totalLandChanged(AjaxBehaviorEvent event) {
		requiredFreeLand = farmsToBuild + minesToBuild + towersToBuild + marketsToBuild;
	}
	
	//GETTERS AND SETTER SPAM
	public int getGold() {
		return fiefdomBean.getGold();
	}
	
	public int getFreeLand() {
		int result = fiefdomBean.getLand();
		result -= buildingsDTO.getTotalBuildings();
		
		return result;
	}
	
	public int getFarmsToBuild() {
		return farmsToBuild;
	}

	public void setFarmsToBuild(int farmsToBuild) {
		this.farmsToBuild = farmsToBuild;
	}

	public int getMinesToBuild() {
		return minesToBuild;
	}

	public void setMinesToBuild(int minesToBuild) {
		this.minesToBuild = minesToBuild;
	}

	public int getTowersToBuild() {
		return towersToBuild;
	}

	public void setTowersToBuild(int towersToBuild) {
		this.towersToBuild = towersToBuild;
	}

	public int getMarketsToBuild() {
		return marketsToBuild;
	}

	public void setMarketsToBuild(int marketsToBuild) {
		this.marketsToBuild = marketsToBuild;
	}

	public int getBuiltFarms() {
		return buildingsDTO.getFarms();
	}

	public int getBuiltMines() {
		return buildingsDTO.getMines();
	}

	public int getBuiltTowers() {
		return buildingsDTO.getTowers();
	}

	public int getBuiltMarkets() {
		return buildingsDTO.getMarkets();
	}
	
	public int getFarmCost() {
		return buildingsDTO.getFarmsCost();
	}
	
	public int getMineCost() {
		return buildingsDTO.getMinesCost();
	}
	
	public int getTowerCost() {
		return buildingsDTO.getTowersCost();
	}
	
	public int getMarketCost() {
		return buildingsDTO.getMarketsCost();
	}

	public int getTotalFarmsCost() {
		return totalFarmsCost;
	}

	public void setTotalFarmsCost(int totalFarmsCost) {
		this.totalFarmsCost = totalFarmsCost;
	}

	public int getTotalMinesCost() {
		return totalMinesCost;
	}

	public void setTotalMinesCost(int totalMinesCost) {
		this.totalMinesCost = totalMinesCost;
	}

	public int getTotalTowersCost() {
		return totalTowersCost;
	}

	public void setTotalTowersCost(int totalTowersCost) {
		this.totalTowersCost = totalTowersCost;
	}

	public int getTotalMarketsCost() {
		return totalMarketsCost;
	}

	public void setTotalMarketsCost(int totalMarketsCost) {
		this.totalMarketsCost = totalMarketsCost;
	}

	public int getRequiredGold() {
		return requiredGold;
	}

	public void setRequiredGold(int requiredGold) {
		this.requiredGold = requiredGold;
	}

	public int getRequiredFreeLand() {
		return requiredFreeLand;
	}

	public void setRequiredFreeLand(int requiredFreeLand) {
		this.requiredFreeLand = requiredFreeLand;
	}
	
}
