package dto;

import java.io.Serializable;

public class FiefdomDTO extends FiefdomBasicDTO implements Serializable {
	private int gold;
	private int food;
	private int peasants;

	public FiefdomDTO(int id, String name, String race, int gold, int land, int food, int peasants) {
		super(race, land, id, name);
		this.gold = gold;
		this.food = food;
		this.peasants = peasants;
	}

	public int getGold() {
		return gold;
	}

	public int getFood() {
		return food;
	}

	public int getPeasants() {
		return peasants;
	}
}
