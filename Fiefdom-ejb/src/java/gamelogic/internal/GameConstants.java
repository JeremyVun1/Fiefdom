package gamelogic.internal;

import java.util.HashMap;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;

@ConcurrencyManagement(CONTAINER)
@Singleton
@Startup
@Lock(READ)
public class GameConstants {
	//TURN INTERVAL
	public static final int TURN_INTERVAL_SECS = 10;
	
	//ECONOMY
	public static final double POP_GROWTH_RATE = 0.1;
	public static final double GOLD_GENERATION_MULTI = 0.02;
	
	//POWER MULTIPLIERS
	public static final int LAND_MULTI = 1;
	public static final int PEASANTS_MULTI = 1;
	public static final int ARCHERS_MULTI = 5;
	public static final int KNIGHTS_MULTI = 15;
	public static final int SPEARS_MULTI = 8;
	public static final int SWORDS_MULTI = 8;
	public static final int WIZARD_MULTI = 25;
	
	public static  final int FARMS_MULTI = 2;
	public static final int MARKETS_MULTI = 2;
	public static final int MINES_MULTI = 2;
	public static final int TOWERS_MULTI = 3;
	
	public static final int FARM_COST = 100;
	public static final int MARKET_COST = 150;
	public static final int MINE_COST = 200;
	public static final int TOWER_COST = 150;
	
	public static final String FARM_EFFECT = "Provides food for peasant growth";
	public static final String MARKET_EFFECT = "Provdes gold income";
	public static final String MINE_EFFECT = "Provides more gold income";
	public static final String TOWER_EFFECT = "Provides defense against attacks";
	
	public static final int KNIGHT_COST = 50;
	public static final int ARCHER_COST = 10;
	public static final int SPEAR_COST = 10;
	public static final int WIZARD_COST = 100;
	
	public static final int EXPLORE_COST = 50;
	
	//WAR
	public static final double TOWER_STRENGTH = 50;
	
	public static final double ARCHER_DAMAGE = 2;
	public static final double ARCHER_SPLASH = 0.3;
	public static final double ARCHER_HEALTH = 2;
	
	public static final double SPEAR_DAMAGE = 1;
	public static final double SPEAR_SPLASH = 0.1;
	public static final double SPEAR_HEALTH = 9;
	
	public static final double KNIGHT_DAMAGE = 8;
	public static final double KNIGHT_SPLASH = 0.2;
	public static final double KNIGHT_HEALTH = 15;
	
	public static final double WIZARD_DAMAGE = 20;
	public static final double WIZARD_SPLASH = 0.5;
	public static final double WIZARD_HEALTH = 3;
	
	//MESSAGE TYPES
	public static HashMap<String, String> getMESSAGE_TYPES() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("Diplomatic Message", "ME");
		result.put("Request Peace", "RP");
		result.put("Declare War", "DW");
		result.put("Request Alliance", "RA");
		result.put("Break Alliance", "BA");
		
		return result;
	}
	
	//RACE STRINGS
	public static String raceStr(int race) {
		switch (race) {
			case 0:
				return "Human";
			case 1:
				return "Elf";
			case 2:
				return "Orc";
			default:
				return "Unknown";
		}
	}

	public static int raceInt(String race) {
		switch (race) {
			case "Human":
				return 0;
			case "Elf":
				return 1;
			case "Orc":
				return 2;
			default:
				return 0;
		}
	}
}
