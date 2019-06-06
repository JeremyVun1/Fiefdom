package user;

import dto.FiefdomDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import stateless.FiefdomBeanRemote;
import stateless.UserinfoManagementBeanRemote;
import web.SessionMBean;

@Named(value = "fiefdomInfoMBean")
@RequestScoped
public class FiefdomInfoMBean {	
	@EJB
	FiefdomBeanRemote fiefdomBean;
	
	@EJB
	UserinfoManagementBeanRemote userBean;
	
	@Inject
	SessionMBean sessBean;
	
	private FiefdomDTO fdto;
	
	@PostConstruct
	private void init() {
		fdto = fiefdomBean.getFiefdom();
	}
	
	public boolean isSuspended() {
		return userBean.isSuspended();
	}
	
	public int getId() {
		return fdto.getId();
	}
	
	public String getfName() {
		return fdto.getName();
	}

	//getter and setter spam
	public int getGold() {
		return fdto.getGold();
	}

	public int getPeasants() {
		return fdto.getPeasants();
	}

	public int getLand() {
		return fdto.getLand();
	}

	public String getRace() {
		return fdto.getRace();
	}
	
	public int getPower() {
		return fdto.getPower();
	}
	
	public int getRank() {
		return fdto.getRank();
	}
}
