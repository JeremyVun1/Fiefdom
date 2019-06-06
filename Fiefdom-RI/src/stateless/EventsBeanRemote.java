package stateless;

import dto.EventLogsDTO;
import javax.ejb.Remote;

@Remote
public interface EventsBeanRemote {

	public EventLogsDTO getEventLogs();
	
}
