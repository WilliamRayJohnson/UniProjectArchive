--Will Ray Johnson 9/5/2014
WITH Ada.Text_IO;
WITH Ada.Float_Text_IO;


PROCEDURE GPA_Calc IS

   --Define variables for class names
   Name_Class_1   : String(1 .. 30);
   Name_Class_2   : String(1 .. 30);
   Name_Class_3   : String(1 .. 30);
   Name_Class_4   : String(1 .. 30);
   Name_Class_5   : String(1 .. 30);

   --Define variables for class credit hours
   Credit_Class_1 : Float;
   Credit_Class_2 : Float;
   Credit_Class_3 : Float;
   Credit_Class_4 : Float;
   Credit_Class_5 : Float;

   --Define variables for class grade
   Grade_Class_1  : String(1 .. 1);
   Grade_Class_2  : String(1 .. 1);
   Grade_Class_3  : String(1 .. 1);
   Grade_Class_4  : String(1 .. 1);
   Grade_Class_5  : String(1 .. 1);

   --Define variables for class's scale
   Scale_Class_1  : Float;
   Scale_Class_2  : Float;
   Scale_Class_3  : Float;
   Scale_Class_4  : Float;
   Scale_Class_5  : Float;

   --Class name length
   Length_Class_1 : Integer;
   Length_Class_2 : Integer;
   Length_Class_3 : Integer;
   Length_Class_4 : Integer;
   Length_Class_5 : Integer;

   --Define variables for quality points
   Quality_Class_1 : Float;
   Quality_Class_2 : Float;
   Quality_Class_3 : Float;
   Quality_Class_4 : Float;
   Quality_Class_5 : Float;

   --Grade Point Average variables
   Total_Quality_Points : Float;
   Total_Credit_Hours   : Float;
   Grade_Point_Average  : Float;




   --Constants

   --Grade string
   Grade_A        : CONSTANT String := "A";
   Grade_B        : CONSTANT String := "B";
   Grade_C        : CONSTANT String := "C";
   Grade_D        : CONSTANT String := "D";
   Grade_F        : CONSTANT String := "F";

   --Grade scale
   Scale_A        : CONSTANT Float  := 4.0;
   Scale_B        : CONSTANT Float  := 3.0;
   Scale_C        : CONSTANT Float  := 2.0;
   Scale_D        : CONSTANT Float  := 1.0;
   Scale_F        : CONSTANT Float  := 0.0;



BEGIN
   Ada.Text_IO.Put(Item => "Welcome to the program! I will calculate your GPA!");
   Ada.Text_IO.New_Line;
   Ada.Text_IO.New_Line;

   --Ask user for 5 classes they are taking

   Ada.Text_IO.Put(Item => "What is your first class? : ");
   Ada.Text_IO.Get_Line(Item => Name_Class_1, Last => Length_Class_1);

   Ada.Text_IO.Put(Item => "What is your second class? : ");
   Ada.Text_IO.Get_Line(Item => Name_Class_2, Last => Length_Class_2);

   Ada.Text_IO.Put(Item => "What is your third class? : ");
   Ada.Text_IO.Get_Line(Item => Name_Class_3, Last => Length_Class_3);

   Ada.Text_IO.Put(Item => "What is your fourth class? : ");
   Ada.Text_IO.Get_Line(Item => Name_Class_4, Last => Length_Class_4);

   Ada.Text_IO.Put(Item => "What is your fifth class? : ");
   Ada.Text_IO.Get_Line(Item => Name_Class_5, Last => Length_Class_5);

   Ada.Text_IO.New_Line;

   --Ask user for credit hours in classes

   Ada.Text_IO.Put(Item => "How many credit hours is ");
   Ada.Text_IO.Put(Item => Name_Class_1 (1 .. Length_Class_1));
   Ada.Text_IO.Put(Item => "? : ");
   Ada.Float_Text_IO.Get(Item => Credit_Class_1);

   Ada.Text_IO.Put(Item => "How many credit hours is ");
   Ada.Text_IO.Put(Item => Name_Class_2 (1 .. Length_Class_2));
   Ada.Text_IO.Put(Item => "? : ");
   Ada.Float_Text_IO.Get(Item => Credit_Class_2);

   Ada.Text_IO.Put(Item => "How many credit hours is ");
   Ada.Text_IO.Put(Item => Name_Class_3 (1 .. Length_Class_3));
   Ada.Text_IO.Put(Item => "? : ");
   Ada.Float_Text_IO.Get(Item => Credit_Class_3);

   Ada.Text_IO.Put(Item => "How many credit hours is ");
   Ada.Text_IO.Put(Item => Name_Class_4 (1 .. Length_Class_4));
   Ada.Text_IO.Put(Item => "? : ");
   Ada.Float_Text_IO.Get(Item => Credit_Class_4);

   Ada.Text_IO.Put(Item => "How many credit hours is ");
   Ada.Text_IO.Put(Item => Name_Class_5 (1 .. Length_Class_5));
   Ada.Text_IO.Put(Item => "? : ");
   Ada.Float_Text_IO.Get(Item => Credit_Class_5);

   Ada.Text_IO.New_Line;

   --Ask user what their grade is in each class

   Ada.Text_IO.Put(Item => "What is your grade in ");
   Ada.Text_IO.Put(Item => Name_Class_1 (1 .. Length_Class_1));
   Ada.Text_IO.Put(Item => "? (Please input captial A,B,C,D, or F) : ");
   Ada.Text_IO.Get(Item => Grade_Class_1);

   Ada.Text_IO.Put(Item => "What is your grade in ");
   Ada.Text_IO.Put(Item => Name_Class_2 (1 .. Length_Class_2));
   Ada.Text_IO.Put(Item => "? (Please input captial A,B,C,D, or F) : ");
   Ada.Text_IO.Get(Item => Grade_Class_2);

   Ada.Text_IO.Put(Item => "What is your grade in ");
   Ada.Text_IO.Put(Item => Name_Class_3 (1 .. Length_Class_3));
   Ada.Text_IO.Put(Item => "? (Please input captial A,B,C,D, or F) : ");
   Ada.Text_IO.Get(Item => Grade_Class_3);

   Ada.Text_IO.Put(Item => "What is your grade in ");
   Ada.Text_IO.Put(Item => Name_Class_4 (1 .. Length_Class_4));
   Ada.Text_IO.Put(Item => "? (Please input captial A,B,C,D, or F) : ");
   Ada.Text_IO.Get(Item => Grade_Class_4);

   Ada.Text_IO.Put(Item => "What is your grade in ");
   Ada.Text_IO.Put(Item => Name_Class_5 (1 .. Length_Class_5));
   Ada.Text_IO.Put(Item => "? (Please input captial A,B,C,D, or F) : ");
   Ada.Text_IO.Get(Item => Grade_Class_5);

   --Determine the scale corresponding to the letter grade inserted by user


   --Class 1
   IF Grade_Class_1 = Grade_A THEN
      Scale_Class_1 := Scale_A;

   ELSIF Grade_Class_1 = Grade_B THEN
      Scale_Class_1 := Scale_B;

   ELSIF Grade_Class_1 = Grade_C THEN
      Scale_Class_1 := Scale_C;

   ELSIF Grade_Class_1 = Grade_D THEN
      Scale_Class_1 := Scale_D;

   ELSIF Grade_Class_1 = Grade_F THEN
      Scale_Class_1 := Scale_F;

   END IF;

   --Class 2
   IF Grade_Class_2 = Grade_A THEN
      Scale_Class_2 := Scale_A;

   ELSIF Grade_Class_2 = Grade_B THEN
      Scale_Class_2 := Scale_B;

   ELSIF Grade_Class_2 = Grade_C THEN
      Scale_Class_2 := Scale_C;

   ELSIF Grade_Class_2 = Grade_D THEN
      Scale_Class_2 := Scale_D;

   ELSIF Grade_Class_2 = Grade_F THEN
      Scale_Class_2 := Scale_F;

   END IF;

   --Class 3
   IF Grade_Class_3 = Grade_A THEN
      Scale_Class_3 := Scale_A;

   ELSIF Grade_Class_3 = Grade_B THEN
      Scale_Class_3 := Scale_B;

   ELSIF Grade_Class_3 = Grade_C THEN
      Scale_Class_3 := Scale_C;

   ELSIF Grade_Class_3 = Grade_D THEN
      Scale_Class_3 := Scale_D;

   ELSIF Grade_Class_3 = Grade_F THEN
      Scale_Class_3 := Scale_F;

   END IF;

   --Class 4
   IF Grade_Class_4 = Grade_A THEN
      Scale_Class_4 := Scale_A;

   ELSIF Grade_Class_4 = Grade_B THEN
      Scale_Class_4 := Scale_B;

   ELSIF Grade_Class_4 = Grade_C THEN
      Scale_Class_4 := Scale_C;

   ELSIF Grade_Class_4 = Grade_D THEN
      Scale_Class_4 := Scale_D;

   ELSIF Grade_Class_4 = Grade_F THEN
      Scale_Class_4 := Scale_F;

   END IF;

   --Class 5
   IF Grade_Class_5 = Grade_A THEN
      Scale_Class_5 := Scale_A;

   ELSIF Grade_Class_5 = Grade_B THEN
      Scale_Class_5 := Scale_B;

   ELSIF Grade_Class_5 = Grade_C THEN
      Scale_Class_5 := Scale_C;

   ELSIF Grade_Class_5 = Grade_D THEN
      Scale_Class_5 := Scale_D;

   ELSIF Grade_Class_5 = Grade_F THEN
      Scale_Class_5 := Scale_F;

   END IF;

   --Calculate quality points for each class

   Quality_Class_1 := (Scale_Class_1 * Credit_Class_1);
   Quality_Class_2 := (Scale_Class_2 * Credit_Class_2);
   Quality_Class_3 := (Scale_Class_3 * Credit_Class_3);
   Quality_Class_4 := (Scale_Class_4 * Credit_Class_4);
   Quality_Class_5 := (Scale_Class_5 * Credit_Class_5);

   --Calculate total quality points
   Total_Quality_Points := (Quality_Class_1 + Quality_Class_2 +
                            Quality_Class_3 + Quality_Class_4 +
                            Quality_Class_5);

   --Calculate total credit hours
   Total_Credit_Hours := (Credit_Class_1 + Credit_Class_2 +
                          Credit_Class_3 + Credit_Class_4 +
                          Credit_Class_5);

   --Calculate GPA
   Grade_Point_Average := Total_Quality_Points/Total_Credit_Hours;

   --Print Calculated GPA
   Ada.Text_IO.Put(Item => "Your GPA is ");
   Ada.Float_Text_IO.Put(Item => Grade_Point_Average,
                         Fore => 1,
                         Aft  => 3,
                         Exp  => 0);
   Ada.Text_IO.Put(Item => ".");
   Ada.Text_IO.New_Line;
   Ada.Text_IO.Put(Item => "WOW! THAT IS AMAZING!");

END GPA_Calc;




   