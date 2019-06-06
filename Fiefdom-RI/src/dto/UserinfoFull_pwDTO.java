package dto;

import java.io.Serializable;

public class UserinfoFull_pwDTO extends UserinfoFullDTO implements Serializable{
	
	public String password;

	public UserinfoFull_pwDTO(int id, String password, String fName, String race, String email, String rolegroup, String username) {
		super(id, fName, race, email, rolegroup, username);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

}
