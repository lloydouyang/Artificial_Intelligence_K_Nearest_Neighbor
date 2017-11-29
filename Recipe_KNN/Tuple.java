import java.util.Comparator;


public class Tuple {
	public final double distance;
	public final String cuisine;
	public Tuple(double distance, String cuisine){
		this.distance = distance;
		this.cuisine = cuisine;
	}
	public double getDistance() {
		return distance;
	}
	public String getCuisine() {
		return cuisine;
	}

}
