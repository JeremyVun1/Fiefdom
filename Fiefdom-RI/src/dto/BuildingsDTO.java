package dto;

public class BuildingsDTO {
	private int bid;
	
	private int farms;
	private int farmsCost;
	
	private int markets;
	private int marketsCost;
	
	private int mines;
	private int minesCost;
	
	private int towers;
	private int towersCost;
	
	public BuildingsDTO(int bid, int farms, int markets, int mines, int towers) {
		this.bid = bid;
		
		this.farms = farms;
		this.markets = markets;
		this.mines = mines;
		this.towers = towers;
	}

	public BuildingsDTO(int bid, int farms, int markets, int mines, int towers, int farmsCost, int marketsCost, int minesCost, int towersCost) {
		this.bid = bid;
		
		this.farms = farms;
		this.markets = markets;
		this.mines = mines;
		this.towers = towers;
		
		this.farmsCost = farmsCost;
		this.marketsCost = marketsCost;
		this.minesCost = minesCost;
		this.towersCost = towersCost;
	}

	public int getBid() {
		return bid;
	}

	public int getFarms() {
		return farms;
	}

	public int getMarkets() {
		return markets;
	}

	public int getMines() {
		return mines;
	}

	public int getTowers() {
		return towers;
	}

	public int getFarmsCost() {
		return farmsCost;
	}

	public int getMarketsCost() {
		return marketsCost;
	}

	public int getMinesCost() {
		return minesCost;
	}

	public int getTowersCost() {
		return towersCost;
	}
	
	public int getTotalBuildings() {
		return (farms + towers + mines + markets);
	}
	
}
