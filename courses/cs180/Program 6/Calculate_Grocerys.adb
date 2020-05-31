--William Ray Johnson
--12/5/2014
With Ada.Text_IO;
Use Ada.Text_IO;
With Ada.Integer_Text_IO;
Use Ada.Integer_Text_IO;
With Ada.Float_Text_IO;
USE Ada.Float_Text_IO;

Procedure Calculate_Grocerys is

	Max_Grocery_String_Length: constant Integer := 64;
	Max_Items: constant Integer := 100;

	type Grocery_String is record
		Name: String (1 .. Max_Grocery_String_Length);
		Name_Length: Integer range 0 .. Max_Grocery_String_Length;
	end record;

	type Grocery_Item is record
		Item: Grocery_String;
		Quantity: Integer;
		Cost_Per_Item: Float;
		Total_Cost: Float;
	end record;

	type Grocery_List is array (1 .. Max_Items) of Grocery_Item;

	Procedure Get_Grocerys(List_O_Groce: in out Grocery_List;
						   Groce_Input: in File_Type;
						   Num_Of_Groce: out Integer) is
		--Assign values to array from .txt file and calculate total price
		Count: Integer := 1;

	Begin
		--Loop until end of file is reached
		Assign_Groce:
		Loop
			--Assign values from .txt
			Get(File => Groce_Input, Item => List_O_Groce(Count).Quantity);
			Get(File => Groce_Input, Item => List_O_Groce(Count).Cost_Per_Item);
			Get_Line(File => Groce_Input, Item => List_O_Groce(Count).Item.Name, Last => List_O_Groce(Count).Item.Name_Length);

			--Calculate total cost
			List_O_Groce(Count).Total_Cost := Float(List_O_Groce(Count).Quantity) * List_O_Groce(Count).Cost_Per_Item;

			exit Assign_Groce when End_Of_File(Groce_Input);

			--Increment count
			Count := Count + 1;
		end Loop Assign_Groce ;

		Num_Of_Groce := Count;
	End Get_Grocerys;

	Function Minimum(List_O_Groce: in Grocery_List; Index: in Integer; List_Length: in Integer) return Integer is
		--Will find the lowest total in an array
		--Will search through current index of loop to the length of list
		The_Lowest: Integer;
	Begin
		--Set The_Lowest equal to some item on the list for comparison
		The_Lowest := Index;

		--Loop through all values except first one
		for i in Index + 1 .. List_Length loop
			if List_O_Groce(i).Total_Cost < List_O_Groce(The_Lowest).Total_Cost then
				The_Lowest := i;
			end if;
		end loop;

		return The_Lowest;
	end Minimum;

	Procedure Tradesies(First: in out Grocery_Item; New_Low: in out Grocery_Item) is
		--Trade first in array for new low
		Temp_Low: Grocery_Item;

	Begin
		Temp_Low := New_Low;
		New_Low := First;
		First := Temp_Low;
	end Tradesies;

	--.txt Variables
	Grocery_File_Name: Constant String := "Grocery.txt";
	Grocery_File: File_Type;

	--List Variables
	List: Grocery_List;
	Number_Of_Items: Integer;

	Min_i: Integer;


Begin
	--Open File
	Open(File => Grocery_File, Mode => In_File, Name => Grocery_File_Name);

	Get_Grocerys(List, Grocery_File, Number_Of_Items);

	--Close File
	Close(Grocery_File);

	--Selection sort
	For i in List'First .. Number_Of_Items - 1 loop
		Min_i := Minimum(List, i, Number_Of_Items);
		Tradesies(List(i), List(Min_i));
	end loop;


	--Makes a box at before data to describe items
	--NOTICE: COMMAND PROMPT WIDTH BUFFER MUST AT LEAST BE 100 IN ORDER FOR DATA TO BE DISPLAED CORRECTLY
	Put("--------------------------------");
	For i in 5 .. Max_Grocery_String_Length loop
		Put("-");
	End loop;
	New_Line;

	Put("| Quantity | Item");
	For i in 5 .. Max_Grocery_String_Length loop
		Put(" ");
	End loop;
	Put("| Total Price |");
	New_Line;

	Put("--------------------------------");
	For i in 5 .. Max_Grocery_String_Length loop
		Put("-");
	End loop;
	New_Line;
	--Box finished

	--Print data to screen
	For i in 1 .. Number_Of_Items loop
		Put(List(i).Quantity);
		Put(" ");
		Put(List(i).Item.Name(1 .. List(i).Item.Name_Length));
		--Put "-" for every space in string that is not taken up
		For j in List(i).Item.Name_Length .. Max_Grocery_String_Length loop
			Put("-");
		end loop;
		Put("  ");
		Put(Item => List(i).Total_Cost, Fore => 0, Aft => 2, Exp => 0);
		New_Line;
	end loop;

	Skip_Line;

End Calculate_Grocerys;


