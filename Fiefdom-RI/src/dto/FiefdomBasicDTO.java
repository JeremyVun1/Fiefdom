package dto;

import java.io.Serializable;

public class FiefdomBasicDTO extends FiefdomShortDTO implements Serializable {
	private String race;
	private int land;
	private int rank;
	private int power;

	public FiefdomBasicDTO(String race, int land, int id, String name) {
		super(id, name);
		this.race = race;
		this.land = land;
		this.rank = rank;
		this.power = power;
	}

	public String getRace() {
		return race;
	}

	public int getLand() {
		return land;
	}

	public int getRank() {
		return rank;
	}

	public int getPower() {
		return power;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
