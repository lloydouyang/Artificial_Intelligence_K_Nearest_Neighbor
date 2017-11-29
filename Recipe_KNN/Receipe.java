import java.util.HashSet;


public class Receipe {
	public final String cuisine;
	public final HashSet<String> ingre;
	public  Receipe(HashSet<String> ingre, String cuisine){
		this.ingre = ingre;
		this.cuisine = cuisine;
	}
	public HashSet<String> getingre() {
		return ingre;
	}
	public String getCuisine() {
		return cuisine;
	}
}
