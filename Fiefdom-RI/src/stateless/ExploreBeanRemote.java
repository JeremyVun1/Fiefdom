package stateless;

import javax.ejb.Remote;

@Remote
public interface ExploreBeanRemote {
	
	boolean explore(int land);
	
	int getLandCost();
}
