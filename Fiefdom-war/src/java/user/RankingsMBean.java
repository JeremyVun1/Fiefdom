package user;

import dto.FiefdomBasicDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import stateless.DiplomacyBeanRemote;
import stateless.FiefdomBeanRemote;

@Named(value = "rankingsMBean")
@RequestScoped
public class RankingsMBean implements Serializable {
	
	@EJB
	FiefdomBeanRemote fiefdomBean;
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;

	private int id;
	private String fiefdomName;
	private int land;
	private String race;
	private int rank;
	private int power;
	
	private ArrayList<FiefdomBasicDTO> fiefdoms;

	@PostConstruct
	private void init() {
		fiefdoms = fiefdomBean.fetchFiefdomsBasic();
	}

	//GETTERS AND SETTES
	public String getFiefdomName() {
		return fiefdomName;
	}

	public void setFiefdomName(String fiefdomName) {
		this.fiefdomName = fiefdomName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRace() {
		return race;
	}

	public int getLand() {
		return land;
	}

	public int getRank() {
		return rank;
	}

	public int getPower() {
		return power;
	}

	public ArrayList<FiefdomBasicDTO> getFiefdoms() {
		return fiefdoms;
	}
}
