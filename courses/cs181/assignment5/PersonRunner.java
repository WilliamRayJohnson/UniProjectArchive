/* William Ray Johnson
 * 4/29/15
 *Q-3
 */
 
 public class PersonRunner{
	public static void main(String[] args){
		Person p1 = new Person("Claire");
		p1.speak();
		Person p2 = new Person("Beth");
		p2.speak();
		Person p3 = new Person("Frank");
		p3.speak();
		Person p4 = new Person("Jeff");
		p4.speak();
		Person.printCount();
	}
 }