package web;

import dto.EmailDTO;
import dto.RacesDTO;
import dto.RegisterUserDTO;
import javax.inject.Named;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import stateless.UserinfoManagementBeanRemote;
import validation.IsAlphaNumeric;
import validation.ValidPassword;
import email.EmailingBeanRemote;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import stateless.GameInfoBeanRemote;

@Named(value = "signupMBean")
@RequestScoped
public class SignUpMBean {

	@EJB
	private GameInfoBeanRemote infoBean;

	@EJB
	private UserinfoManagementBeanRemote userBean;

	@EJB
	private EmailingBeanRemote emailBean;

	@Inject
	LoginMBean loginBean;

	@Inject
	SessionMBean sessionBean;

	@Size(max = 20, message = "Fiefdom name cannot be greater than 20 characters")
	@NotBlank
	@IsAlphaNumeric(message = "Fiefdom name must only contain alphanumeric characters")
	private String fiefdomName;

	@Size(max = 15, message = "username cannot be grearer than 15 characters")
	@NotBlank
	@IsAlphaNumeric(message = "username must only contain alphanumeric characters")
	private String username;

	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String password;

	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String confirmPassword;

	@Size(max = 30, message = "email cannot be greater than 30 characters")
	@NotBlank
	@Email
	private String email;

	private int race;
	private Map<String, Integer> races;

	public Map<String, Integer> getRaces() {
		RacesDTO result = infoBean.getRaces();
		return result.getRaces();
	}

	public String registerUser() {
		if (validUserInput()) {
			//hash password before sending
			String hashPass = sha256Hash(password);

			RegisterUserDTO userDetails = new RegisterUserDTO(fiefdomName, race, username.toLowerCase(), hashPass, email);
			boolean result = userBean.registerUser(userDetails);

			if (result) {
				if (loginBean.login(username, password).equals("UserSuccess")) {
					//send welcome email
					String subject = "Fiefdom account created";
					String body = "Welcome " + username + ". You have created a " + sessionBean.getRace() + " Fiefdom called " + fiefdomName;
					emailBean.sendEmail(new EmailDTO(email, subject, body));
					
					return "UserSuccess";
				}
				else {
					System.out.println("ERROR: welcome email not sending");
				}
			}
		}

		return "Failure";
	}
	
	private String sha256Hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
		} catch (Exception ex) { }
		
		return password;
	}

	private boolean validUserInput() {
		return (fiefdomName != null && username != null && password != null && password.equals(confirmPassword) && email != null && race >= 0);
	}

	//validators
	public void isUniqueUsername(FacesContext context, UIComponent component, Object value) {
		String uName = (String) value;

		if (uName != null && userBean.usernameExists(uName)) {
			String message = uName + " already taken!";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}

	public void isUniqueFiefdomName(FacesContext context, UIComponent component, Object value) {
		String fName = (String) value;

		if (fName != null && userBean.fiefdomNameExists(fName)) {
			String message = fName + " already taken!";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
	}

	public void validatePasswordPair(FacesContext context, UIComponent componentToValidate, Object pwdValue) {
		String pwd = (String) pwdValue;

		UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
		String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

		if (!pwd.equals(cnfPwd)) {
			FacesMessage message = new FacesMessage(
					"Password and Confirm Password are not the same! They must be the same.");
			throw new ValidatorException(message);
		}
	}

	//getter setter spam
	public String getFiefdomName() {
		return fiefdomName;
	}

	public void setFiefdomName(String fiefdomName) {
		this.fiefdomName = fiefdomName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

}
