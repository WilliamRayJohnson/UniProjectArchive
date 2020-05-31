--William Ray Johnson
--11/17/2014
With Ada.Text_IO;
Use Ada.Text_IO;
With Ada.Integer_Text_IO;
Use Ada.Integer_Text_IO;
With Ada.Float_Text_IO;
USE Ada.Float_Text_IO; 	


Procedure Orders_Of_Day is

	Max_Server_Name: constant Integer := 32;
	subtype Name_Type is String(1 .. Max_Server_Name);

	type Server_Name_Type is record
		Name: Name_Type;
		Name_Length: Integer;
	end record;

	type Order_Type is record
		Server: Server_Name_Type;
		Order_Number: Integer;
		N_Large, N_Medium, N_Small: Integer;
		Tip: Float;
	end record;

	Subtype Max_Orders is Integer range 1 .. 666;

	Type Order_Array is array(Max_Orders) of Order_Type;

	Function Bill_Total(Large: in Integer; Medium: in Integer; Small: in Integer) return Float is
		--Will calculate the total bill based on pizza count and their
		--respected prices

		--Cost of Pizza
		L_Pizza_Cost: Constant Float := 12.00;
		M_Pizza_Cost: Constant Float := 7.00;
		S_Pizza_Cost: Constant Float := 4.00;

		--Total
		Total: Float;

	Begin
		Total := (L_Pizza_Cost * Float(Large)) + (M_Pizza_Cost * Float(Medium)) + (S_Pizza_Cost * Float(Small));

		return Total;

	End Bill_Total;

	Function Tip_Percentage(Bill: in Float; Tip: in Float) return Float is
		--Calculate the percentage of the bill that the tip is

		Percent: Float;

	Begin
		Percent := 100.00 * (Tip / Bill);

		return Percent;

	End Tip_Percentage;

	--Input Variables
	Days_Order: File_Type;
	Order_File_Name: Constant String := "Orders.txt";

	--Output Variables
	Highest_Order: Integer;
	Highest_Tip_Serv: Server_Name_Type;
	Highest_Tip_Per_Bill_Serv: Server_Name_Type;
	Days_Order_Out: File_Type;
	Order_Out_Name: Constant String := "Orders_Out.txt";

	--Comparison Variables for Output Calculation
	High_Bill: Float := 0.0;
	High_Tip: Float := 0.0;
	High_Tip_Per: Float := 0.0;

	--Temporary Variables for Output Calculation
	Temp_High_Bill: Float;
	Temp_High_Tip_Per: Float;

	--Loop Variables
	Server_Nam: Server_Name_Type;
	Order_Num: Integer;
	L_Pizza: Integer;
	M_Pizza: Integer;
	S_Pizza: Integer;
	Tip: Float;
	Array_Count: Integer := 1;

	--Orders array
	Txts_Orders: Order_Array;

Begin
	--Load in .txt file
	Open(File => Days_Order, Mode => In_File, Name => Order_File_Name);

	Assign_Array:
	Loop
		--Assign File Data to variables
		Get_Line(File => Days_Order, Item => Server_Nam.Name, Last => Server_Nam.Name_Length);
		get(File => Days_Order, Item => Order_Num);
		get(File => Days_Order, Item => L_Pizza);
		get(File => Days_Order, Item => M_Pizza);
		get(File => Days_Order, Item => S_Pizza);
		get(File => Days_Order, Item => Tip);

		--Assign variables to array
		Txts_Orders(Array_Count).Server := Server_Nam;
		Txts_Orders(Array_Count).Order_Number := Order_Num;
		Txts_Orders(Array_Count).N_Large := L_Pizza;
		Txts_Orders(Array_Count).N_Medium := M_Pizza;
		Txts_Orders(Array_Count).N_Small := S_Pizza;
		Txts_Orders(Array_Count).Tip := Tip;

		Exit Assign_Array when End_Of_File(Days_Order);
		Skip_Line(Days_Order);

		--Add to count to move on to next item in array
		Array_Count := Array_Count + 1;

	End Loop Assign_Array;

	--Close .txt file
	Close(File => Days_Order);

	--Will loop through array to calculate outputs
	For i in 1 .. Array_Count Loop
		--Calculate this orders total and tip percentage
		Temp_High_Bill := Bill_Total(Large => Txts_Orders(i).N_Large, Medium => Txts_Orders(i).N_Medium, Small => Txts_Orders(i).N_Small);
		Temp_High_Tip_Per := Tip_Percentage(Bill => Temp_High_Bill, Tip => Txts_Orders(i).Tip);

		--Check if current arrays values are higher than the highest calculated
		If High_Bill < Temp_High_Bill then
			High_Bill := Temp_High_Bill;
			Highest_Order := Txts_Orders(i).Order_Number;
		end if;

		If High_Tip < Txts_Orders(i).Tip then
			High_Tip := Txts_Orders(i).Tip;
			Highest_Tip_Serv := Txts_Orders(i).Server;
		end if;

		If High_Tip_Per < Temp_High_Tip_Per then
			High_Tip_Per := Temp_High_Tip_Per;
			Highest_Tip_Per_Bill_Serv := Txts_Orders(i).Server;
		end if;

	End Loop;
	
	Create(File => Days_Order_Out, Mode => Out_File, Name => Order_Out_Name);

	--Output to screen
	Put("Today's best orders are as follow:");
	Skip_Line;

	Put("Highest bill order number: No. ");
	Put(Highest_Order, 1);
	New_Line;

	Put("Server with largest tip: " & Highest_Tip_Serv.Name(1 .. Highest_Tip_Serv.Name_Length));
	New_Line;

	Put("Server with largest tip/bill percentage: " & Highest_Tip_Per_Bill_Serv.Name(1 .. Highest_Tip_Per_Bill_Serv.Name_Length));
	New_Line;

	Put("Press enter to exit");
	Skip_Line;
	
	--Output to file
	Put(File => Days_Order_Out, Item => "Today's best orders are as follow:");
	New_Line(Days_Order_Out);

	Put(File => Days_Order_Out, Item => "Highest bill order number: No. ");
	Put(File => Days_Order_Out, Item => Highest_Order, Width => 1);
	New_Line(Days_Order_Out);

	Put(File => Days_Order_Out, Item => "Server with largest tip: " & Highest_Tip_Serv.Name(1 .. Highest_Tip_Serv.Name_Length));
	New_Line(Days_Order_Out);

	Put(File => Days_Order_Out, Item => "Server with largest tip/bill percentage: " & Highest_Tip_Per_Bill_Serv.Name(1 .. Highest_Tip_Per_Bill_Serv.Name_Length));
	New_Line(Days_Order_Out);

	Put(File => Days_Order_Out, Item => "Press enter to exit");

End Orders_Of_Day;
	