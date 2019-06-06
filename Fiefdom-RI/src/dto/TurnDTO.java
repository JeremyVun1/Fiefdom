package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class TurnDTO implements Serializable {
	
	private int turn;
	private int gold;
	private int land;
	private int peasants;
	private ArrayList<GameEventDTO> events;
	
	public TurnDTO() {
		events = new ArrayList<GameEventDTO>();
	}

	public TurnDTO(int turn, int gold, int land, int peasants, ArrayList<GameEventDTO> events) {
		this.turn = turn;
		this.gold = gold;
		this.land = land;
		this.peasants = peasants;
		this.events = events;
	}

	public TurnDTO(int turn) {
		this.turn = turn;
		
		events = new ArrayList<GameEventDTO>();
	}

	public TurnDTO(int turnCount, int gold, int land, int pop) {
		this.turn = turnCount;
		this.gold = gold;
		this.land = land;
		this.peasants = pop;
		
		events = new ArrayList<GameEventDTO>();
	}
	
	public void logResourceChange(int goldChange, int landChange, int popChange) {
		gold += goldChange;
		land += landChange;
		peasants += popChange;
	}

	public int getTurn() {
		return turn;
	}

	public int getGold() {
		return gold;
	}
	
	public String getGoldStr() {
		if (gold > 0)
			return ("+" + gold);
		else if (gold < 0)
			return ("" + gold);
		else return "0";
	}

	public int getLand() {
		return land;
	}
	
	public String getLandStr() {
		if (land > 0)
			return ("+" + land);
		else if (land < 0)
			return ("" + land);
		else return "0";
	}

	public int getPeasants() {
		return peasants;
	}
	
	public String getPeasantsStr() {
		if (peasants > 0)
			return ("+" + peasants);
		else if (peasants < 0)
			return ("" + peasants);
		else return "0";
	}

	public ArrayList<GameEventDTO> getEvents() {
		System.out.println("getting events in the turn dto - size: " + events.size());
		return events;
	}

	void logEvent(String s) {
		events.add(new GameEventDTO(s));
	}
	
}
