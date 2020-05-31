--William Ray Johnson
--November 18, 2014
WITH Ada.Text_IO;
USE Ada.Text_IO;
WITH Ada.Float_Text_IO;
USE Ada.Float_Text_IO;
WITH Ada.Integer_Text_IO;
USE Ada.Integer_Text_IO;

PROCEDURE New_GPA_Calc IS

   --Class Name String
   Max_Class_Length: CONSTANT Integer := 32;
   SUBTYPE Class_Name IS String(1 .. Max_Class_Length);

   --Class name record for string and length
   TYPE Class_Type IS RECORD
      Name: Class_Name;
      Name_Length: Integer;
   END RECORD;

   --Record for Course information
   TYPE Course_Type IS RECORD
      Course: Class_Type;
      Hours: Integer;
      Grade: Character;
   END RECORD;

   --Array to store classes
   Max_Classes: Integer := 9;
   TYPE Class_Array IS ARRAY(1 .. Max_Classes) OF Course_Type;

   --Array for scalar for GPA calculation
   SUBTYPE Grades IS Character RANGE 'A' .. 'F';
   TYPE Grade_Array IS ARRAY(Grades) OF Float;
   Grade_Scale: Grade_Array := (4.0,3.0,2.0,1.0,0.0,0.0);

   Function Calc_GPA(Courses: IN Class_Array; Num_Of_Class: in Integer) return Float IS
      --Will calculate GPA based on inputed array of records

      Total_Quality: Float := 0.0;
      Total_Hours: Integer := 0;

   BEGIN
      --Calculate total Quality points
	  FOR i IN 1 .. Num_Of_Class LOOP
		Total_Quality := Total_Quality + (Float(Courses(i).Hours) * Grade_Scale(Courses(i).Grade));
	  End Loop;

	  --Calculate total Hours
	  For i In 1 .. Num_Of_Class Loop
		Total_Hours := Total_Hours + Courses(i).Hours;
	  End loop;

	  return Total_Quality/Float(Total_Hours);

	End Calc_GPA;

   --Variables
   Number_Of_Classes: Integer;
   The_Courses: Class_Array;
   GPA: Float;

BEGIN
      Put("Welcome to the Program! I will calculate your GPA!");
      New_Line;

	Put("How many classes are you taking? (Max 9): ");
	Get(Number_Of_Classes);
	Skip_Line;

	--Ask for name of each class
	Put("What is the name of");
	New_Line;
	For i in 1 .. Number_Of_Classes Loop
		Put("Class ");
        Put(i,1);
        Put(": ");
		Get_Line(Item => The_Courses(i).Course.Name, Last => The_Courses(i).Course.Name_Length);
		New_Line;
	End Loop;

	--Ask for Number of hours in each class
	Put("How many hours do you have in");
	New_Line;
	For i in 1 .. Number_Of_Classes Loop
		Put(Item => The_Courses(i).Course.Name(1 .. The_Courses(i).Course.Name_Length) & ": ");
		Get(The_Courses(i).Hours);
		New_Line;
	End Loop;

	--Ask for Grade in each class
	Put("What is your grade in");
	New_Line;
	For i in 1 .. Number_Of_Classes Loop
		Put(Item => The_Courses(i).Course.Name(1 .. The_Courses(i).Course.Name_Length) & ": ");
		Get(The_Courses(i).Grade);
		New_Line;
	End Loop;

	GPA := Calc_GPA(The_Courses, Number_Of_Classes);

	Put("Your GPA is ");
	Put(Item => GPA, Fore => 1, Aft  => 3, Exp  => 0);
      Put(".");
      New_Line;
	Put("Wow! That's Amazing!");

END New_GPA_Calc;


