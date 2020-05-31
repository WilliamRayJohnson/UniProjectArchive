with Ada.Float_Text_Io;
WITH Ada.Text_Io;
WITH Ada.Integer_Text_IO;


procedure Wage is

   ------------------------------------
   -- This program figures the wage for
   -- a worker.
   --
   -- Written by Bob Matthews
   -- Modified by Will Ray Johnson
   -- Date:  Sep. 9, 2014
   ------------------------------------

   Full_Time_Hours : constant Float  := 40.0;
   Overtime_Rate   : constant Float  := 1.5;
   Wage_Increase   : CONSTANT Float  := 0.07;
   Long_Tenure     : CONSTANT Integer:= 12;
   --Cost of insurance
   High_Insure     : CONSTANT Float  := 320.00;
   Low_Insure      : CONSTANT Float  := 240.00;
   --Identifier for insurance
   Hi_Insurance    : CONSTANT String := "H";

   Base_Wage             : Float;
   Hours_Worked          : Float;
   Overtime_Hours_Worked : Float;
   Pay                   : Float;
   Months_Worked         : Integer;
   Insurance_Type        : String(1 .. 1);


begin

   -- Prompt user to enter hourly wage, hours worked,
   -- and months worked
   Ada.Text_Io.Put (Item => "Please enter hourly wage: ");
   Ada.Float_Text_Io.Get (Item => Base_Wage);

   Ada.Text_Io.Put (Item => "Please enter hours worked: ");
   Ada.Float_Text_Io.Get (Item => Hours_Worked);

   Ada.Text_IO.Put(Item => "Please enter number of months worked: ");
   Ada.Integer_Text_IO.Get(Item => Months_Worked);

   Ada.Text_IO.Put(Item => "Please enter your insurance type (H or L): ");
   Ada.Text_IO.Get(Item => Insurance_Type);

   -- Determine if overtime was worked.  Then, compute
   -- wage using appropriate formula
   if Hours_Worked <= Full_Time_Hours then

      -- No overtime
      Pay := Base_Wage * Hours_Worked;

   else

      -- Overtime.  Figure overtime overs worked, then
      -- compute pay based on the overtime rate.
      Overtime_Hours_Worked := Hours_Worked - Full_Time_Hours;
      Pay := Base_Wage * Full_Time_Hours
         + Overtime_Hours_Worked * Base_Wage * Overtime_Rate;

   END IF;

   --Check and see if worker needs a wage increase
   IF Months_Worked >= Long_Tenure THEN
      Pay := Pay + (Pay * Wage_Increase);

   END IF;

   --Check for what type of insurance worker has
   --and subtract the insurance type from pay
   IF Insurance_Type = Hi_Insurance THEN
      Pay := Pay - High_Insure;

   ELSE
      Pay := Pay - Low_Insure;

   END IF;




   -- Output total pay amount.
   Ada.Text_Io.Put (Item => "Pay is $");
   Ada.Float_Text_Io.Put (
      Item => Pay,
      Fore => 0,
      Aft  => 2,
      Exp  => 0);
   Ada.Text_Io.New_Line;



end Wage;
