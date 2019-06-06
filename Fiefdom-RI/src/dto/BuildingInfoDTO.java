package dto;

import java.io.Serializable;

public class BuildingInfoDTO implements Serializable {
	
	private int farmCost;
	private int mineCost;
	private int marketCost;
	private int towerCost;
	
	private String farmEffect;
	private String mineEffect;
	private String marketEffect;
	private String towerEffect;

	public BuildingInfoDTO(int farmCost, int mineCost, int marketCost, int towerCost, String farmEffect, String mineEffect, String marketEffect, String towerEffect) {
		this.farmCost = farmCost;
		this.mineCost = mineCost;
		this.marketCost = marketCost;
		this.towerCost = towerCost;
		this.farmEffect = farmEffect;
		this.mineEffect = mineEffect;
		this.marketEffect = marketEffect;
		this.towerEffect = towerEffect;
	}

	public int getFarmCost() {
		return farmCost;
	}

	public int getMineCost() {
		return mineCost;
	}

	public int getMarketCost() {
		return marketCost;
	}

	public int getTowerCost() {
		return towerCost;
	}

	public String getFarmEffect() {
		return farmEffect;
	}

	public String getMineEffect() {
		return mineEffect;
	}

	public String getMarketEffect() {
		return marketEffect;
	}

	public String getTowerEffect() {
		return towerEffect;
	}
	
}
