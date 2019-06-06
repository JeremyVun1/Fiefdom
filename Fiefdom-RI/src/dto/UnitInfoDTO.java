package dto;

import java.io.Serializable;

public class UnitInfoDTO implements Serializable {
	private int archerCost;
	private int archerDD;
	private int archerSplash;
	private int archerHealth;
	
	private int spearCost;
	private int spearDD;
	private int spearSplash;
	private int spearHealth;
	
	private int knightCost;
	private int knightDD;
	private int knightSplash;
	private int knightHealth;
	
	private int wizardCost;
	private int wizardDD;
	private int wizardSplash;
	private int wizardHealth;

	public UnitInfoDTO(int archerCost, int archerDD, int archerSplash, int archerHealth, int spearCost, int spearDD, int spearSplash, int spearHealth, int knightCost, int knightDD, int knightSplash, int knightHealth, int wizardCost, int wizardDD, int wizardSplash, int wizardHealth) {
		this.archerCost = archerCost;
		this.archerDD = archerDD;
		this.archerSplash = archerSplash;
		this.archerHealth = archerHealth;
		this.spearCost = spearCost;
		this.spearDD = spearDD;
		this.spearSplash = spearSplash;
		this.spearHealth = spearHealth;
		this.knightCost = knightCost;
		this.knightDD = knightDD;
		this.knightSplash = knightSplash;
		this.knightHealth = knightHealth;
		this.wizardCost = wizardCost;
		this.wizardDD = wizardDD;
		this.wizardSplash = wizardSplash;
		this.wizardHealth = wizardHealth;
	}

	public int getArcherCost() {
		return archerCost;
	}

	public int getArcherDD() {
		return archerDD;
	}

	public int getArcherSplash() {
		return archerSplash;
	}

	public int getArcherHealth() {
		return archerHealth;
	}

	public int getSpearCost() {
		return spearCost;
	}

	public int getSpearDD() {
		return spearDD;
	}

	public int getSpearSplash() {
		return spearSplash;
	}

	public int getSpearHealth() {
		return spearHealth;
	}

	public int getKnightCost() {
		return knightCost;
	}

	public int getKnightDD() {
		return knightDD;
	}

	public int getKnightSplash() {
		return knightSplash;
	}

	public int getKnightHealth() {
		return knightHealth;
	}

	public int getWizardCost() {
		return wizardCost;
	}

	public int getWizardDD() {
		return wizardDD;
	}

	public int getWizardSplash() {
		return wizardSplash;
	}

	public int getWizardHealth() {
		return wizardHealth;
	}
	
	
}
