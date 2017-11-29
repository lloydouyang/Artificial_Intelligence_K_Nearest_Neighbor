import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;


public class Recipe_KNN {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		crossval();
		
       
        
	}
	public static void crossval() throws IOException{
		// Read in
        ArrayList<String> ingredients= new ArrayList<String>();
        BufferedReader reader1 = new BufferedReader(new FileReader("ingredients.txt"));
        String line = null;
        while ((line = reader1.readLine()) != null) {
        	ingredients.add(line.substring(1,line.length()-1));
        }
        reader1.close();
        
        ArrayList<Receipe> training= new ArrayList<Receipe>();
        BufferedReader reader2 = new BufferedReader(new FileReader("training.csv"));
        while ((line = reader2.readLine()) != null) {
        	int len=line.length();
        	int counter=0;
        	int prev=0;
        	String recipe="";
        	HashSet<String> hset=new HashSet<String>();
        	for (int i=0; i<2;i++){
                if (line.charAt(i)=='"'){
             	   counter++;
             	   if (counter % 2==1){
             		   prev=i;   
             	   }
             	   else{
             		   recipe= (line.substring(prev+1,i)); 
             		   
             	   }
                }
             }
        	for (int i=2; i<len;i++){
               if (line.charAt(i)=='"'){
            	   counter++;
            	   if (counter % 2==1){
            		   prev=i;   
            	   }
            	   else{
            		   hset.add(line.substring(prev+1,i)); 
            		   
            	   }
               }
            }
        		Receipe recep = new Receipe(hset,recipe);
           training.add(recep);
        }
        reader2.close();
       for (int i = 0; i < 10; i++) {
    	   		System.out.println(ingredients.get(i));
       }
		 // Separate the training into 6 parts and then repeat KNN 6 times with each being test data
        int index[]= {0, 299, 598, 897, 1196, 1495,1794};
        int kk=10;
        // 6 fold cross validation
        for (int i=0; i<6;i++){
 
        	    for (int h=index[i];h<index[i+1];h++){
        	    		// loop through training data to computer K nearest distance

        	    		PriorityQueue<Tuple> q = new PriorityQueue<Tuple>(10,new TupleComparator());
        	    		q.clear();
        	    		for (int j=0;j<index[i];j++){
        	    		    double dis=0;
        	    		    int zai=0;
        	    		    int buzai=0;
        	    		    for (String s : training.get(h).getingre()) {
        	    		    		if (training.get(j).getingre().contains(s)) {zai++;} else buzai++;
        	    		    }
        	    		    dis=Math.sqrt(buzai+training.get(j).getingre().size()-zai);
        	    			Tuple t=new Tuple(dis,training.get(j).getCuisine());
        	    			if (q.size()==kk){
        	    				if (dis<q.peek().getDistance()){
        	    					q.poll();
        	    					q.add(t);
        	    				}
        	    			else{	
        	    				q.add(t);
        	    			}
        	    		}
        	    		q.clear();
	        		for (int k=index[i+1];k<1794;k++){
	        		dis=0;
    	    		    zai=0;
    	    		     buzai=0;
    	    		    for (String s : training.get(h).getingre()) {
    	    		    		if (training.get(j).getingre().contains(s)) {zai++;} else buzai++;
    	    		    }
    	    		    dis=Math.sqrt(buzai+training.get(j).getingre().size()-zai);
    	    			t=new Tuple(dis,training.get(j).getCuisine());
    	    			if (q.size()==kk){
    	    				if (dis<q.peek().getDistance()){
    	    					q.poll();
    	    					q.add(t);
    	    				}
    	    			else{	
    	    				q.add(t);
    	    			}
	        		
	        	    }
        	    }
        }

	}
	
}

