/* William Ray Johnson
 * 4/10/15
 *Q-3
 */
 
 import java.util.Arrays;
 
 public class ArraysTest{
	 public static void main(String[] args){
		 double[] x = {8,4,5,21,7,9,18,2,100};
		 System.out.println("Before rotation: ==========================:");
		 for(int i=0; i<x.length; i++){
			 System.out.println("x[" + i + "]: " + x[i]);
		 }
		 x = rotate(x, 3);
		 System.out.println("After rotation: =============================:");
		 for(int i=0; i<x.length; i++){
			 System.out.println("x[" + i + "]: " + x[i]);
		 }
	 }
	 
	 static double[] rotate(double[] x, int n){
		 double[] y = Arrays.copyOf(x,x.length);
		 
		 for(int i=(x.length-1); i>=n; i--){
			 y[i-n] = x[i];
		 }
		 
		 /*for(int i=0; i<y.length; i++){
			 System.out.println("y[" + i + "]: " + y[i]);
		 }*/
		 
		 return y;
		 
	 }
 }