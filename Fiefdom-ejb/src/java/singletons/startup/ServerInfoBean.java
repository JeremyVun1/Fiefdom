package singletons.startup;

import gamelogic.internal.GameConstants;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.EJB;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import DAFacades.GamestateFacade;

//singleton to cache simple global data like turn number
//and number of online players. Reduce database hits
//faster than counting all logged in users in the db each time
@ConcurrencyManagement(CONTAINER)
@Singleton
@Startup
public class ServerInfoBean implements ServerInfoBeanLocal {
	@EJB
	GamestateFacade gamestateFacade;
	
	@EJB
	GameConstants constants;
	
	private int online;
	private int turn;
	private Date nextTurnDate;
	
	@PostConstruct
	@Lock(WRITE)
	private void init() {
		online = 0;
		turn = gamestateFacade.getTurn();
		nextTurnDate = calcNextTurnDate();
		
		System.out.println("SINGLETON INITIALIZED: RankPowers");
	}
	
	private Date calcNextTurnDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, constants.TURN_INTERVAL_SECS);
		return now.getTime();
	}
	
	@Lock(WRITE)
	@Override
	public void incTurn() {
		turn++;
		
		nextTurnDate = calcNextTurnDate();
		
		gamestateFacade.setTurn(turn);
	}
	
	@Lock(WRITE)
	@Override
	public void incPlayersOnline() {
		online++;
	}
	
	@Lock(WRITE)
	@Override
	public void decPlayersOnline() {
		if(online<=0)
			return;
		
		online--;
	}

	@Lock(READ)
	@Override
	public int getOnlineCount() {
		return online;
	}
	
	@Lock(READ)
	@Override
	public int getTurnCount() {
		return turn;
	}
	
	@Lock(READ)
	@Override
	public Date getNextTurnDate() {
		return nextTurnDate;
	}
	
}
