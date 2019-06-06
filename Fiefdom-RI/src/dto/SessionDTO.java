package dto;

import java.io.Serializable;

public class SessionDTO implements Serializable {
	private String username;
	private int fId;
	private String fName;
	private String race;

	public SessionDTO(String username, int fId, String fName, String race) {
		this.username = username;
		this.fId = fId;
		this.fName = fName;
		this.race = race;
	}

	public String getUsername() {
		return username;
	}

	public int getfId() {
		return fId;
	}

	public String getfName() {
		return fName;
	}

	public String getRace() {
		return race;
	}
	
	
	
}
