package dto;

import java.io.Serializable;

public class RankPowerDTO implements Serializable {
	private int power;
	private int rank;

	public RankPowerDTO(int power, int rank) {
		this.power = power;
		this.rank = rank;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
