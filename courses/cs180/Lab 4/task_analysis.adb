with Ada.Text_IO;
use Ada.Text_IO;
with Ada.Integer_Text_IO;
use Ada.Integer_Text_IO;
with Ada.Float_Text_IO;
use Ada.Float_Text_IO;

-----------------------------------------
--
-- CS 180 Lab 4
--
-- Program to demonstrate file I/O
-- Written by John Seiffertt
--
-- Modified by ____William Johnson____________
-- on __9/21/2014__
--
------------------------------------------

procedure task_analysis is

Input_File : Ada.Text_IO.File_Type;
Tasks: String(1..32);
Task_Length : Integer;

Number : Integer range 1 .. 10;
Choice : String(1..1);
Task_Name : String(1..32);
Size_Name : Integer;
Task_Status : String(1..11);
Size_Status : Integer;
Task_Hours : Integer;
Number_of_Tasks : Integer := 0;
Total_Hours : Integer := 0;
Count : Integer := 0;
Average_Hours : Float;

begin

--Ask user what file to load
Put(Item => "What file would you like to load? ");
Get_Line(Item => Tasks, Last => Task_Length);

Put ("How many tasks do you want to analyze? (Max 10 tasks) ");
Get (Item => Number);
Put ("Do you want an analysis of (c)ompleted or (f)inished tasks? ");
Get (Item => Choice);

Open(File => Input_File, Mode => Ada.Text_IO.In_File, Name => Tasks(1..Task_Length));


Task_Loop:
loop
   Get_Line(File => Input_File, Item => Task_Name, Last => Size_Name);
   Get_Line(File => Input_File, Item => Task_Status, Last => Size_Status);
   Get(File => Input_File, Item => Task_Hours);
   Skip_Line(File => Input_File);

  if (Choice = "c") and (Task_Status(1..9) = "completed") then
    Number_of_Tasks := Number_of_Tasks + 1;
    Total_Hours := Total_Hours + Task_Hours;
    Put("Completed task ");
    Put(Item => Number_of_Tasks, width => 1);
    Put(Item => " is " & Task_Name(1..Size_Name));
    New_Line;
  elsif (Choice = "f") and (Task_Status(1..8) = "finished") then
    Number_of_Tasks := Number_of_Tasks + 1;
    Total_Hours := Total_Hours + Task_Hours;
    Put("finished task ");
    Put(Item => Number_of_Tasks, width => 1);
    Put(Item => " is " & Task_Name(1..Size_Name));
    New_Line;
  end if;

  Count := Count + 1;

  exit Task_Loop when Count = Number;

end loop Task_Loop;

Close(File => Input_File);

New_Line;
New_Line;
Put("Your total number of task hours is ");
Put(Item => Total_Hours, Width => 1);
New_Line;
Put("Your average number of hours per task is ");

Average_Hours := Float(Total_Hours) / Float (Number_of_Tasks);

Put(Item => Average_Hours, Fore => 2, Aft => 2, Exp => 0);

end task_analysis;