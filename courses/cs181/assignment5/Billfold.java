/* William Ray Johnson
 * 4/29/15
 *Q-1
 */
 
 public class Billfold{
	 private Card card1;
	 private Card card2;
	 public void addCard(Card card){
		 if (card1 == null){
			 card1 = card;
		 }
		 else if (card2 == null){
			 card2 = card;
		 }	 
	 }
	 public String formatCards() {
		 String formatString = "[ ";
		 if (card1 != null) formatString += card1.format();
		 if (card1 != null && card2 != null) formatString += " | ";
		 if (card2 != null) formatString += card2.format();
		 formatString += " ]";
		 return formatString;
	 }
 }