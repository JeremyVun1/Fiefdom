package web;

import dto.BuildingInfoDTO;
import dto.UnitInfoDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import stateless.GameInfoBeanRemote;


@Named(value = "encMBean")
@RequestScoped
public class EncylopediaMBean {
	
	@EJB
	GameInfoBeanRemote gameInfoBean;
	
	private UnitInfoDTO unitInfo;
	private BuildingInfoDTO buildingInfo;
	
	@PostConstruct
	private void init() {
		unitInfo = gameInfoBean.fetchUnitInfo();
		buildingInfo = gameInfoBean.fetchBuildingInfo();
	}
	
	public int getArcherCost() {
		return unitInfo.getArcherCost();
	}
	
	public int getArcherDirectDamage() {
		return unitInfo.getArcherDD();
	}
	
	public int getArcherSplashDamage() {
		return unitInfo.getArcherSplash();
	}
	
	public int getArcherHealth() {
		return unitInfo.getArcherHealth();
	}
	
	public int getSpearCost() {
		return unitInfo.getSpearCost();		
	}
	
	public int getSpearDirectDamage() {
		return unitInfo.getSpearDD();
	}
	
	public int getSpearSplashDamage() {
		return unitInfo.getSpearSplash();
	}
	
	public int getSpearHealth() {
		return unitInfo.getSpearHealth();
	}
	
	public int getKnightCost() {
		return unitInfo.getKnightCost();
	}
	
	public int getKnightDirectDamage() {
		return unitInfo.getKnightDD();
	}
	
	public int getKnightSplashDamage() {
		return unitInfo.getKnightSplash();
	}
	
	public int getKnightHealth() {
		return unitInfo.getKnightHealth();
	}
	
	public int getWizardCost() {
		return unitInfo.getWizardCost();
	}
	
	public int getWizardDirectDamage() {
		return unitInfo.getWizardDD();
	}
	
	public int getWizardSplashDamage() {
		return unitInfo.getWizardSplash();
	}
	
	public int getWizardHealth() {
		return unitInfo.getWizardHealth();
	}
	
	public int getFarmCost() {
		return buildingInfo.getFarmCost();
	}
	
	public String getFarmEffect() {
		return buildingInfo.getFarmEffect();
	}
	
	public int getMineCost() {
		return buildingInfo.getMineCost();
	}
	
	public String getMineEffect() {
		return buildingInfo.getMineEffect();
	}
	
	public int getMarketCost() {
		return buildingInfo.getMarketCost();
	}
	
	public String getMarketEffect() {
		return buildingInfo.getMarketEffect();
	}
	
	public int getTowerCost() {
		return buildingInfo.getTowerCost();
	}
	
	public String getTowerEffect() {
		return buildingInfo.getTowerEffect();
	}
}
