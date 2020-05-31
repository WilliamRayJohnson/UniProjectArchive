with Ada.Integer_Text_Io, Ada.Text_Io;
use Ada.Integer_Text_Io, Ada.Text_Io;

procedure Subprogram_Example is

   ------------------------------------
   -- A program to experiment with procedures
   -- and parameters.
   --
   -- Written by: William Ray Johnson
   -- Date:  September 30, 2014
   ------------------------------------



   procedure Maximum (
         A   : in     Integer;
         B   : IN     Integer;
         Max : out    Integer) is

      -- This procedure computes the maximum of
      -- two integers and returns the largest through
      -- the parameter Max.

      Largest : Integer;

   begin

      if A > B then
         Largest := A;
      else
         Largest := B;
      end if;

      Max := Largest;

   end Maximum;

   -- Main program variables
   Value1      :   Integer;
   Value2      :   Integer;
   Value3      :   Integer;
   Value4      :   Integer;
   Max_3_4     :   Integer;
   Max_1_2_3_4 :   Integer;
   Max_1_2 : Integer;

begin

   -- Prompt the user for four values.
   Put (Item => "Please enter four integers: ");
   Get (Item => Value1);
   Get (Item => Value2);
   Get (Item => Value3);
   Get (Item => Value4);

   --Max of first two values
   Maximum (
      A   => Value1,
      B   => Value2,
      Max => Max_1_2);

   --Max of second two values
   Maximum (
      A   => Value3,
      B   => Value4,
      Max => Max_3_4);

   --Max of all four values using max of frist and second two values
   Maximum (
      A   => Max_1_2,
      B   => Max_3_4,
      Max => Max_1_2_3_4);


   -- Print the max
   Put (Item => "The maximum of ");
   Put (
      Item  => Value1,
      Width => 0);
   Put (" ");
   Put (
      Item  => Value2,
      Width => 0);
   Put (" ");
   Put (
      Item  => Value3,
      Width => 0);
   Put (" ");
   Put (
      Item  => Value4,
      Width => 0);
   Put (" is ");
   Put (
      Item  => Max_1_2_3_4,
      Width => 0);
   Put_Line (".");

end Subprogram_Example;
