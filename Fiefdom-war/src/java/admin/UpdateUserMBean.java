package admin;

import dto.RacesDTO;
import dto.UserinfoBasicDTO;
import dto.UserinfoFullDTO;
import dto.UserinfoFull_pwDTO;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import stateless.UserinfoManagementBeanRemote;
import stateless.GameInfoBeanRemote;

@Named(value = "updateUserMBean")
@ConversationScoped
public class UpdateUserMBean implements Serializable {
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	GameInfoBeanRemote infoBean;
	
	@Inject
	private Conversation conversation;

	private int id;
	private String fiefdomName;
	private String username;
	private String password;
	private String confirmPassword;
	private String email;
	private String roleGroup;
	private int race;
	
	private RacesDTO racesDTO;
	private Map<String, Integer> races;
	private ArrayList<UserinfoBasicDTO> users;
	
	private UserinfoFullDTO fetched;
	private boolean userChosen;

	public boolean isUserChosen() {
		return userChosen;
	}

	public void setUserChosen(boolean userChosen) {
		this.userChosen = userChosen;
	}	

	@PostConstruct
	private void init() {
		if (conversation.isTransient())
			conversation.begin();
		
		users = userinfoBean.fetchUsersBasic();
		
		racesDTO = infoBean.getRaces();
		races = racesDTO.getRaces();
		
		userChosen = false;
	}
	
	public void chooseUserById() {
		UserinfoFullDTO u = userinfoBean.fetchUserFull(id);
		
		if (u == null)
			return;
		else populate(u);
	}
	
	public void chooseUserByUsername() {
		if (username == null)
			return;
		
		UserinfoFullDTO u = userinfoBean.fetchUserFull(username);
		
		if (u == null)
			return;
		else populate(u);
	}
	
	private void populate(UserinfoFullDTO u) {		
		fiefdomName = u.getFiefdomName();
		username = u.getUsername();
		email = u.getEmail();
		roleGroup = u.getRolegroup();
		race = racesDTO.getRaceInt(u.getRace());
		
		//save a copy so that we can do evaluation of changes
		fetched = u;
		userChosen = true;
	}
	
	public String updateUser() {		
		//hash any password before we send
		password = sha256Hash(password);
		UserinfoFull_pwDTO send = new UserinfoFull_pwDTO(id, password, fiefdomName, racesDTO.getRaceStr(race), email, roleGroup, username);
		
		if (userinfoBean.updateUser(send)) {
			conversation.end();
			return "Success";
		}
		
		conversation.end();
		return "Failure";
	}
	
	private String sha256Hash(String password) {
		if (password == null)
			return null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
		} catch (Exception ex) { }
		
		return password;
	}
	
	//VALIDATORS
	public void isExistingUser(FacesContext context, UIComponent component, Object value) {
		String uName = (String) value;

		if (uName != null && !userinfoBean.usernameExists(uName)) {
			String message = "username " + uName + " not found";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	public void isNotExistingUser(FacesContext context, UIComponent component, Object value) {
		String uName = (String) value;

		if (uName != null && !uName.equals(fetched.getUsername()) && userinfoBean.usernameExists(uName)) {
			String message = "username " + uName + " already taken";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	public void isNotExistingFiefdomName(FacesContext context, UIComponent component, Object value) {
		String fName = (String) value;

		if (fName != null && !fName.equals(fetched.getFiefdomName()) && userinfoBean.fiefdomNameExists(fName)) {
			String message = "fiefdom name " + fName + " already taken!";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}
	
	public void validatePasswordPair(FacesContext context, UIComponent componentToValidate, Object pwdValue) {
		String pwd = (String) pwdValue;

		UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
		String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();
		
		if (pwd == null && cnfPwd == null)
			return;

		if (!pwd.equals(cnfPwd)) {
			FacesMessage message = new FacesMessage(
					"Password and Confirm Password are not the same! They must be the same.");
			throw new ValidatorException(message);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<UserinfoBasicDTO> getUsers() {
		return users;
	}

	public String getFiefdomName() {
		return fiefdomName;
	}

	public void setFiefdomName(String fiefdomName) {
		this.fiefdomName = fiefdomName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public Map<String, Integer> getRaces() {
		return races;
	}

	public void setRaces(Map<String, Integer> races) {
		this.races = races;
	}
	
}
