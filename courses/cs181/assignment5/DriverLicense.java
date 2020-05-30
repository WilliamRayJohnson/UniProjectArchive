/* William Ray Johnson
 * 4/29/15
 *Q-1
 */
 import java.util.Calendar;
 import java.util.GregorianCalendar;
 
 public class DriverLicense extends Card{
	 private int expYear;
	 public DriverLicense(String n, int year){
		 super(n);
		 expYear = year;
	 }
	 public String format(){
		 return super.format() + " Expiration year: " + expYear;
	 }
	 public boolean isExpired(){
		 GregorianCalendar calendar = new GregorianCalendar();
		 if (expYear < calendar.get(Calendar.YEAR)) return true;
		 else return false;
	 }
		 
 }