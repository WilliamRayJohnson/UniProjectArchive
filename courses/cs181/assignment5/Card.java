/* William Ray Johnson
 * 4/29/15
 *Q-1
 */
 
 public class Card{
	 private String name;
	 public Card(){
		 name = "";
	 }
	 public Card(String n){
		 name = n;
	 }
	 public String getName(){
		 return name;
	 }
	 public boolean isExpired(){
		 return false;
	 }
	 public String format(){
		 return "Card holder: " + name;
	 }
 }
	 