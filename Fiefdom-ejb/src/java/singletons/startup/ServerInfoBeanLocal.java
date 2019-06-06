package singletons.startup;

import java.util.Date;
import javax.ejb.Local;

@Local
public interface ServerInfoBeanLocal {
	void incTurn();	
	
	void incPlayersOnline();	
	
	void decPlayersOnline();

	int getOnlineCount();
	
	int getTurnCount();
	
	Date getNextTurnDate();
}
