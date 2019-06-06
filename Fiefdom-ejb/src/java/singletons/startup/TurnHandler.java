package singletons.startup;

import entity.Fiefdom;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import gamelogic.internal.EcoFunctionsLocal;
import DAFacades.FiefdomFacade;

@Startup
@Singleton
public class TurnHandler {
	
	@EJB
	ServerInfoBeanLocal serverBean;
	
	@EJB
	EventLoggerBeanLocal eventLogger;
	
	@EJB
	RankPowerBeanLocal rankPower;
	
	@EJB
	FiefdomFacade fiefdomFacade;
	
	@EJB
	EcoFunctionsLocal ecoFunctions;
	
	@PostConstruct
	private void init() {
		System.out.println("SINGLETON turn timer init started");
	}
	
	@Schedule(second="*/10", minute="*", hour="*")
	private void tick() {
		//increase turn count
		serverBean.incTurn();
		
		//update event log
		ArrayList<Fiefdom> fiefdoms = fiefdomFacade.getAllFiefdoms();
		
		for (Fiefdom f : fiefdoms) {
			int goldChange = ecoFunctions.GenerateGold(f);
			int popChange = ecoFunctions.GeneratePeasants(f);
			
			//update entity
			fiefdomFacade.updateFiefdom(f, goldChange, 0, popChange);
			
			//log the changes
			eventLogger.update(f.getId(), goldChange, 0, popChange);
		}
		
		//update rank power
		rankPower.update();
	}
}

