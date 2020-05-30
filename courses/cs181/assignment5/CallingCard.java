/* William Ray Johnson
 * 4/29/15
 *Q-1
 */
 
 public class CallingCard extends Card{
	 private String cardNum;
	 private String PIN;
	 public CallingCard(String n, String carNu, String pin){
		 super(n);
		 cardNum = carNu;
		 PIN = pin;
	 }
	 public String format(){
		 return super.format() + " Card Number: " + cardNum + " PIN: " + PIN;
	 }
 }