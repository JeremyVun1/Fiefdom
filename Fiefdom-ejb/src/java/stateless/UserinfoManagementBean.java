package stateless;

import DAFacades.UserFacade;
import DAFacades.FiefdomFacade;
import dto.ArmyDTO;
import dto.BuildingsDTO;
import dto.EmailDTO;
import dto.FiefdomDTO;
import dto.RegisterUserDTO;
import dto.UserinfoBasicDTO;
import dto.UserinfoFullDTO;
import dto.UserinfoFull_pwDTO;
import email.EmailingBeanRemote;
import javax.ejb.Stateless;
import entity.Userinfo;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import singletons.startup.EventLoggerBeanLocal;
import singletons.startup.RankPowerBeanLocal;

@DeclareRoles({"ADMIN", "USER", "SUSPENDED"})
@Stateless
public class UserinfoManagementBean implements UserinfoManagementBeanRemote {

	@Resource
	SessionContext context;

	@EJB
	private UserFacade userFacade;

	@EJB
	private FiefdomFacade fiefdomFacade;
	
	@EJB
	private EventLoggerBeanLocal eventLogger;
	
	@EJB
	private RankPowerBeanLocal rankPower;
	
	@EJB
	private EmailingBeanRemote emailBean;

	@Override
	public boolean registerUser(RegisterUserDTO regDTO) {
		if (userFacade.usernameExists(regDTO.getUsername()) || fiefdomFacade.fiefdomNameExists(regDTO.getFiefdomName())) {
			return false;
		} else {
			//create persistant user and fiefom
			userFacade.createUser(regDTO);
			fiefdomFacade.createFiefdom(regDTO);
			
			//get fiefdom information
			FiefdomDTO fDTO = fiefdomFacade.getFiefdomByUsername(regDTO.getUsername());
			ArmyDTO aDTO = fiefdomFacade.getArmyByFId(fDTO.getId());
			BuildingsDTO bDTO = fiefdomFacade.getBuildingsByFId(fDTO.getId());
			
			rankPower.recordNewFiefdom(fDTO, aDTO, bDTO);
			
			//add to event loggger
			eventLogger.recordNewFiefdom(fDTO.getId());
			
			return true;
		}
	}

	@Override
	public boolean usernameExists(String username) {
		return userFacade.usernameExists(username);
	}

	@Override
	public boolean fiefdomNameExists(String fName) {
		return fiefdomFacade.fiefdomNameExists(fName);
	}

	private Userinfo UIDTO2Entity(RegisterUserDTO dto) {
		return new Userinfo(dto.getUsername(), dto.getPassword(), "USER", dto.getEmail());
	}

	@Override
	@RolesAllowed("ADMIN")
	public boolean removeUser(String username) {
		Userinfo u = userFacade.fetchUserByUsername(username);
		
		try {
			FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(u.getUsername());
			if (f == null)
				return false;
			
			//remove from rankings
			rankPower.removeFiefdom(f.getId());
			
			//remove from event logs
			eventLogger.removeFiefdom(f.getId());
					
			//remove fiefdom and user from db
			fiefdomFacade.removeFiefdom(u);
			return userFacade.removeUser(u);
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@RolesAllowed("USER")
	public boolean removeSelf(String hashPass) {
		String username = getUsername();

		if (userFacade.verify(username, hashPass)) {
			Userinfo u = userFacade.fetchUserByUsername(username);
			fiefdomFacade.removeFiefdom(u);
			userFacade.removeUser(u);
			
			String subject = "Fiefdom Account Deleted";
			String body = "Your have deleted your account " + u.getUsername();
			
			EmailDTO email = new EmailDTO(u.getEmail(), subject, body);
			emailBean.sendEmail(email);
			
			return true;
		}

		return false;
	}
	
	@Override
	@RolesAllowed("ADMIN")
	public boolean suspendUser(String username) {
		return userFacade.suspendUser(username);
	}
	
	@Override
	@RolesAllowed("ADMIN")
	public boolean unsuspendUser(String username) {
		return userFacade.unsuspenUser(username);
	}

	@Override
	@RolesAllowed("ADMIN")
	public boolean updateUser(UserinfoFull_pwDTO u) {
		if (u == null)
			return false;
		
		if (fiefdomFacade.updateFiefdomFromUserinfo(u) && userFacade.updateUser(u))
			return true;
		return false;
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean changePassword(String oldPass, String newPass) {
		String username = getUsername();

		if (userFacade.verify(username, oldPass) && userFacade.changePassword(username, newPass)) {
			String subject = "Fiefdom password changed!";
			String body = "Password has been changed for account " + username;			
			String userEmail = userFacade.getUserEmail(username);
			if (userEmail == null)
				return true;
			
			EmailDTO email = new EmailDTO(userEmail, subject, body);
			emailBean.sendEmail(email);
			
			return true;
		}
		else return false;
	}
	
	@Override
	@RolesAllowed("ADMIN")		
	public ArrayList<UserinfoBasicDTO> fetchUnsuspendedUsersBasic() {
		return userFacade.fetchUsersBasic(false);
	}
	
	@Override
	@RolesAllowed("ADMIN")
	public ArrayList<UserinfoBasicDTO> fetchSuspendedUsersBasic() {
		return userFacade.fetchUsersBasic(true);
	}

	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<UserinfoBasicDTO> fetchUsersBasic() {
		return userFacade.fetchUsersBasic();
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public UserinfoBasicDTO fetchUserBasic(String username) {
		if (username == null)
			return null;
		
		return userFacade.fetchUserBasic(username);
	}

	@Override
	@RolesAllowed("ADMIN")
	public UserinfoFullDTO fetchUserFull(int uId) {
		Userinfo u = userFacade.fetchUserById(uId);
		if (u == null)
			return null;
		
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(u.getUsername());
		if (f == null)
			return null;
		
		return new UserinfoFullDTO(u.getId(), f.getName(), f.getRace(), u.getEmail(), u.getRolegroup(), u.getUsername());
	}
	
	@Override
	@RolesAllowed("ADMIN")
	public UserinfoFullDTO fetchUserFull(String username) {
		if (username == null)
			return null;
		
		Userinfo u = userFacade.fetchUserByUsername(username);		
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(username);
		
		if (u == null || f == null)
			return null;
		
		return new UserinfoFullDTO(u.getId(), f.getName(), f.getRace(), u.getEmail(), u.getRolegroup(), u.getUsername());
	}

	@Override
	@RolesAllowed({"USER", "SUSPENDED"})
	public String getUsername() {
		return context.getCallerPrincipal().getName();
	}
	
	@RolesAllowed({"USER"})
	public Userinfo getUserinfo() {
		String username = getUsername();
		return userFacade.fetchUserByUsername(username);
	}

	@Override
	@RolesAllowed({"ADMIN", "USER", "SUSPENDED"})
	public boolean isAdmin() {
		return (context.isCallerInRole("ADMIN"));
	}

	@Override
	@RolesAllowed({"ADMIN", "USER", "SUSPENDED"})
	public boolean isUser() {
		return (context.isCallerInRole("USER"));
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER", "SUSPENDED"})
	public boolean isSuspended() {
		return (context.isCallerInRole("SUSPENDED"));
	}
}
