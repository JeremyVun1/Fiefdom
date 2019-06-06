package stateless;

import DAFacades.ThreadFacade;
import DAFacades.FiefdomFacade;
import DAFacades.RelationFacade;
import dto.CreateThreadDTO;
import dto.FiefdomDTO;
import dto.FiefdomShortDTO;
import dto.MessageDTO;
import dto.RelationDTO;
import dto.ThreadDTO;
import gamelogic.internal.GameConstants;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import singletons.startup.EventLoggerBeanLocal;

@Stateless
@DeclareRoles({"ADMIN", "USER"})
public class DiplomacyBean implements DiplomacyBeanRemote {
	
	@EJB
	UserinfoManagementBeanRemote userinfoBean;
	
	@EJB
	RelationFacade relationFacade;
	
	@EJB
	ThreadFacade threadFacade;
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	GameConstants constants;

	@Override
	@RolesAllowed("USER")
	public ArrayList<RelationDTO> getRelations() {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		if (f == null)
			return null;
		
		return relationFacade.getRelations(f.getId());
	}
	
	@Override
	@RolesAllowed("USER")
	public ArrayList<ThreadDTO> getThreads() {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		if (f == null)
			return null;
		
		return threadFacade.getThreads(f.getId());
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean startThread(CreateThreadDTO sendDTO) {
		if (sendDTO == null)
			return false;
		
		//check if sender is the current logged in user
		if (!senderIsLoggedIn(sendDTO))
			return false;
		
		//create thread and update relations
		if (updateRelations(sendDTO.getFromId(), sendDTO.getToId(), sendDTO.getType())
				&& threadFacade.createThread(sendDTO)) {
			return true;
		} else return false;
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean accept(ThreadDTO thread) {
		if (thread == null)
			return false;
		
		String action = null;
		if (thread.getType().equals("RP"))
			action = "AP";
		else if (thread.getType().equals("RA"))
			action = "AA";
		
		if (updateRelations(thread.getFromId(), thread.getToId(), action)
				&& threadFacade.recordResponded(thread.getThreadId()))
			return true;
		else return false;
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean decline(ThreadDTO thread) {
		if (thread == null)
			return false;
		
		return threadFacade.recordResponded(thread.getThreadId());
	}
	
	private boolean updateRelations(int a, int b, String type) {		
		FiefdomDTO aDTO = fiefdomFacade.getFiefdomById(a);
		FiefdomDTO bDTO = fiefdomFacade.getFiefdomById(b);
		
		if (type == null)
			return false;
		
		switch (type) {
			case "DW": //declare war
				eventLogger.logEvent(a, "At war with (#" + b + ")" + bDTO.getName());
				eventLogger.logEvent(b, "At war with (#" + a + ")" + aDTO.getName());
				return relationFacade.setWarBetween(a, b);
			case "BA": //break alliance
				eventLogger.logEvent(a, "Alliance broken with (#" + b + ")" + bDTO.getName());
				eventLogger.logEvent(b, "Alliance broken with (#" + a + ")" + aDTO.getName());
				return relationFacade.breakAllianceBetween(a, b);
			case "AP": //accept peace
				eventLogger.logEvent(a, "At peace with (#" + b + ")" + bDTO.getName());
				eventLogger.logEvent(b, "At peace with (#" + a + ")" + aDTO.getName());
				return relationFacade.setPeaceBetween(a, b);
			case "AA": //accept alliance
				eventLogger.logEvent(a, "Allied with (#" + b + ")" + bDTO.getName());
				eventLogger.logEvent(b, "Allied with (#" + a + ")" + aDTO.getName());
				return relationFacade.setAllianceBetween(a, b);
			default:
				return true;
		}
	}
	
	private boolean senderIsLoggedIn(CreateThreadDTO sendDTO) {		
		if (sendDTO == null)
			return false;
		
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		if (f == null)
			return false;
		
		if (f.getId() != sendDTO.getFromId())
			return false;
		else return true;
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean sendMessage(ThreadDTO thread, String body) {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (f == null)
			return false;
		
		return threadFacade.createMessage(thread.getThreadId(), f.getId(), body);
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public boolean deleteThread(ThreadDTO thread) {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		if (f == null)
			return false;
		
		return threadFacade.deactivate(thread.getThreadId(), f.getId());
	}
	
	@Override
	@RolesAllowed("USER")
	public HashMap<String, String> getMessageTypes() {
		return constants.getMESSAGE_TYPES();
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean isAllyWith(int toFId) {
		ArrayList<RelationDTO> relations = getRelations();
		
		for (RelationDTO r : relations) {
			if (r.getId() == toFId && r.getRelation().toUpperCase().equals("A"))
				return true;
		}
		
		return false;
	}
	
	@Override
	@RolesAllowed("USER")
	public boolean isAtWarWith(int toFId) {
		ArrayList<RelationDTO> relations = getRelations();
		
		for (RelationDTO r : relations) {
			if (r.getId() == toFId && r.getRelation().toUpperCase().equals("W"))
				return true;
		}
		
		return false;
	}

	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ThreadDTO getThread(int tId) {
		return threadFacade.getThread(tId);
	}

	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<MessageDTO> getThreadMessages(int tId) {
		return threadFacade.getThreadMessages(tId);
	}
	
	@Override
	@RolesAllowed({"ADMIN", "USER"})
	public ArrayList<FiefdomShortDTO> getWarTargets() {
		String ctxUsername = userinfoBean.getUsername();
		FiefdomDTO f = fiefdomFacade.getFiefdomByUsername(ctxUsername);
		
		return relationFacade.getAllAtWarWith(f.getId());
	}
}
