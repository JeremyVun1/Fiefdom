package dto;

import java.io.Serializable;
import java.util.Map;

public class RacesDTO implements Serializable{
	private Map<String, Integer> races;

	public RacesDTO(Map<String, Integer> races) {
		this.races = races;
	}

	public Map<String, Integer> getRaces() {
		return races;
	}
	
	public String getRaceStr(int n) {
		for (Map.Entry<String, Integer> e : races.entrySet()) {
			if (e.getValue() == n)
				return e.getKey();
		}
		
		//default if can't find race string
		return "Human";
	}
	
	public int getRaceInt(String s) {
		return races.get(s);
	}
}
