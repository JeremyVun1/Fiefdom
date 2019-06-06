package web;

import dto.SessionDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import stateless.ServerStateBeanRemote;
import stateless.GameInfoBeanRemote;

@Named(value = "sessMBean")
@SessionScoped
public class SessionMBean implements Serializable {
	
	@EJB
	GameInfoBeanRemote infoBean;
	
	@EJB
	ServerStateBeanRemote serverState;
	
	private String username;
	private int fId;
	private String fName;
	private String race;
	private boolean loggedIn;
	
	public SessionMBean() {
		loggedIn = false;
	}
	
	public void init() {
		SessionDTO s = infoBean.getSessionInfo();
		
		username = s.getUsername();
		fId = s.getfId();
		fName = s.getfName();
		race = s.getRace();
	}
	
	public void recordLogin() {
		serverState.recordLogin(username);
		loggedIn = true;
	}
	
	public void recordLogout() {
		serverState.recordLogout(username);
		loggedIn = false;
	}
	
	//destructor to make sure we remove ourselves from the global online player count
	@PreDestroy
	private void cleanup() {
		serverState.recordLogout(username);
	}
	
	//getters and setters
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return (username.substring(0, 1).toUpperCase() + username.substring(1));
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getfId() {
		return fId;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}

	public String getfName() {
		return (fName.substring(0, 1).toUpperCase() + fName.substring(1));
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getRace() {
		return (race.substring(0, 1).toUpperCase() + race.substring(1));
	}

	public void setRace(String race) {
		this.race = race;
	}
	
	public String getTimestamp() {
		return (new Date().toString());
	}
	
	public String getNextTurnDate() {
		return (serverState.getNextTurnDate().toString());
	}
}
