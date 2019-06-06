package stateless;

import dto.RegisterUserDTO;
import dto.UserinfoBasicDTO;
import dto.UserinfoFullDTO;
import dto.UserinfoFull_pwDTO;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface UserinfoManagementBeanRemote {

	boolean registerUser(RegisterUserDTO regUserDTO);
	
	boolean usernameExists(String username);
	boolean fiefdomNameExists(String fName);
	
	ArrayList<UserinfoBasicDTO> fetchUsersBasic();
	UserinfoFullDTO fetchUserFull(int id);
	UserinfoFullDTO fetchUserFull(String username);
	UserinfoBasicDTO fetchUserBasic(String username);
	
	String getUsername();
	
	ArrayList<UserinfoBasicDTO> fetchUnsuspendedUsersBasic();
	ArrayList<UserinfoBasicDTO> fetchSuspendedUsersBasic();
	
	boolean removeUser(String username);	
	boolean removeSelf(String hashPass);	
	
	boolean updateUser(UserinfoFull_pwDTO u);
	
	boolean suspendUser(String username);
	boolean unsuspendUser(String username);
	
	boolean changePassword(String currPass, String newPass);
	
	boolean isAdmin();
	boolean isUser();
	boolean isSuspended();
}
