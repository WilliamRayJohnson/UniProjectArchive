/* William Ray Johnson
 * 4/10/15
 *Q-3
 */
 
 import java.util.Arrays;
 
 public class ArraysTest{
	 public static void main(String[] args){
		 double[] x = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		 System.out.println("Before rotation: ==========================:");
		 for(int i=0; i<x.length; i++){
			 System.out.println("x[" + i + "]: " + x[i]);
		 }
		 x = rotate(x, 9);
		 System.out.println("After rotation: =============================:");
		 for(int i=0; i<x.length; i++){
			 System.out.println("x[" + i + "]: " + x[i]);
		 }
	 }
	 
	 static double[] rotate(double[] x, int n){
		 double[] y = Arrays.copyOf(x,x.length);
		 
		 //use modulo to account for shifts >= array length
		int newN = n % x.length;
		
		 //Shift elements from n to the end of array
		 for(int i=(x.length-1); i>=newN; i--){
			 y[i-newN] = x[i];
		 }
		 
		 //Shift elements from 0 to n
		 for(int i=0; i<newN; i++){
			 //These elements are shifted off the array, so they actually move backwards n+1
			 y[i+x.length-newN] = x[i];
		 }
		 
		 return y;
		 
	 }
 }