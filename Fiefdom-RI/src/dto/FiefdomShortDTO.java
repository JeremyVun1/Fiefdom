package dto;

import java.io.Serializable;

public class FiefdomShortDTO implements Serializable {
	private int id;
	private String name;

	public FiefdomShortDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
