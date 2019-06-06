package dto;

import java.io.Serializable;

public class RegisterUserDTO implements Serializable{
	public String fiefdomName;
	public int race;
	
	public String username;
	public String password;
	public String email;

	public RegisterUserDTO(String fiefdomName, int race, String username, String password, String email) {
		this.fiefdomName = fiefdomName;
		this.race = race;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getFiefdomName() {
		return fiefdomName;
	}

	public int getRace() {
		return race;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	
}
