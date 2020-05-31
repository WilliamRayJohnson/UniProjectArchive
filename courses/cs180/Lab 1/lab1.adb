-- Will Johnson 9/1/2014
WITH Ada.Text_IO;
WITH Ada.Integer_Text_IO;

PROCEDURE Lab1 IS
   MyName      :String(1 .. 10);
   NameLength  :Integer;
   YearBorn    :Integer RANGE 1900 .. 2100;
   AgeIn2067   :Integer;

BEGIN
   Ada.Text_IO.Put(Item => "What is your first name?");
   Ada.Text_IO.Get_Line(Item => MyName,Last => NameLength);
   Ada.Text_IO.Put(Item => "Welcome, ");
   Ada.Text_IO.Put(Item => MyName (1 .. NameLength));
   Ada.Text_IO.Put(Item => ", to CS 180!");
   Ada.Text_IO.New_Line;
   Ada.Text_IO.Put(Item => "In what year where you born?");
   Ada.Integer_Text_IO.Get(Item => YearBorn);
   Ada.Text_IO.Put(Item => "Well, ");
   Ada.Integer_Text_IO.Put(Item => YearBorn,Width => 4);
   AgeIn2067 := 2067 - YearBorn;
   Ada.Text_IO.Put(Item => " sure was a great year!");
   Ada.Text_IO.Put(Item => "In 2067, you'll be ");
   Ada.Integer_Text_IO.Put(Item => AgeIn2067,Width => 3);
   Ada.Text_IO.Put(Item =>". That's Truman's bicentennial! WOW!");
END Lab1;

