
 class EmpDemo {

 public static void main(String[] args) { //Main method is the entry point of your program
  
  int i; //Loop control variable
  int noOfEmployees = 5; //Variable that tells the number of employees in your record
  
  Employee empToSearch = new Employee();  //empToSearch is the employee object that we want to search in
            //the array empArray
  empToSearch.setEmpId(3); //set the empID field of empToSearch object to 3, passing a parameter in setEmpID method
  
  
  Employee[] empArray = new Employee[noOfEmployees]; //empArray is an array (collection) of employee objects.
                       //In other way we cam make 5 different objets also like    Employee e1= new Employee();
  
  for(i=0;i<empArray.length;i++){  //Loop that runs till the length of empArray 0 to 4
   empArray[i] = new Employee(); //create five Employee objects
  }
  
  intializeEmpArray(empArray); //method call to intialize empArray. This method is defined in this class.
                               // we can define this method in a different class also to class simple.
  
  empToSearch = searchEmployee(empToSearch, empArray); //method call to search if empToSearch object is present 
                //in empArray or not 
  
  if(null != empToSearch){ //printing the details of empToSearch if this employee 
         //is present in the array of employees 
   
   System.out.println("\nEmployee ID: "+ empToSearch.getEmpId());
   System.out.println("\nEmployee Name: "+ empToSearch.getEmpName());
   System.out.println("\nEmployee Email: "+ empToSearch.getEmail());
   System.out.println("\nEmployee Salary: "+ empToSearch.getSalary());
  
  }else{ //otherwise print that employee doesnot exist
   System.out.println("Employee with ID: "+ empToSearch.getEmpId() +" doesnot exist");
  }
  
 }
 
 //Setting the values of objects' state
 private static void intializeEmpArray(Employee[] empArray){
  
  empArray[0].setEmpId(1);
  empArray[0].setEmpName("Gaur");
  empArray[0].setEmail("gaur@ks.com");
  empArray[0].setSalary(35000);
  
  empArray[1].setEmpId(2);
  empArray[1].setEmpName("Sharan");
  empArray[1].setEmail("sharan@ks.com");
  empArray[1].setSalary(45000);
  
  empArray[2].setEmpId(3);
  empArray[2].setEmpName("AC");
  empArray[2].setEmail("ac@ks.com");
  empArray[2].setSalary(20000);
  
  empArray[3].setEmpId(4);
  empArray[3].setEmpName("Dennis");
  empArray[3].setEmail("dennis@ks.com");
  empArray[3].setSalary(50000);
  
  empArray[3].setEmpId(5);
  empArray[3].setEmpName("Anna");
  empArray[3].setEmail("anna@ks.com");
  empArray[3].setSalary(35000);
 }
 
 /**
  * @author AC
  * @param emp
  * @param empArray
  * @return Employee
  * This method search for a given employee in the employee array
  */
 private static Employee searchEmployee(Employee emp, Employee[] empArray){
  
  int i;
  Employee empToSearch = null;
  
  for(i=0; i<empArray.length; i++){
   if(emp.getEmpId() == empArray[i].getEmpId()){
    empToSearch = empArray[i];
    return empToSearch;
   }
  }
  
  return empToSearch;
  
 }

}








