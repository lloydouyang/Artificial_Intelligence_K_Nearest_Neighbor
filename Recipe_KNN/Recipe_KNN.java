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
		// cuisines
		String[] cs={"brazilian","british","cajun_creole","chinese","filipino","french","greek","indian","irish","italian","jamaican","japanese","korean","mexican","moroccan","russian","southern_us","spanish","thai","vietnamese"};
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
	        int br = 0;
	        	for (int i=0; i<len;i++){
	                if (line.charAt(i)=='"'){
	             	   counter++;
	             	   if (counter % 2==1){
	             		   prev=i;   
	             	   }
	             	   else{
	             		   recipe= (line.substring(prev+1,i)); 
	             		   br=i;
	             		   break;
	             	   }
	                }
	             }
	       
	        	for (int i=br+1; i<len;i++){
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
        double dis=0;
	    int zai=0;
	    int buzai=0;
	    String maxs="";
	    double maxx=0;
	    int error=0;
	    double[] errorRate={0,0,0,0,0,0};
        // 6 fold cross validation
        for (int i=0; i<6;i++){
        		error=0;
        		// loop through test data to find k nearest neighbors for each of them
        	    for (int h=index[i];h<index[i+1];h++){
        	    		// loop through training data to computer K nearest distance
        	    		PriorityQueue<Tuple> q = new PriorityQueue<Tuple>(10,new TupleComparator());
        	    		q.clear();
        	    		for (int j=0;j<index[i];j++){
        	    		    dis=0;
        	    		    zai=0;
        	    		    buzai=0;
        	    		    for (String s : training.get(h).getingre()) {
        	    		    		if (training.get(j).getingre().contains(s)) {zai++;} else {buzai++;}
        	    		    }
        	    		    dis=Math.sqrt(buzai+training.get(j).getingre().size()-zai);
        	    			Tuple t=new Tuple(dis,training.get(j).getCuisine());
        	    			if (q.size()==kk){
        	    				if (dis<q.peek().getDistance()){
        	    					q.poll();
        	    					q.add(t);
        	    				}
        	    			}
        	    			else{	
        	    				q.add(t);
        	    			}
        	    		}
        	    		
	        		for (int j=index[i+1];j<1794;j++){
		        		dis=0;
	    	    		    zai=0;
	    	    		    buzai=0;
		    	    		for (String s : training.get(h).getingre()) {
	    	    		    		if (training.get(j).getingre().contains(s)) {zai++;} else buzai++;
		    	    		}
	    	    		    dis=Math.sqrt(buzai+training.get(j).getingre().size()-zai);
	    	    			Tuple tt=new Tuple(dis,training.get(j).getCuisine());
	    	    			if (q.size()==kk){
	    	    				if (dis<q.peek().getDistance()){
	    	    					q.poll();
	    	    					q.add(tt);
	    	    				}
	    	    			}
	    	    			else{	
	    	    				q.add(tt);
	    	    			}
	        	    }
	        		maxs="";
	        		maxx=0;
	        		double f=0;
	        		for (int j=0;j<20;j++){
	        			f=0;
	        			for (Tuple element: q){
	        				if (element.getCuisine().equals(cs[j])){
	        					f=f+1.0;
	        					//(double)((double)1/(double)element.getDistance());
	        				}
	        			}
	        			if (f>maxx){
	        				maxx=f;
	        				maxs=cs[j];
	        			}
	        		}
	        		if (!maxs.equals(training.get(h).getCuisine())){
	        			error++;
	        		}	
//	        		for (Tuple element: q){
//	        		System.out.println(element.getCuisine());
//	        		}
	        		//System.out.println(maxs);
        	    }
        	    errorRate[i]=(double)(error)/((double)(299))*(double)(100);
        	    
        }
        
        double mmax=0.0;
        double mmin=200.0;
        double sum=0.0;
        double mean;
        for (int i=0;i<6;i++){
        		if (errorRate[i]>mmax) mmax=errorRate[i];
        		if (errorRate[i]<mmin) mmin=errorRate[i];
        		sum+=errorRate[i];
        }
        mean=(double)(sum)/(double)(6);
        double var=0;
        for (int i=0;i<6;i++){
    		   var+=Math.pow(errorRate[i]-mean,2);
    		}
        var=(double)(var)/(double)(6);
        System.out.println(errorRate[0]+" "+errorRate[1]+" "+errorRate[2]+" "+errorRate[3]+" "+errorRate[4]+" "+errorRate[5]);
        System.out.println("Max is: "+ mmax);
        System.out.println("Min is: "+ mmin);
        System.out.println("Generalization Error is: "+ var);
	}
	
}

