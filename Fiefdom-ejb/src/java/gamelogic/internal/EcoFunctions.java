package gamelogic.internal;

import entity.Buildings;
import entity.Fiefdom;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EcoFunctions implements EcoFunctionsLocal {
	
	@EJB
	GameConstants constants;

	@Override
	public int GenerateGold(Fiefdom f) {
		int result = 0;
		
		Buildings b = f.getBid();
		result += b.getMines() * f.getPeasants() * constants.GOLD_GENERATION_MULTI;
		
		return result;
	}

	@Override
	public int GeneratePeasants(Fiefdom f) {
		int farmsPerPeasant = f.getBid().getFarms() / f.getPeasants();
		
		return Math.max((int)(f.getPeasants() * farmsPerPeasant * constants.POP_GROWTH_RATE), 1);
	}
}
