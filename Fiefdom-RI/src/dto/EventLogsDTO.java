package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class EventLogsDTO implements Serializable {
	
	ArrayList<TurnDTO> logs;
	
	public EventLogsDTO() {
		logs = new ArrayList<TurnDTO>();
	}

	public EventLogsDTO(ArrayList<TurnDTO> logs) {
		this.logs = logs;
	}

	public ArrayList<TurnDTO> getLogs() {
		return logs;
	}
	
	public void addTurn(int turnCount) {
		logs.add(new TurnDTO(turnCount));
	}
	
	public void logEvent(String e, int turnCount) {
		if (logs == null) {
			logs = new ArrayList<TurnDTO>();
		}
		
		if (logs.isEmpty()) {
			logs.add(new TurnDTO(turnCount));
		}
		
		logs.get(logs.size()-1).logEvent(e);
	}
	
	public void logResourceChange(int goldChange, int landChange, int popChange, int turnCount) {
		if (logs == null) {
			logs = new ArrayList<TurnDTO>();
		}
		
		if (logs.isEmpty()) {
			logs.add(new TurnDTO(turnCount));
		}
		
		logs.get(logs.size()-1).logResourceChange(goldChange, landChange, popChange);
	}

	public void addTurn(int turnCount, int gold, int land, int pop) {
		logs.add(new TurnDTO(turnCount, gold, land, pop));
	}
}
