package DAFacades;

import dto.CreateThreadDTO;
import dto.MessageDTO;
import dto.ThreadDTO;
import entity.Fiefdom;
import entity.Message;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Thread;
import java.util.Date;
import javax.ejb.EJB;

@Stateless
@LocalBean
public class ThreadFacade {
	
	@EJB
	UserFacade userFacade;
	
	@EJB
	FiefdomFacade fiefdomFacade;

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	private Thread find(int tId){
		return em.find(Thread.class, tId);
	}
	
	public ArrayList<ThreadDTO> getThreads(int callingFId) {
		Fiefdom f = fiefdomFacade.getFiefdomEntById(callingFId);
		if (f == null)
			return null;
		
		Query q = em.createNamedQuery("Thread.findByToId").setParameter("toid", f);
		List<Thread> list = q.getResultList();		
		ArrayList<ThreadDTO> result = DAOList2DTOArrayList(list, true, false);
		
		q = em.createNamedQuery("Thread.findByFromId").setParameter("fromid", f);
		list = q.getResultList();
		result.addAll(DAOList2DTOArrayList(list, false, true));
		
		return result;
	}
	
	private ArrayList<ThreadDTO> DAOList2DTOArrayList(List<Thread> list, boolean to, boolean from) {
		if (list == null)
			return null;
		
		ArrayList<ThreadDTO> result = new ArrayList<ThreadDTO>();
		
		for (Thread t : list) {
			if (to && t.isActiveto())
				result.add(threadDAO2DTO(t));
			else if (from && t.isActivefrom())
				result.add(threadDAO2DTO(t));
		}
		
		return result;
	}
	
	public boolean createThread(CreateThreadDTO sendDTO) {
		if (sendDTO == null)
			return false;
		
		Fiefdom to = fiefdomFacade.getFiefdomEntById(sendDTO.getToId());
		Fiefdom from = fiefdomFacade.getFiefdomEntById(sendDTO.getFromId());
		if (to == null || from == null)
			return false;
		
		try {
			//create the parent thread entity
			Thread t = new Thread(from, to, new Date(), sendDTO.getSubject(), sendDTO.getType());
			
			//set the thread to require a response from the player e.g. request alliance, request peace
			if (sendDTO.getType().equals("RP") || sendDTO.getType().equals("RA"))
				t.setResponded(false);
			else t.setResponded(true);
			
			t.setActivefrom(true);
			t.setActiveto(true);
			
			em.persist(t);

			//create first message in the thread
			Message m = new Message(new Date(), from, sendDTO.getBody(), t);
			em.persist(m);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public ThreadDTO getThread(int tId) {
		Thread t = find(tId);
		
		if (t.isActivefrom() || t.isActiveto())
			return threadDAO2DTO(find(tId));
		else return null;
	}

	public ArrayList<MessageDTO> getThreadMessages(int tId) {
		Thread t = find(tId);
		if (t == null || (!t.isActivefrom() && !t.isActiveto()))
			return null;
		
		Query q = em.createNamedQuery("Message.findByTid").setParameter("tid", t);
		
		List<Message> msgs = q.getResultList();
		
		ArrayList<MessageDTO> result = new ArrayList<MessageDTO>();
		for (Message m : msgs) {
			result.add(messageDAO2DTO(m));
		}
		
		return result;
	}
	
	public boolean recordResponded(int threadId) {
		Thread t = find(threadId);
		
		if (t == null)
			return false;
		
		try {
			t.setResponded(true);
			em.merge(t);
			
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean createMessage(int threadId, int authorId, String body) {
		Fiefdom author = fiefdomFacade.getFiefdomEntById(authorId);
		Thread t = find(threadId);
		
		if (author == null || t == null || (!t.isActivefrom() && !t.isActiveto()) || body == null)
			return false;

		Message m = new Message(new Date(), author, body, t);
		em.persist(m);
		return true;
	}
	
	public boolean deactivate(int threadId, int fId) {
		Thread t = find(threadId);
		
		if (t == null)
			return false;
		
		if (t.getFromid().getId() == fId) {
			t.setActivefrom(false);
			em.merge(t);
			return true;
		} else if (t.getToid().getId() == fId) {
			t.setActiveto(false);
			em.merge(t);
			return true;
		}
		else return false;
	}
	
	private ThreadDTO threadDAO2DTO(Thread t) {
		if (t == null)
			return null;
		
		return new ThreadDTO(t.getTid(), t.getPosted(), t.getSubject(),
				t.getFromid().getId(), t.getFromid().getName(),
				t.getToid().getId(), t.getToid().getName(),
				t.getThreadtype(), t.isResponded());
	}
	
	private MessageDTO messageDAO2DTO(Message m) {
		if (m == null)
			return null;
	
		return new MessageDTO(m.getMid(), m.getTid().getTid(), m.getPosted(), m.getMessage(), m.getAuthor().getId(), m.getAuthor().getName());
	}
}
