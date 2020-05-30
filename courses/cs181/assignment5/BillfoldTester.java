/* William Ray Johnson
 * 4/29/15
 *Q-1
 */

 public class BillfoldTester{
	 public static void main(String[] args){
		IDCard truman = new IDCard("Will", "wrj6844");
		DriverLicense missouri = new DriverLicense("Will", 2020);
		Billfold myWallet = new Billfold();
		
		myWallet.addCard(truman);
		myWallet.addCard(missouri);
		
		System.out.print(myWallet.formatCards());
		
		System.out.print("\n" + missouri.isExpired());
	 }
 }