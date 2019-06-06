package stateless;

import dto.CreateThreadDTO;
import dto.FiefdomShortDTO;
import dto.MessageDTO;
import dto.RelationDTO;
import dto.ThreadDTO;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Remote;

@Remote
public interface DiplomacyBeanRemote {
	ArrayList<RelationDTO> getRelations();
	
	ArrayList<ThreadDTO> getThreads();

	HashMap<String, String> getMessageTypes();
	
	boolean isAllyWith(int toFId);
	
	boolean isAtWarWith(int toFId);

	ThreadDTO getThread(int tId);
	
	ArrayList<MessageDTO> getThreadMessages(int tId);

	boolean startThread(CreateThreadDTO sendDTO);
	boolean sendMessage(ThreadDTO thread, String body);
	
	boolean accept(ThreadDTO thread);
	boolean decline (ThreadDTO thread);
	
	boolean deleteThread(ThreadDTO thread);

	ArrayList<FiefdomShortDTO> getWarTargets();
}
