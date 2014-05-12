// Ta sxolia tou programmatos tha graftoun sta agglika gia apofugi thematwn sumbatotitas me alla PC
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*; //Eclipse Warning suggests that java.lang is never imported

public class StockSpan
    {
	public static void main(String args[])
     { 
	  ArrayList<String> dates=new ArrayList<String>();
	  ArrayList<Float> prices=new ArrayList<Float>();		// Creates objects for date and price arrays 
      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD"); //Object to format the date as requested by the assignment
      String currentLine;
      try
          {
    	  BufferedReader br=new BufferedReader(new FileReader (args[1])); //Reads and parses the file.
           while ((currentLine=br.readLine())!=null)
               {
               String[] fields=currentLine.split(",");
               Date date=sdf.parse(fields[0]);
               String d=sdf.format(date);
               dates.add(d);
               Float p=Float.parseFloat(fields[1]);
               prices.add(p);         
               }
           }
      catch (IOException e)    //Catches IOexceptions like FileNotFound etc. and prints informative message
	       {e.printStackTrace();}
      catch(Exception ex)      // Catches and prints other exceptions (non IO)
           {System.out.println(ex);}
    
 //The following 5 lines add the previously parsed file's dates and prices into respective arrays.
      String[] datesArray=new String[dates.size()];
      Float[] pricesArray=new Float[prices.size()];
     for (int i=0;i<datesArray.length;i++){
    	 datesArray[i]=dates.get(i);
         pricesArray[i]=prices.get(i);
        
     }// End of file reading and array creation
     
     
    //The naive implementation algorithm   
     if(args[0].equals("-n")){
    	 for(int i=0;i<=pricesArray.length-1;i++){
    		 int n=1;
    		 boolean span_end=false;
    		 while(i-n>=0 && !span_end){
    			 if(pricesArray[i-n]<=pricesArray[i])
    				 n=n+1;
    			 else
    				 span_end=true;
    		 }
    		 System.out.println(datesArray[i]+","+n); //Printing results with YYYY-MM-DD,n format as requested
    	 }
     }
     
     
     //The stack implementation algorithm   
     else if(args[0].equals("-s")){
    	 Integer[] span=new Integer[pricesArray.length];
    	 Stack<Integer> stackImpl=new Stack<Integer>();
    	 stackImpl.push(0);
    	 span[0]=1;
    	 for(int i=1;i<=pricesArray.length-1;i++){
    		 while(!stackImpl.empty() && pricesArray[stackImpl.peek()]<=pricesArray[i])
    			 stackImpl.pop();
    		 if(stackImpl.empty())
    			 span[i]=i+1; 
    		 else
    			 span[i]=i-stackImpl.peek();
    		 stackImpl.push(i);
    	 }
    	 for(int i=0;i<=pricesArray.length-1;i++){
    		 System.out.println(datesArray[i]+","+span[i]);
    	 }	 
     }
     
     
     //The 100 from each type implementation and execution time in milliseconds
     
     else if(args[0].equals("-b")){
    	 long tStart = System.currentTimeMillis(); //Used to check the naive implementation starting time
    	 for(int counter=0;counter<=99;counter++){//Naive impl. for the first 100 
    		 for(int i=0;i<=pricesArray.length-1;i++){
    			 int n=1;
    			 boolean span_end=false;
    			 while(i-n>=0 && !span_end){
    				 if(pricesArray[i-n]<=pricesArray[i])
    					 n=n+1;
    				 else
    					 span_end=true;
    			 }
    		 }
    	 }
    	 long tEnd = System.currentTimeMillis();//Used to check the naive implementation ending time
    	 long tElapsed = tEnd - tStart;//Calculates time elapsed for the execution
    	 System.out.println("Naive implementation took:"+tElapsed+"millis");
    	 
    	 
    	 tStart = System.currentTimeMillis();//Used to check the stack implementation starting time
    	 for(int counter=0;counter<=99;counter++){//Stack impl. for the first 100
    		 Integer[] span=new Integer[pricesArray.length];
    		 Stack<Integer> stackImpl=new Stack<Integer>();
    		 stackImpl.push(0);
    		 span[0]=1;
    		 for(int i=1;i<=pricesArray.length-1;i++){
    			 while(!stackImpl.empty() && pricesArray[stackImpl.peek()]<=pricesArray[i])
    				 stackImpl.pop();
    			 if(stackImpl.empty())
    				 span[i]=i+1;
    			 else
    				 span[i]=i-stackImpl.peek();
    			 stackImpl.push(i);
    		 }
    	 }
    	 tEnd = System.currentTimeMillis();//Used to check the stack implementation ending time
    	 tElapsed = tEnd - tStart;//Calculates time elapsed for the execution
    	 System.out.println(" Stack implementation took:"+tElapsed+"millis");
     }
  }
}