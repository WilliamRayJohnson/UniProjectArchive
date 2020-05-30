/* William Ray Johnson
 * 4/10/15
 *Q-1.2
 */
 
 public class TriangleRow{
	 public static void main(String[] args){
		 printUpTriangle(13,"*");
	 }
	 
	 static public String makeRow(int n, String s){
		 //empty string to start with
		 String returnString = "";
		 
		 for(int i=0; i<n; i++){
			 //add s to returnString n number of times
			 returnString = returnString.concat(s);
		 }
		 
		 return returnString;
	 }
	 
	 static public void printUpTriangle(int n, String s){
		 String loopString;
		 
		 for(int i=1; i<=n; i++){
			 //print spaces before * so triangle is formatted correctly 
			 for(int j=0; j<(n-i); j++){
				 System.out.print(" ");
			 }
			 loopString = makeRow(i,s);
			 System.out.printf("%s \n",loopString);
		 }
	 }
 }