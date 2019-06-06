package dto;

import java.io.Serializable;

public class CreateThreadDTO implements Serializable {
	private String subject;
	private int fromId;
	private int toId;
	private String type;
	private String body;

	public CreateThreadDTO(String subject, String body, int fromId , int toId, String type) {
		this.subject = subject;
		this.fromId = fromId;
		this.toId = toId;
		this.type = type;
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public int getFromId() {
		return fromId;
	}

	public int getToId() {
		return toId;
	}
	
	public String getType() {
		return type;
	}
	
	public String getBody() {
		return body;
	}
}
