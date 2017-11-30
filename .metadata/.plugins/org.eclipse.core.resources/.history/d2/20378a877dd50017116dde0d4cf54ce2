import java.util.Comparator;

public class TupleComparator implements Comparator<Tuple>{
	@Override
	public int compare(Tuple x, Tuple y) {
		if (x.getDistance() > y.getDistance()) {
			return -1;
		} else if (x.getDistance() < y.getDistance()) {
			return 1;
		}
		return 0;
	}
}
