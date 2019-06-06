package singletons.startup;

import dto.EventLogsDTO;
import javax.ejb.Local;

@Local
public interface EventLoggerBeanLocal {

	EventLogsDTO getEventLogs(int id);
	
	void recordNewFiefdom(int id);
	
	void removeFiefdom(int id);
	
	void TickOver();
	
	void update(int id, int gold, int land, int pop);
	
	void logEvent(int id, String msg);	
	void logResourceChange(int id, int goldChange, int landChange, int popChange);
	
}
