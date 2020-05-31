with Ada.Text_IO;
use Ada.Text_IO;
with Ada.Integer_Text_IO;
use Ada.Integer_Text_IO;

-----------------------------------------
--
-- CS 180 Lab 4
--
-- Program to demonstrate loops
-- Written by John Seiffertt
--
-- Modified by William Ray Johnson_________
-- on 9/16/2014__
--
------------------------------------------

procedure hours is

Hours_In_Day: CONSTANT Integer := 24;
Days_To_Count: CONSTANT Integer := 4;

Hours : Integer;
Minutes : Integer;
AMPM : String(1..2);
Hours_Printed : Integer;
Days_Count : Integer := 0;



begin

Hours := 00;
AMPM := "AM";
Hours_Printed := 0;

Outer_Loop:
loop
  Minutes := 0;

  Inner_Loop:
  LOOP
     Put(Item => "Day ");
     Put(Item => Days_Count + 1, width => 1);
     Put(Item => Hours);
     Put(Item => ":");
     If (Minutes = 0) then
       Put(Item => "0");
     end if;
     Put(Item => Minutes, Width => 1);
     New_Line;

     Minutes := Minutes + 30;
     exit Inner_Loop when Minutes = 60;
  end loop Inner_Loop;

  New_Line;

  Hours := Hours + 1;
  Hours_Printed := Hours_Printed + 1;

  If Hours = 24 then
     Hours := 00;
     Days_Count := Days_Count + 1;

  end if;

  EXIT Outer_Loop WHEN Hours_Printed = Days_To_Count * Hours_In_Day;


end loop Outer_Loop;

end hours;