package web;

import static java.lang.Thread.sleep;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import stateless.ServerStateBeanRemote;

@Named(value = "serverStatMBean")
@ViewScoped
public class ServerStatMBean {
	
	@EJB
	ServerStateBeanRemote serverStat;
	
	@Inject
	SessionMBean sessBean;

	public int getOnlineCount() {
		return serverStat.getOnlineCount();
	}

	public int getTurnCount() {
		return serverStat.getTurnCount();
	}
	
	public String serverStatus() {
		long start, finish;
		String result = "";
		
		try {
			start = System.currentTimeMillis();
			serverStat.ping();
			finish = System.currentTimeMillis();			
			
			result += "ONLINE [ping: " + Long.toString(finish-start) + "ms]";			
		} catch(Exception ex) {
			result += "OFFLINE";
		}
		
		return result;
	}
	
	public String getSystemTime() {
		Date d = serverStat.getSystemTime();
		return d.toString();
	}	
}
