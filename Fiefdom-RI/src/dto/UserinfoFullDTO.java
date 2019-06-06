package dto;

import java.io.Serializable;

public class UserinfoFullDTO extends UserinfoBasicDTO implements Serializable{
	
	public String fiefdomName;
	public String race;
	public String email;
	public String rolegroup;

	public UserinfoFullDTO(int id, String fName, String race, String email, String rolegroup, String username) {
		super(id, username);
		this.fiefdomName = fName;
		this.race = race;
		this.email = email;
		this.rolegroup = rolegroup;
	}

	public String getFiefdomName() {
		return fiefdomName;
	}

	public String getRace() {
		return race;
	}

	public String getEmail() {
		return email;
	}

	public String getRolegroup() {
		return rolegroup;
	}
	
	

}
