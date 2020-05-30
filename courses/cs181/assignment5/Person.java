/* William Ray Johnson
 * 4/29/15
 *Q-3
 */

 public class Person{
	 static int count;
	 private String name;
	 public Person(String name){
		 this.name = name;
		 count++;
	 }
	 public void setName(String name){
		 this.name = name;
	 }
	 public String getName(){
		 return name;
	 }
	 public void speak(){
		 System.out.println("My name is " + name);
	 }
	 public static void printCount(){
		 System.out.println("There are " + count + " persons.");
	 }
 }