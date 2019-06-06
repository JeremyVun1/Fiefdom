package dto;

import java.io.Serializable;

public class RelationDTO implements Serializable {
	private int id;
	private String name;
	private int power;
	private int rank;
	private String relation;

	public RelationDTO(int fId, String fName, int power, int rank, String relation) {
		this.id = fId;
		this.name = fName;
		this.power = power;
		this.rank = rank;
		this.relation = relation;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPower() {
		return power;
	}

	public int getRank() {
		return rank;
	}

	public String getRelation() {
		return relation;
	}
}
