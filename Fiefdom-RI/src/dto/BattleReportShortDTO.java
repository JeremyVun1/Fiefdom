package dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BattleReportShortDTO implements Serializable {
	private int id;	
	private Date battleDate;
	
	private int attId;
	private int defId;
	private String attName;
	private String defName;
	
	private boolean attWin;
	
	public BattleReportShortDTO(int bId, Date battleDate, int aId, String aName, int dId, String dName, boolean attWin)
	{
		this.id = bId;
		this.battleDate = battleDate;
		this.attId = aId;
		this.defId = dId;
		this.attName = aName;
		this.defName = dName;
		
		this.attWin = attWin;
	}
	
	public String getLabelString() {
		String result = "Report #" + id;
		result += " [" + new SimpleDateFormat("dd/MM/yyyy").format(battleDate) + "]";
		result += ": (#" + attId + ")" + attName + " Attacks " + "(#" + defId + ")" + defName;
		
		if (attWin)
			result += " <ATTACKER WIN>";
		else result += " <DEFENDER WIN>";
		
		return result;
	}

	public int getId() {
		return id;
	}

	public Date getBattleDate() {
		return battleDate;
	}

	public int getAttId() {
		return attId;
	}

	public int getDefId() {
		return defId;
	}

	public boolean isAttWin() {
		return attWin;
	}

	public String getAttName() {
		return attName;
	}

	public String getDefName() {
		return defName;
	}
}
