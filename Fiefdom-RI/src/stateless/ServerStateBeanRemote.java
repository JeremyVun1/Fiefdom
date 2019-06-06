package stateless;

import java.util.Date;
import javax.ejb.Remote;

@Remote
public interface ServerStateBeanRemote {
	
	void recordLogin(String username);
	void recordLogout(String username);

	int getOnlineCount();
	int getTurnCount();
	
	Date getNextTurnDate();
	Date getSystemTime();
	
	boolean ping();
}
