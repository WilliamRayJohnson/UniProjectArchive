with Ada.Text_Io;
with Ada.Float_Text_Io;

--------------------------------------------------
--
-- A program to convert feet to other measurements.
--
-- Written by: Bob Matthews.  Modified by: Will Ray Johnson.
-- Date: 9/2/2014
--
---------------------------------------------------

procedure Convert_Distance is

   Output_Precision : constant Integer := 2;
   Inches_Per_Foot  : CONSTANT Float := 12.0;
   Feet_Per_Meter   : CONSTANT Float := 3.281;
   Parsec_Per_Foot  : CONSTANT Float := 9.871E-18;

   -- Number of feet, input by user.
   Number_Of_Feet : Float;

   -- Number of inches, calculated by program.
   Number_Of_Inches : Float;

   -- Number of Meters, calculated by program.
   Number_Of_Meters : Float;

   -- Number of Parsecs, calculated by program.
   Number_Of_Parsecs: Float;

begin

   -- Prompt user for input, and then allow user
   -- to enter the number of feet.
   Ada.Text_Io.Put (Item => "Please enter the number of feet: ");
   Ada.Float_Text_Io.Get (Item => Number_Of_Feet);

   -- Convert feet to inches
   Number_Of_Inches := Number_Of_Feet * Inches_Per_Foot;

   -- Convert feet to meters
   Number_Of_Meters := Number_Of_Feet / Feet_Per_Meter;

   -- Convert feet to parsecs
   Number_Of_Parsecs:= Number_Of_Feet * Parsec_Per_Foot;

   -- Print the result.
   Ada.Text_Io.Put (Item => "There are ");

   -- Print the output in scientific notation
   Ada.Float_Text_Io.Put (
      Item => Number_Of_Inches,
      Aft  => Output_Precision);

   Ada.Text_Io.Put (Item => " inches in ");

   Ada.Float_Text_Io.Put (
      Item => Number_Of_Feet,
      Aft  => Output_Precision);

   Ada.Text_Io.Put (Item => " feet.");
   Ada.Text_Io.New_Line;

   -- Print number of meters
   Ada.Text_IO.Put (Item => "And there are ");

   Ada.Float_Text_IO.Put (
      Item => Number_Of_Meters,
      Aft  => Output_Precision);

   Ada.Text_IO.Put (Item => " meters in ");

   Ada.Float_Text_IO.Put (
      Item => Number_Of_Feet,
      Aft  => Output_Precision);

   Ada.Text_IO.Put (Item => " feet.");
   Ada.Text_IO.New_Line;

   -- Print number of Parsecs
   Ada.Text_IO.Put (Item => "And if you were a captain of a starship, you might want to know that,");
   Ada.Text_IO.New_Line;
   Ada.Text_IO.Put (Item => "there are ");

   Ada.Float_Text_IO.Put (
      Item => Number_Of_Parsecs,
      Aft  => Output_Precision);

   Ada.Text_IO.Put (Item => " parsecs in ");

   Ada.Float_Text_IO.Put (
      Item => Number_Of_Feet,
      Aft  => Output_Precision);

   Ada.Text_IO.Put (Item => " feet.");
   Ada.Text_IO.New_Line;

end Convert_Distance;
