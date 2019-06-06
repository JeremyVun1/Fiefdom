package structs;

import java.util.Comparator;

//helper struct for sorting
public class BattleArmy {
	public int archers;
	public double archersHealth;
	
	public int spears;
	public double spearsHealth;
	
	public int wizards;
	public double wizardsHealth;
	
	public int knights;
	public double knightsHealth;

	public BattleArmy(int archers, double archersHealth, int spears, double spearsHealth, int knights, double knightsHealth, int wizards, double wizardsHealth) {
		this.archers = archers;
		this.archersHealth = archersHealth;
		this.spears = spears;
		this.spearsHealth = spearsHealth;
		this.wizards = wizards;
		this.wizardsHealth = wizardsHealth;
		this.knights = knights;
		this.knightsHealth = knightsHealth;
	}
}
