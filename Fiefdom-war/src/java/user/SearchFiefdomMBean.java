package user;

import dto.FiefdomBasicDTO;
import dto.FiefdomShortDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.inject.Inject;
import stateless.DiplomacyBeanRemote;
import stateless.FiefdomBeanRemote;

@Named(value = "searchFiefdomMBean")
@ConversationScoped
public class SearchFiefdomMBean implements Serializable {
	
	@EJB
	FiefdomBeanRemote fiefdomBean;
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	@Inject
	private Conversation conversation;

	private int id;
	private String fiefdomName;
	private int land;
	private String race;
	private int rank;
	private int power;
	
	private ArrayList<FiefdomShortDTO> fiefdoms;

	@PostConstruct
	private void init() {
		if (conversation.isTransient())
			conversation.begin();
		
		fiefdoms = fiefdomBean.fetchFiefdomsShort();
	}
	
	public void chooseFiefdomById() {
		if (id == 0)
			return;
		
		FiefdomBasicDTO f = fiefdomBean.fetchFiefdomBasicById(id);
		
		if (f == null)
			return;
		else populate(f);
	}
	
	public void chooseFiefdomByName() {
		if (fiefdomName == null)
			return;
		
		FiefdomBasicDTO f = fiefdomBean.fetchFiefdomBasicByName(fiefdomName);
		
		if (f == null)
			return;
		else populate(f);
	}
	
	private void populate(FiefdomBasicDTO f) {
		fiefdomName = f.getName();
		land = f.getLand();
		race = f.getRace();
		rank = f.getRank();
		power = f.getPower();
		
		if (!conversation.isTransient())
			conversation.end();
	}
	
	//VALIDATORS
	public void isExistingFiefdom(FacesContext context, UIComponent component, Object value) {
		String fName = (String) value;

		if (fName != null && !fiefdomBean.fiefdomExists(fName)) {
			String message = "Fiefdom " + fName + " does not exist";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			throw new ValidatorException(msg);
		}
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
	
	public String getRelation() {
		if (diplomacyBean.isAllyWith(id))
			return "ALLIANCE";
		else if (diplomacyBean.isAtWarWith(id))
			return "WAR";
		else return "PEACE";
	}

	public ArrayList<FiefdomShortDTO> getFiefdoms() {
		return fiefdoms;
	}
}
