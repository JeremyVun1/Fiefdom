package web;

import java.security.MessageDigest;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import stateless.UserinfoManagementBeanRemote;

@Named(value = "loginMBean")
@RequestScoped
public class LoginMBean {
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@Inject
	SessionMBean sessionBean;
	
	private String username;
	private String password;
	
	public String login(String u, String p) {
		username = u;
		password = p;
		return login();
	}

	public String login() {		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		try {
			request.login(username.toLowerCase(), password);
		} catch (ServletException e) {
			context.addMessage(null, new FacesMessage("Login failed."));
			return "Failure";
		}
		
		if (userinfoBean.isAdmin()){
			sessionBean.setLoggedIn(true);
			return "AdminSuccess";
		}
		
		sessionBean.init();
		sessionBean.recordLogin();
		return "UserSuccess";
	}
	
	private String sha256Hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			password = DatatypeConverter.printHexBinary(hash);
		} catch (Exception ex) { }
		
		return password;
	}
	
	public void onlyLogout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			sessionBean.recordLogout();
			request.logout();
		} catch (ServletException e) {
			context.addMessage(null, new FacesMessage("Logout failed."));
		}
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		try {
			sessionBean.recordLogout();
			request.logout();
		} catch (ServletException e) {
			context.addMessage(null, new FacesMessage("Logout failed."));
		}
		
		//redirect
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Fiefdom-war/index.xhtml");
		} catch(Exception ex) {}
		
		return "/Fiefdom-war/index.xhtml";
	}

	//getter and setter spam
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
}
