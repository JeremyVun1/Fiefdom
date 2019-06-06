package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class BattleReportDTO implements Serializable {
	private int id;	
	private Date battleDate;
	
	private int attId;
	private int defId;
	private String attName;
	private String defName;
	
	private boolean attWin;
	
	//resource gains
	private int goldGain;
	private int landGain;
	private int peasantGain;
	
	//initial armies
	private int aarchers;
	private int aspears;
	private int aknights;
	private int awizards;
	
	private int darchers;
	private int dspears;
	private int dknights;
	private int dwizards;
	
	//losses
	private int attArcherLoss;
	private int attSpearLoss;
	private int attKnightLoss;
	private int attWizardLoss;
	
	private int defArcherLoss;
	private int defSpearLoss;
	private int defKnightLoss;
	private int defWizardLoss;
	
	private int defTowerLoss;
	private int defMineLoss;
	private int defMarketLoss;
	private int defFarmLoss;
	
	//turns
	private ArrayList<BattleTurnDTO> turns;
	
	public BattleReportDTO(int bId, Date battleDate, int aId, String aName, int dId, String dName, boolean attWin,
			int goldGain, int landGain, int peasantGain, 
			int aarchers, int aspears, int aknights, int awizards,
			int darchers, int  dspears, int dknights, int dwizards,
			int attArcherLoss, int attSpearLoss, int attKnightLoss, int attWizardLoss,
			int defArcherLoss, int defSpearLoss, int defKnightLoss, int defWizardLoss,
			int defTowerLoss, int defMineLoss, int defMarketLoss, int defFarmLoss,
			ArrayList<BattleTurnDTO> turns)
	{
		this.id = bId;
		this.battleDate = battleDate;
		this.attId = aId;
		this.defId = dId;
		this.attName = aName;
		this.defName = dName;
		
		this.attWin = attWin;
		
		this.goldGain = goldGain;
		this.landGain = landGain;
		this.peasantGain = peasantGain;

		this.aarchers = aarchers;
		this.aspears = aspears;
		this.aknights = aknights;
		this.awizards = awizards;
		
		this.darchers = darchers;
		this.dspears = dspears;
		this.dknights = dknights;
		this.dwizards = dwizards;
		
		this.attArcherLoss = attArcherLoss;
		this.attSpearLoss = attSpearLoss;
		this.attKnightLoss = attKnightLoss;
		this.attWizardLoss = attWizardLoss;
		
		this.defArcherLoss = defArcherLoss;
		this.defSpearLoss = defSpearLoss;
		this.defKnightLoss = defKnightLoss;
		this.defWizardLoss = defWizardLoss;
	
		this.defTowerLoss = defTowerLoss;
		this.defMineLoss = defMineLoss;
		this.defMarketLoss = defMarketLoss;
		this.defFarmLoss = defFarmLoss;
		
		this.turns = turns;
	}
	
	public BattleReportDTO(int bId, int aId, String aName, int dId, String dName, int aarchers, int aspears, int aknights, int awizards, int darchers, int  dspears, int dknights, int dwizards) {
		this.id = bId;
		this.attId = aId;
		this.defId = dId;		
		this.attName = aName;
		this.defName = dName;

		this.aarchers = aarchers;
		this.aspears = aspears;
		this.aknights = aknights;
		this.awizards = awizards;
		
		this.darchers = darchers;
		this.dspears = dspears;
		this.dknights = dknights;
		this.dwizards = dwizards;
	}
	
	public void setBattleResult(boolean attWin, int attArcherLoss, int attSpearLoss, int attKnightLoss, int attWizardLoss, int defArcherLoss, int defSpearLoss, int defKnightLoss, int defWizardLoss, ArrayList<BattleTurnDTO> turns) {
		this.attWin = attWin;
		
		this.attArcherLoss = attArcherLoss;
		this.attSpearLoss = attSpearLoss;
		this.attKnightLoss = attKnightLoss;
		this.attWizardLoss = attWizardLoss;
		
		this.defArcherLoss = defArcherLoss;
		this.defSpearLoss = defSpearLoss;
		this.defKnightLoss = defKnightLoss;
		this.defWizardLoss = defWizardLoss;
		
		this.turns = turns;
	}

	public int getId() {
		return id;
	}

	public Date getBattleDate() {
		return battleDate;
	}

	public int getAttId() {
		return attId;
	}

	public int getDefId() {
		return defId;
	}

	public boolean isAttWin() {
		return attWin;
	}

	public int getGoldGain() {
		return goldGain;
	}

	public int getLandGain() {
		return landGain;
	}

	public int getPeasantGain() {
		return peasantGain;
	}

	public int getAttArcherLoss() {
		return attArcherLoss;
	}

	public int getAttSpearLoss() {
		return attSpearLoss;
	}

	public int getAttKnightLoss() {
		return attKnightLoss;
	}

	public int getAttWizardLoss() {
		return attWizardLoss;
	}

	public int getDefArcherLoss() {
		return defArcherLoss;
	}

	public int getDefSpearLoss() {
		return defSpearLoss;
	}

	public int getDefKnightLoss() {
		return defKnightLoss;
	}

	public int getDefWizardLoss() {
		return defWizardLoss;
	}

	public ArrayList<BattleTurnDTO> getTurns() {
		return turns;
	}

	public void setGoldGain(int goldGain) {
		this.goldGain = goldGain;
	}

	public void setLandGain(int landGain) {
		this.landGain = landGain;
	}

	public void setPeasantGain(int peasantGain) {
		this.peasantGain = peasantGain;
	}

	public String getAttName() {
		return attName;
	}

	public String getDefName() {
		return defName;
	}

	public int getDefMineLoss() {
		return defMineLoss;
	}

	public void setDefMineLoss(int defMineLoss) {
		this.defMineLoss = defMineLoss;
	}

	public int getDefMarketLoss() {
		return defMarketLoss;
	}

	public void setDefMarketLoss(int defMarketLoss) {
		this.defMarketLoss = defMarketLoss;
	}

	public int getDefFarmLoss() {
		return defFarmLoss;
	}

	public void setDefFarmLoss(int defFarmLoss) {
		this.defFarmLoss = defFarmLoss;
	}

	public int getDefTowerLoss() {
		return defTowerLoss;
	}

	public void setDefTowerLoss(int defTowerLoss) {
		this.defTowerLoss = defTowerLoss;
	}

	public int getAarchers() {
		return aarchers;
	}

	public int getAspears() {
		return aspears;
	}

	public int getAknights() {
		return aknights;
	}

	public int getAwizards() {
		return awizards;
	}

	public int getDarchers() {
		return darchers;
	}

	public int getDspears() {
		return dspears;
	}

	public int getDknights() {
		return dknights;
	}

	public int getDwizards() {
		return dwizards;
	}
	
	
}
