/* William Ray Johnson
 * 4/10/15
 *Q-1.1
 */
 
 public class RowTest{
	 public static void main(String[] args){
		 System.out.print(makeRow(5,"*"));
		 System.out.print(makeRow(5,"="));
		 System.out.print(makeRow(5,"*"));
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
	 
	 
 }