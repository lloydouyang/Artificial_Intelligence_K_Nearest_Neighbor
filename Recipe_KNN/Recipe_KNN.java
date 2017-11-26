import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Recipe_KNN {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        System.out.println("hello Yujian!");
        ArrayList<String> ingredients= new ArrayList<String>();
        BufferedReader reader1 = new BufferedReader(new FileReader("ingredients.txt"));
        String line = null;
        while ((line = reader1.readLine()) != null) {
        	ingredients.add(line.substring(1,line.length()-1));
        }
        reader1.close();
        BufferedReader reader2 = new BufferedReader(new FileReader("training.csv"));
        while ((line = reader2.readLine()) != null) {
            
        }
        reader2.close();
	}

}
