package dto;

import java.io.Serializable;

public class ArmyDTO implements Serializable {
	private int aid;
	private int knights;
	private int archers;
	private int spears;
	private int wizards;
	
	private int knightCost;
	private int archerCost;
	private int spearCost;
	private int wizardCost;

	public ArmyDTO(int aid, int knights, int archers, int spears, int wizards, int knightCost, int archerCost, int spearCost, int wizardCost) {
		this.aid = aid;
		this.knights = knights;
		this.archers = archers;
		this.spears = spears;
		this.wizards = wizards;
		
		this.knightCost = knightCost;
		this.archerCost = archerCost;
		this.spearCost = spearCost;
		this.wizardCost = wizardCost;
	}
	
	public ArmyDTO(int aid, int knights, int archers, int spears, int wizards) {
		this.aid = aid;
		this.knights = knights;
		this.archers = archers;
		this.spears = spears;
		this.wizards = wizards;
	}

	public int getAid() {
		return aid;
	}

	public int getKnights() {
		return knights;
	}

	public int getArchers() {
		return archers;
	}

	public int getSpears() {
		return spears;
	}

	public int getWizards() {
		return wizards;
	}

	public int getKnightCost() {
		return knightCost;
	}

	public int getArcherCost() {
		return archerCost;
	}

	public int getSpearCost() {
		return spearCost;
	}

	public int getWizardCost() {
		return wizardCost;
	}
	
	public int getArmySize() {
		return (archers + spears + knights + wizards);
	}
	
}
