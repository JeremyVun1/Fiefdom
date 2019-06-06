package dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageDTO implements Serializable {
	private int mid;
	private int tid;
	private String message;
	private int authorId;
	private String authorName;
	private Date posted;

	public MessageDTO(int mid, int tid, Date posted, String message, int author, String authorName) {
		this.mid = mid;
		this.tid = tid;
		this.posted = posted;
		this.message = message;
		this.authorId = author;
		this.authorName = authorName;
	}

	public int getMid() {
		return mid;
	}

	public int getTid() {
		return tid;
	}

	public String getMessage() {
		return message;
	}

	public int getAuthorId() {
		return authorId;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public String getPosted() {
		return new SimpleDateFormat("dd-MM-yyyy '[' h:mm z ']'").format(posted);
	}
}
