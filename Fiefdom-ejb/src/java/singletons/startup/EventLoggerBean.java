package singletons.startup;

import dto.EventLogsDTO;
import entity.Fiefdom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.EJB;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import DAFacades.FiefdomFacade;

/*
Use singleton to avoid unecessary db read writes.
Eevents are only temporary and are removed when fetched
 */
@ConcurrencyManagement(CONTAINER)
@Singleton
@Startup
public class EventLoggerBean implements EventLoggerBeanLocal {

	@EJB
	ServerInfoBeanLocal serverBean;

	@EJB
	FiefdomFacade fiefdomFacade;

	//hash map of all event logs for all fiefdoms
	HashMap<Integer, EventLogsDTO> logs;

	@PostConstruct
	@Lock(WRITE)
	private void init() {
		logs = new HashMap<Integer, EventLogsDTO>();

		ArrayList<Fiefdom> fiefdoms = fiefdomFacade.getAllFiefdoms();

		for (Fiefdom f : fiefdoms) {
			logs.put(f.getId(), new EventLogsDTO());
		}
		
		System.out.println("SINGLETON INITIALIZED: EventLogger with fiefdoms: " + fiefdoms.size());
	}

	//when registering new players
	@Override
	@Lock(WRITE)
	public void recordNewFiefdom(int id) {
		//check if we already have a log for the fiefdom id
		if (logs.get(id) != null) {
			return;
		}
		
		logs.put(id, new EventLogsDTO());
	}

	@Override
	@Lock(WRITE)
	public void removeFiefdom(int id) {
		try {
			logs.remove(id);
		} catch (Exception ex) {

		}
	}

	@Override
	@Lock(READ)
	public EventLogsDTO getEventLogs(int fId) {
		//get current log
		EventLogsDTO result = logs.remove(fId);

		//put an empty event log back in
		logs.put(fId, new EventLogsDTO());

		return result;
	}

	//add new eventlog each turn
	@Override
	@Lock(WRITE)
	public void TickOver() {
		for (Map.Entry l : logs.entrySet()) {
			EventLogsDTO e = (EventLogsDTO) l.getValue();
			e.addTurn(serverBean.getTurnCount());
		}
	}

	//add new eventlog each turn with changes in resources
	@Override
	@Lock(WRITE)
	public void update(int id, int goldChange, int landChange, int popChange) {		
		EventLogsDTO e = logs.get(id);
		if (e == null)
			return;

		e.addTurn(serverBean.getTurnCount(), goldChange, landChange, popChange);
	}
	
	@Override
	@Lock(WRITE)
	public void logResourceChange(int id, int goldChange, int landChange, int popChange) {
		EventLogsDTO e = logs.get(id);
		if (e == null)
			return;
		
		e.logResourceChange(goldChange, landChange, popChange, serverBean.getTurnCount());
	}

	@Override
	@Lock(WRITE)
	public void logEvent(int id, String s) {
		EventLogsDTO e = logs.get(id);
		if (e == null) {
			return;
		}

		e.logEvent(s, serverBean.getTurnCount());
	}

}
