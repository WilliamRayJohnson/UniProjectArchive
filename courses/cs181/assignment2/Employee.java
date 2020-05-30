class Employee {
 
 private int empId;
 private String empName;
 private String email;
 private int salary;
 
 //a Getter/Accesor method
 public String getEmail() {
  return email;
 }
 
 // a Setter/Mutator method
 public void setEmail(String email) {
  this.email = email;
 }
 public int getEmpId() {
  return empId;
 }
 public void setEmpId(int empId) {
  this.empId = empId;
 }
 public String getEmpName() {
  return empName;
 }
 public void setEmpName(String empName) {
  this.empName = empName;
 }
 public int getSalary() {
  return salary;
 }
 public void setSalary(int salary) {
  this.salary = salary;
 }
}