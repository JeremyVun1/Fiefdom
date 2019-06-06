package DAFacades;

import entity.Gamestate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GamestateFacade {
	
	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	//use id 0 for now because we are only running one game
	private final int gameId = 0;

	public void setTurn(int turnCount) {
		Gamestate g = em.find(Gamestate.class, gameId);
		g.setTurncount(turnCount);
		em.merge(g);
	}
	
	public int getTurn() {
		Gamestate g = em.find(Gamestate.class, gameId);
		return g.getTurncount();
	}
}
