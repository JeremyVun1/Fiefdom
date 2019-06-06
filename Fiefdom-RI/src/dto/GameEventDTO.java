package dto;

import java.io.Serializable;

public class GameEventDTO implements Serializable {
	
	private String eventString;

	public GameEventDTO(String eventString) {
		this.eventString = eventString;
	}

	public String getEventString() {
		return eventString;
	}
	
}
