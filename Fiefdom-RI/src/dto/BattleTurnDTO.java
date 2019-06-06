package dto;

import java.io.Serializable;

public class BattleTurnDTO implements Serializable {
	
	private boolean initiatorIsAttacker;
	private String initiatingUnit;
	private int initiatingSize;
	
	private int archerLoss;
	private int spearLoss;
	private int knightLoss;
	private int wizardLoss;
	
	private int turnCount;

	public BattleTurnDTO(int turnCount, boolean initiatorIsAttacker, String initiatingUnit, int initiatingSize, int archerLoss, int spearLoss, int knightLoss, int wizardLoss) {
		this.turnCount = turnCount;
		this.initiatorIsAttacker = initiatorIsAttacker;
		this.initiatingUnit = initiatingUnit;
		this.initiatingSize = initiatingSize;
		this.archerLoss = archerLoss;
		this.spearLoss = spearLoss;
		this.knightLoss = knightLoss;
		this.wizardLoss = wizardLoss;
	}

	public int getTurnCount() {
		return turnCount;
	}
	
	public String getAttackerString() {
		if (initiatorIsAttacker)
			return initiatingForceString();
		else return lossesString();
	}
	
	public String getDefenderString() {
		if (initiatorIsAttacker)
			return lossesString();
		else return initiatingForceString();
	}
	
	public String getDirectionString() {
		if (initiatorIsAttacker)
			return " -> ";
		else return " <- ";
	}
	
	private String initiatingForceString() {
		return initiatingSize + " " + initiatingUnit + " attack";
	}
	
	private String lossesString() {
		String result = "";
		
		boolean killed = false;
		
		if (archerLoss > 0) {
			killed = true;
			result += archerLoss + " Archers,";
		}
		
		if (spearLoss > 0) {
			killed = true;
			result += spearLoss + " Spears,";
		}
		
		if (knightLoss > 0) {
			killed = true;
			result += knightLoss + " Knights,";
		}
		
		if (wizardLoss > 0) {
			killed = true;
			result += wizardLoss + " Wizards,";
		}
		
		if (killed) {
			result = result.substring(0, result.length()-1);
			result += " killed";
		}
		else result += "Units damaged";
		
		return result;
	}

	public boolean isInitiatorIsAttacker() {
		return initiatorIsAttacker;
	}

	public String getInitiatingUnit() {
		return initiatingUnit;
	}

	public int getInitiatingSize() {
		return initiatingSize;
	}

	public int getArcherLoss() {
		return archerLoss;
	}

	public int getSpearLoss() {
		return spearLoss;
	}

	public int getKnightLoss() {
		return knightLoss;
	}

	public int getWizardLoss() {
		return wizardLoss;
	}
}
