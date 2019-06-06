package dto;

import java.io.Serializable;

public class UserinfoBasicDTO implements Serializable{
	
	public int id;
	public String username;

	public UserinfoBasicDTO(int id, String username) {
		this.username = username;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getId() {
		return id;
	}
}
