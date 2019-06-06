package structs;

import java.util.Comparator;

//helper struct for sorting
public class FIdPower {
	private int fId;
	private int power;

	public FIdPower(int fId, int power) {
		this.fId = fId;
		this.power = power;
	}

	public int getfId() {
		return fId;
	}

	public int getPower() {
		return power;
	}

	public static Comparator<FIdPower> powerComparator = new Comparator<FIdPower>() {
		@Override
		public int compare(FIdPower a, FIdPower b) {
			return (b.getPower() < a.getPower() ? -1
					: (b.getPower() == a.getPower() ? 0 : 1));
		}
	};
}
