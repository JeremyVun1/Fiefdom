package dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadDTO implements Serializable {
	private int threadId;
	private String subject;
	private int fromId;
	private int toId;
	private String type;
	
	private Date posted;
	
	private String fromName;
	private String toName;
	
	private boolean responded;

	public ThreadDTO(int threadId, Date posted, String subject, int fromId, String fromName, int toId, String toName, String type, boolean responded) {
		this.threadId = threadId;
		this.posted = posted;
		this.subject = subject;
		this.fromId = fromId;
		this.toId = toId;
		this.type = type;
		
		this.fromName = fromName;
		this.toName = toName;
		
		this.responded = responded;
	}

	public int getThreadId() {
		return threadId;
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
	
	public String getTypeFullStr() {
		if (type.equals("DW"))
			return "Declare War";
		if (type.equals("RA"))
			return "Request Alliance";
		if (type.equals("BA"))
			return "Break Alliance";
		if (type.equals("RP"))
			return "Request Peace";
		return "Diplomatic Message";
	}
	
	public String getLink() {
		return ("/Fiefdom-war/user/message.xhtml?id=" + threadId);
	}
	
	public boolean isResponded() {
		return responded;
	}

	public String getFromName() {
		return fromName;
	}

	public String getToName() {
		return toName;
	}

	public String getPosted() {
		return new SimpleDateFormat("dd-MM-yyyy").format(posted);
	}
}
