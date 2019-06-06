package user;

import dto.RelationDTO;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.DiplomacyBeanRemote;

@Named(value = "relationsMBean")
@RequestScoped
public class RelationsMBean {
	
	@EJB
	DiplomacyBeanRemote diplomacyBean;
	
	private ArrayList<RelationDTO> relations;

	@PostConstruct
	private void init() {
		relations = diplomacyBean.getRelations();
	}
	
	public ArrayList<RelationDTO> getWars() {
		return filterRelations("W");
	}
	
	public ArrayList<RelationDTO> getAlliances() {
		return filterRelations("A");
	}
	
	private ArrayList<RelationDTO> filterRelations(String relation) {
		ArrayList<RelationDTO> result = new ArrayList<RelationDTO>();
		
		for (RelationDTO r : relations) {
			if (r == null)
				System.out.println("ERROR: RELATION IS NULL???");
			else if (r.getRelation().toUpperCase().equals(relation))
				result.add(r);
		}
		
		return result;
	}
}
