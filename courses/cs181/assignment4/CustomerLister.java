/* William Ray Johnson
 * 4/10/15
 *Q-4.1
 */
 
 import java.util.Scanner;
 
 public class CustomerLister{
	 public static void main(String[] args){
		 int numOfCust = 5;
		 String[] customerName = new String[numOfCust];
		 double[] customerBalance = new double[numOfCust];
		 
		 Scanner keyboard = new Scanner(System.in);
		 
		 //Cannot use initializer list after array has been instantiated
		 customerName[0] = "Cathy";
		 customerName[1] = "Ben";
		 customerName[2] = "Jorge";
		 customerName[3] = "Wanda";
		 customerName[4] = "Freddie";
		 
		 for(int i=0; i<numOfCust; i++){
			 System.out.print("What is " + customerName[i] + "'s balance?: ");
			 customerBalance[i] = keyboard.nextDouble();
		 }
		 
		 for(int i=0; i<numOfCust; i++){
			 System.out.println(customerName[i] + "'s balance is " + customerBalance[i]);
		 }
		 
	 }
 }
 