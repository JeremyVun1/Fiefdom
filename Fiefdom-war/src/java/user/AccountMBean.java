package user;

import java.security.MessageDigest;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.validator.constraints.NotBlank;
import stateless.UserinfoManagementBeanRemote;
import validation.ValidPassword;
import web.LoginMBean;
import web.SessionMBean;

@Named(value = "accountMBean")
@RequestScoped
public class AccountMBean {
	
	@EJB
	UserinfoManagementBeanRemote userBean;
	
	@Inject
	SessionMBean sessBean;
	
	@Inject
	LoginMBean loginBean;
	
	@Size(min = 8, message = "password must be atleast 8 characters")
	@NotBlank
	@ValidPassword
	private String delPass;

	public String delete() {
		//hash to sha 256 before sending to web server
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(delPass.getBytes("UTF-8"));
			delPass = DatatypeConverter.printHexBinary(hash);
		} catch (Exception ex) {
			return "NotDeleted";
		}
		
		if (delPass != null && userBean.removeSelf(delPass)) {
			loginBean.onlyLogout();
			
			return "Deleted";
		}
		else return "Notdeleted";
	}
	
	//getter and setters
	public String getPassword() {
		return delPass;
	}

	public void setPassword(String password) {
		this.delPass = password;
	}
	
	public boolean isAdmin() {
		return userBean.isAdmin();
	}
	
	public boolean isUser() {
		return userBean.isUser();
	}
	
	public boolean isSuspended() {
		return userBean.isSuspended();
	}
}
