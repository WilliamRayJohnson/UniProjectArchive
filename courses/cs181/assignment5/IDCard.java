/* William Ray Johnson
 * 4/29/15
 *Q-1
 */
 
 public class IDCard extends Card{
	 private String idNum;
	 public IDCard(String n, String id){
		 super(n);
		 idNum = id;
	 }
	 public String format(){
		 return super.format() + " ID Number: " + idNum;
	 }
 }