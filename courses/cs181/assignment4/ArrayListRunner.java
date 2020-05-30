/* William Ray Johnson
 * 4/10/15
 *Q-2
 */
 
 import java.util.ArrayList;
 
 public class ArrayListRunner{
	 public static void main(String[] args){
		 ArrayList<String> names = new ArrayList<String>();
		 System.out.println(names);
		 
		 //part a
		 names.add("Alice");
		 names.add("Bob");
		 names.add("Connie");
		 names.add("David");
		 names.add("Edward");
		 names.add("Fran");
		 names.add("Gomez");
		 names.add("Harry");
		 
		 System.out.println(names + " 2a");
		 
		 //part b
		 System.out.println(names.get(0) + " " + names.get(names.size()-1) + " 2b");
		 
		 //part c
		 System.out.println(names.size() + " 2c");
		 
		 //part d
		 System.out.println(names.get(names.size()-1) + " 2d");
		 
		 //part e
		 names.set(0,"Alice B. Toklas");
		 System.out.println(names + " 2e");
		 
		 //part f
		 names.add(4,"Doug");
		 System.out.println(names + " 2f");
		 
		 //part g
		 for(String element: names){
			 System.out.print(element + " ");
		 }
		 System.out.println("2g");
		 
		 //part h
		 ArrayList<String> names2 = new ArrayList<String>(names);
		 System.out.println(names + " 2h");
		 
		 //part i
		 names.remove(0);
		 System.out.println(names + " 2i");
		 System.out.println(names2 + " 2i");
	 }
 }
 