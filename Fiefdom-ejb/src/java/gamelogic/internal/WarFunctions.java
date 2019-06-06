package gamelogic.internal;

import dto.ArmyDTO;
import dto.BattleReportDTO;
import dto.BattleTurnDTO;
import entity.Fiefdom;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import structs.BattleArmy;

@Stateless
public class WarFunctions implements WarFunctionsLocal {
	
	@EJB
	private GameConstants constants;
	
	private ArrayList<BattleTurnDTO> turns = new ArrayList();
	private boolean attackerWin = false;
		
	private int attArcherLoss = 0;
	private int attSpearLoss = 0;
	private int attKnightLoss = 0;
	private int attWizardLoss = 0;
		
	private int defArcherLoss = 0;
	private int defSpearLoss = 0;
	private int defKnightLoss = 0;
	private int defWizardLoss = 0;
	
	private int turnCount;
	
	@Override
	public BattleReportDTO resolve(int bId, int aId, String aName, int dId, String dName, BattleArmy aArmy, BattleArmy dArmy, double towerPower) {
		System.out.println("ATTACKING ARMY");
		System.out.println("a: " + aArmy.archers + " hp: " + aArmy.archersHealth);
		System.out.println("s: " + aArmy.spears + " hp: " + aArmy.spearsHealth);
		System.out.println("k: " + aArmy.knights + " hp: " + aArmy.knightsHealth);
		System.out.println("w: " + aArmy.wizards + " hp: " + aArmy.wizardsHealth);
		
		System.out.println("DEFENDING ARMY");
		System.out.println("a: " + dArmy.archers + " hp: " + dArmy.archersHealth);
		System.out.println("s: " + dArmy.spears + " hp: " + dArmy.spearsHealth);
		System.out.println("k: " + dArmy.knights + " hp: " + dArmy.knightsHealth);
		System.out.println("w: " + dArmy.wizards + " hp: " + dArmy.wizardsHealth);
		
		BattleReportDTO result = new BattleReportDTO(bId, aId, aName, dId, dName, aArmy.archers, aArmy.spears, aArmy.knights, aArmy.wizards, dArmy.archers, dArmy.spears, dArmy.knights, dArmy.wizards);
		
		//defender or attacker might not have any units to begin with
		if (armyDead(dArmy)) {
			attackerWin = true;
		}
		else if (armyDead(aArmy)) {
			attackerWin = false;
		}
		
		//do battle turn resolution
		else {
			boolean attackerTurn = true;
			turnCount = 1;
			while(!armyDead(aArmy) && !armyDead(dArmy)) {
				//switch turns between attacker and defender
				archerAttack(attackerTurn, aArmy, 0, dArmy);
				attackerTurn = !attackerTurn;
				archerAttack(attackerTurn, dArmy, towerPower, aArmy);
				attackerTurn = !attackerTurn;
				
				spearAttack(attackerTurn, aArmy, 0, dArmy);
				attackerTurn = !attackerTurn;
				spearAttack(attackerTurn, dArmy, towerPower, aArmy);
				attackerTurn = !attackerTurn;
				
				knightAttack(attackerTurn, aArmy, 0, dArmy);
				attackerTurn = !attackerTurn;
				knightAttack(attackerTurn, dArmy, towerPower, aArmy);
				attackerTurn = !attackerTurn;
				
				wizardAttack(attackerTurn, aArmy, 0, dArmy);
				attackerTurn = !attackerTurn;
				wizardAttack(attackerTurn, dArmy, towerPower, aArmy);
				attackerTurn = !attackerTurn;
			}
			
			if (armyDead(dArmy)) {
				System.out.println("Attacker wins!");
				attackerWin = true;
			}
			else {
				System.out.println("Attacker Loses!");
				attackerWin = false;
			}
		}
		
		//return battle report
		result.setBattleResult(attackerWin,
				attArcherLoss, attSpearLoss, attKnightLoss, attWizardLoss,
				defArcherLoss, defSpearLoss, defKnightLoss, defWizardLoss,
				turns);

		return result;
	}
	
	/*
	unit targeting priority:
		
	Each unit type tries to attack their own unit type first. (0.7 to same unit) (0.3 spread to all other units)
	If no more of their unit type left, they attack according to general target priority
		
	General target priority: spear -> knight -> archer -> wizard		
	*/
	private void archerAttack(boolean attackerTurn, BattleArmy aArmy, double towerPower, BattleArmy dArmy) {
		if (aArmy.archers <= 0)
			return;
		
		int[] killed = new int[] {0, 0, 0, 0};

		double dmg = (aArmy.archers * constants.ARCHER_DAMAGE) + towerPower;
		
		//direct and splash damage
		double splash = dmg * constants.ARCHER_SPLASH;
		double direct = dmg - splash;

		directAttackOnArchers(direct, dArmy, killed, 0);
		splashAttack(splash, dArmy, killed);
		
		logAttack(attackerTurn, aArmy.archers, "Archers", killed);
	}
	
	private void spearAttack(boolean attackerTurn, BattleArmy aArmy, double towerPower, BattleArmy dArmy) {
		if (aArmy.spears <= 0)
			return;
		
		int[] killed = new int[] {0, 0, 0, 0};

		double dmg = (aArmy.spears * constants.SPEAR_DAMAGE) + towerPower;
		
		//direct and splash damage
		double splash = dmg * constants.SPEAR_SPLASH;
		double direct = dmg - splash;
		
		directAttackOnSpears(direct, dArmy, killed, 0);
		splashAttack(splash, dArmy, killed);

		logAttack(attackerTurn, aArmy.spears, "Spears", killed);
	}

	private void knightAttack(boolean attackerTurn, BattleArmy aArmy, double towerPower, BattleArmy dArmy) {
		if (aArmy.knights <= 0)
			return;
		
		int[] killed = new int[] {0, 0, 0, 0};

		double dmg = (aArmy.knights * constants.KNIGHT_DAMAGE) + towerPower;
		
		//direct and splash damage
		double splash = dmg * constants.KNIGHT_SPLASH;
		double direct = dmg - splash;
		
		directAttackOnKnights(direct, dArmy, killed, 0);
		splashAttack(splash, dArmy, killed);

		logAttack(attackerTurn, aArmy.knights, "Knights", killed);
	}
	
	private void wizardAttack(boolean attackerTurn, BattleArmy aArmy, double towerPower, BattleArmy dArmy) {
		if (aArmy.wizards <= 0)
			return;
		
		int[] killed = new int[] {0, 0, 0, 0};

		double dmg = (aArmy.wizards * constants.WIZARD_DAMAGE) + towerPower;
		
		//direct and splash damage
		double splash = dmg * constants.WIZARD_SPLASH;
		double direct = dmg - splash;
		
		directAttackOnWizards(direct, dArmy, killed, 0);
		splashAttack(splash, dArmy, killed);

		logAttack(attackerTurn, aArmy.wizards, "Wizards", killed);
	}
	
	private void splashAttack(double splash, BattleArmy dArmy, int[] killed) {
		splash = splash / 4;
		
		directAttackOnArchers(splash, dArmy, killed, 0);
		directAttackOnSpears(splash, dArmy, killed, 0);
		directAttackOnKnights(splash, dArmy, killed, 0);
		directAttackOnWizards(splash, dArmy, killed, 0);
	}
	
	private void directAttackOnArchers(double dmg, BattleArmy dArmy, int[] killed, int targetEscalation) {		
		if (dmg <= dArmy.archersHealth) {
			dArmy.archersHealth -= dmg;
			int newArchers = (int)Math.ceil(dArmy.archersHealth / constants.ARCHER_HEALTH);
			
			killed[0] += dArmy.archers - newArchers;
			dArmy.archers = newArchers;
		}
		else if (dArmy.archersHealth >= 0) {
			killed[0] += dArmy.archers;
			dArmy.archers = 0;
			dmg -= dArmy.archersHealth;
			
			dArmy.archersHealth = 0;
			
			if (targetEscalation == 0) {
				directAttackOnSpears(dmg, dArmy, killed, targetEscalation+1);
			}
			else if (targetEscalation == 2) {
				directAttackOnWizards(dmg, dArmy, killed, targetEscalation+1);
			}
		}
	}
	
	private void directAttackOnSpears(double dmg, BattleArmy dArmy, int[] killed, int targetEscalation) {
		if (dmg <= dArmy.spearsHealth) {
			dArmy.spearsHealth -= dmg;
			int newSpears = (int)Math.ceil(dArmy.spearsHealth / constants.SPEAR_HEALTH);
			
			killed[1] += dArmy.spears - newSpears;
			dArmy.spears = newSpears;
		}
		else if (dArmy.spearsHealth >= 0) {
			killed[1] += dArmy.spears;
			dArmy.spears = 0;
			dmg -= dArmy.spearsHealth;
			
			dArmy.spearsHealth = 0;
			
			directAttackOnKnights(dmg, dArmy, killed, targetEscalation+1);
		}
	}
	
	private void directAttackOnKnights(double dmg, BattleArmy dArmy, int[] killed, int targetEscalation) {
		if (dmg <= dArmy.knightsHealth) {
			dArmy.knightsHealth -= dmg;
			int newKnights = (int)Math.ceil(dArmy.knightsHealth / constants.KNIGHT_HEALTH);
			
			killed[2] += dArmy.knights - newKnights;
			dArmy.knights = newKnights;
		}
		else if (dArmy.knightsHealth >= 0) {
			killed[2] += dArmy.knights;
			dArmy.knights = 0;
			dmg -= dArmy.knightsHealth;
			
			dArmy.knightsHealth = 0;
			
			if (targetEscalation == 0) {
				directAttackOnSpears(dmg, dArmy, killed, targetEscalation+1);
			}
			else if (targetEscalation == 2) {
				directAttackOnArchers(dmg, dArmy, killed, targetEscalation+1);
			}
		}
	}
	
	private void directAttackOnWizards(double dmg, BattleArmy dArmy, int[] killed, int targetEscalation) {
		if (dmg <= dArmy.wizardsHealth) {
			dArmy.wizardsHealth -= dmg;
			int newWizards = (int)Math.ceil(dArmy.wizardsHealth / constants.WIZARD_HEALTH);

			killed[3] += dArmy.wizards - newWizards;
			dArmy.wizards = newWizards;
		}
		else if (dArmy.wizardsHealth >= 0) {
			killed[3] += dArmy.wizards;
			dArmy.wizards = 0;
			dmg -= dArmy.wizardsHealth;
			
			dArmy.wizardsHealth = 0;
			
			if (targetEscalation == 0) {
				directAttackOnSpears(dmg, dArmy, killed, targetEscalation+1);
			}
		}
	}

	private void logAttack(boolean attackerTurn, int attackingUnits, String unitType, int[] killedUnits) {
		System.out.println("turn: " + turnCount + " " + unitType + " kills a: " + killedUnits[0] + " s: " + killedUnits[1] + " k: " + killedUnits[2] + " w: " + killedUnits[3]);
		
		if (attackerTurn) {
			defArcherLoss += killedUnits[0];
			defSpearLoss += killedUnits[1];
			defKnightLoss += killedUnits[2];
			defWizardLoss += killedUnits[3];
		}
		else {
			attArcherLoss += killedUnits[0];
			attSpearLoss += killedUnits[1];
			attKnightLoss += killedUnits[2];
			attWizardLoss += killedUnits[3];
		}

		BattleTurnDTO attack = new BattleTurnDTO(turnCount, attackerTurn, unitType, attackingUnits, killedUnits[0], killedUnits[1], killedUnits[2], killedUnits[3]);
		
		turns.add(attack);
		
		turnCount++;
	}
	
	private boolean armyDead(BattleArmy army) {
		return (army.archers <= 0 && army.spears <= 0 && army.knights <= 0 && army.wizards <= 0);
	}
}
