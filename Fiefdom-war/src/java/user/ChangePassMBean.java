package user;

import java.security.MessageDigest;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.validator.constraints.NotBlank;
import stateless.UserinfoManagementBeanRemote;
import validation.ValidPassword;
import web.LoginMBean;

@Named(value = "changePassMBean")
@RequestScoped
public class ChangePassMBean {
	
	@EJB
	UserinfoManagementBeanRemote userBean;
	
	@Inject
	LoginMBean loginBean;
	
	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String oldPassword;

	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String newPassword;
	
	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String confirmPassword;


	public String changePassword() {
		if (oldPassword == null || newPassword == null)
			return "Failure";
		
		//hash to sha 256 before sending to web server
		oldPassword = sha256Hash(oldPassword);
		newPassword = sha256Hash(newPassword);
		
		if (userBean.changePassword(oldPassword, newPassword))
			return "Success";
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
	
	//validators
	public void validatePasswordPair(FacesContext context, UIComponent componentToValidate, Object pwdValue) {
		String pwd = (String) pwdValue;

		UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
		String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

		if (!pwd.equals(cnfPwd)) {
			FacesMessage message = new FacesMessage(
					"New Password and Confirm Password are not the same!");
			throw new ValidatorException(message);
		}
	}

	//getters and setters
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
