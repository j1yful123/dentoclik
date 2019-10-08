package executionEngine;

/* 
 * Enter your code here. Read input from command line arguments. Print the output in the format:  output< SPACE >< OUTPUT VALUE >. 
 * Your class should be named CandidateCode.
*/

import java.io.*;
import java.util.*;

public class Test {
	
	//public static double[] sum = new double[3];
	
    public static void main(String args[] ) throws Exception {

    	
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Enter number of Integers");
    	int N = input.nextInt();
    	
        double[] numbers = new double[N];
        double[] sum = new double[N-1];
        long cost1 = 0;
        long finalcost = 0;
        
        for (int i = 0; i < numbers.length; i++)
        {
            System.out.println("Please enter number");
            numbers[i] = input.nextDouble();
        }

        System.out.println("Input:-" + Arrays.toString(numbers));
        for (int j = 0; j < numbers.length; j++)
        {
        for (int i = 0; i < numbers.length-1; i++)
        {
        	sum[i] = numbers[i] + numbers[i+1];  
        	
        }
        
        for( double k : sum) {
            cost1 += k;
           
        }
        
        if(cost1>=finalcost)
        {
        	cost1 =  finalcost;
        }
        cost1 = 0;
     
        sendBack(numbers,j);
        }
        System.out.println("Output:-" + Arrays.toString(sum));
   }
    public static void sendBack(double[] array, int idx) {
        double value = array[idx];
        System.arraycopy(array, idx+1, array, idx, array.length-idx-1);
        array[array.length-1] = value;
    }
}
