package dto;

import java.io.Serializable;

public class EmailDTO implements Serializable {
	private String to;
	private String subject;
	private String body;

	public EmailDTO(String to, String subject, String body) {
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}
}
