package gamelogic.internal;

import entity.Fiefdom;
import javax.ejb.Local;

@Local
public interface EcoFunctionsLocal {

	public int GenerateGold(Fiefdom f);

	public int GeneratePeasants(Fiefdom f);
	
}
