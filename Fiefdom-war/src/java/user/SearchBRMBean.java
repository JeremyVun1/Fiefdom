package user;

import dto.BattleReportShortDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import stateless.WarBeanRemote;

@Named(value = "searchBRMBean")
@ConversationScoped
public class SearchBRMBean implements Serializable {
	
	@EJB
	WarBeanRemote warBean;
	
	@Inject
	private Conversation conversation;

	private int id;
	
	private ArrayList<BattleReportShortDTO> reports;

	@PostConstruct
	private void init() {
		if (conversation.isTransient())
			conversation.begin();
		
		reports = warBean.fetchReportsShort();
	}
	
	public String viewReport() {
		if (!conversation.isTransient())
			conversation.end();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Fiefdom-war/user/report.xhtml?id=" + id);
		} catch(Exception ex) {}
		
		return "/Fiefdom-war/user/report.xhtml?id=" + id;
	}

	//GETTERS AND SETTERS
	public ArrayList<BattleReportShortDTO> getReports() {
		return reports;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
