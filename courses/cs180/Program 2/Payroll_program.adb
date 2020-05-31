--Will Ray Johnson 9/8/2014
--Program will calculate payroll stub based on annual salary and marital status

WITH Ada.Text_IO;
WITH Ada.Float_Text_IO;

PROCEDURE Payroll_Program IS

   --User information
   Employee_Name         : String(1 .. 32);
   EN_Length             : Integer;
   Date                  : String(1 .. 10);
   Ann_Sal               : Float;
   Filing_Status         : String(1 .. 1);

   --Output information
   Monthly_Sal           : Float;
   State_Tax             : Float;
   Fed_Tax               : Float;
   SS_Tax                : Float;
   Net_Sal               : Float;
   Net_Sal_Percent       : Float := 1.00; --Running total percent of net pay taken home in decimal form
   Pay_Stub_Out          : Ada.Text_IO.File_Type;

   --Social Security Tax Constants
   SS_Tax_Max            : CONSTANT Integer := 10000; --Maximum amount of money SS taxes
   SS_Tax_Rate           : CONSTANT Float   := 0.065;

   --Federal Tax Constants
   S_Low_Tier            : CONSTANT Integer := 1000; --Max amount of money for that tier
   S_Mid_Tier            : CONSTANT Integer := 5000;

   S_Low_Tier_Rate       : CONSTANT Float   := 0.00;
   S_Mid_Tier_Rate       : CONSTANT Float   := 0.20;
   S_High_Tier_Rate      : CONSTANT Float   := 0.30;

   M_Low_Tier            : CONSTANT Integer := 1500;
   M_Mid_Tier            : CONSTANT Integer := 7500;

   M_Low_Tier_Rate       : CONSTANT Float   := 0.00;
   M_Mid_Tier_Rate       : CONSTANT Float   := 0.20;
   M_High_Tier_Rate      : CONSTANT Float   := 0.30;

   S_High_Tier_Extra     : CONSTANT Integer := 800; --Money that must be paid due to high income
   M_High_Tier_Extra     : CONSTANT Integer := 1500;

   Single                : CONSTANT String  := "S";
   Married               : CONSTANT String  := "M";

   --State Tax Constants
   St_Tax_Low_Tier       : CONSTANT Integer := 1200;

   St_Tax_Low_Tier_Rate  : CONSTANT Float   := 0.00;
   St_Tax_High_Tier_Rate : CONSTANT Float   := 0.06;


BEGIN
   Ada.Text_IO.Put(Item => "Payroll Calculator");
   Ada.Text_IO.New_Line;
   Ada.Text_IO.New_Line;

   --Ask for users information
   Ada.Text_IO.Put(Item => "Please enter the date (MM/DD/YYYY): ");
   Ada.Text_IO.Get(Item => Date);
   Ada.Text_IO.Skip_Line;

   Ada.Text_IO.Put(Item => "Please enter the employee name: ");
   Ada.Text_IO.Get_Line(Item => Employee_Name, Last => EN_Length);

   Ada.Text_IO.Put(Item => "Enter annual salary: ");
   Ada.Float_Text_IO.Get(Item => Ann_Sal);

   Ada.Text_IO.Put(Item => "Enter filing status (S = Single, M = Married): ");
   Ada.Text_IO.Get(Item => Filing_Status);

   --Calculate Monthy Salary
   Monthly_Sal := Ann_Sal / 12.0;

   --Calculate Social Security Tax
   IF Monthly_Sal >= Float(SS_Tax_Max) THEN
      SS_Tax          := Float(SS_Tax_Max) * SS_Tax_Rate;
      Net_Sal_Percent := Net_Sal_Percent - (SS_Tax / Monthly_Sal);

   ELSE
      SS_Tax          := Monthly_Sal * SS_Tax_Rate;
      Net_Sal_Percent := Net_Sal_Percent - SS_Tax_Rate;

   END IF;

   --Calculate Federal Tax
   --Find out if user is single or married
   IF Filing_Status = Single THEN
      --Low Tier between $0 - $1000
      IF Monthly_Sal < Float(S_Low_Tier) THEN
         Fed_Tax         := Monthly_Sal * S_Low_Tier_Rate;
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      --Mid Tier between $1000 - $5000
      ELSIF Monthly_Sal < Float(S_Mid_Tier) THEN
         Fed_Tax         := (Monthly_Sal - Float(S_Low_Tier)) * S_Mid_Tier_Rate;
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      --High Tier between $5000 - Infinity
      ELSE
         Fed_Tax         := ((Monthly_Sal - Float(S_Mid_Tier)) * S_High_Tier_Rate) + Float(S_High_Tier_Extra);
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      END IF;

   --If the user is married
   ELSE
      --Low Tier between $0 - $1500
      IF Monthly_Sal < Float(M_Low_Tier) THEN
         Fed_Tax         := Monthly_Sal * M_Low_Tier_Rate;
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      --Mid Tier between $1500 - $7500
      ELSIF Monthly_Sal < Float(M_Mid_Tier) THEN
         Fed_Tax         := (Monthly_Sal - Float(M_Low_Tier)) * M_Mid_Tier_Rate;
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      --High Tier between $7500 - Infinity
      ELSE
         Fed_Tax         := ((Monthly_Sal - Float(M_Mid_Tier)) * M_High_Tier_Rate) + Float(M_High_Tier_Extra);
         Net_Sal_Percent := Net_Sal_Percent - (Fed_Tax / Monthly_Sal);

      END IF;

   END IF;

   --Calculate State Tax
   --Low Tier betweeen $0 - $1200
   IF Monthly_Sal < Float(St_Tax_Low_Tier) THEN
      State_Tax       := Monthly_Sal * St_Tax_Low_Tier_Rate;
      Net_Sal_Percent := Net_Sal_Percent - (State_Tax / Monthly_Sal);

   --High Tier between $1200 - Infinity
   ELSE
      State_Tax       := (Monthly_Sal - Float(St_Tax_Low_Tier)) * St_Tax_High_Tier_Rate;
      Net_Sal_Percent := Net_Sal_Percent - (State_Tax / Monthly_Sal);

   END IF;

   --Calculate Net Salary
   Net_Sal := Monthly_Sal - (SS_Tax + Fed_Tax + State_Tax);

   --Convert net salary percentage from a decimal to a percentage
   Net_Sal_Percent := Net_Sal_Percent * 100.00;

   --Create file for pay stub
   Ada.Text_IO.Create(File => Pay_Stub_Out,
                      Mode => Ada.Text_IO.Out_File,
                      Name => "Pay_Stub.txt");

   --Outputting results to screen and pay stub
   Ada.Text_IO.Put(Item => "Date:                ");
   Ada.Text_IO.Put(Item => Date);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Date:                ");
   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => Date);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);

   Ada.Text_IO.Put(Item => "Name:                ");
   Ada.Text_IO.Put(Item => Employee_Name (1 .. EN_Length));
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Name:                ");
   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => Employee_Name (1 .. EN_Length));
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);

   Ada.Text_IO.Put(Item => "Monthly Salary:      $ ");
   Ada.Float_Text_IO.Put(Item => Monthly_Sal,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Monthly Salary:      $ ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => Monthly_Sal,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);

   Ada.Text_IO.Put(Item => "Social Security Tax: $ ");
   Ada.Float_Text_IO.Put(Item => SS_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Social Security Tax: $ ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => SS_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);


   Ada.Text_IO.Put(Item => "Federal Tax:         $ ");
   Ada.Float_Text_IO.Put(Item => Fed_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Federal Tax:         $ ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => Fed_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);


   Ada.Text_IO.Put(Item => "State Tax:           $ ");
   Ada.Float_Text_IO.Put(Item => State_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "State Tax:           $ ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => State_Tax,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);


   Ada.Text_IO.Put(Item => "Net Salary:          $ ");
   Ada.Float_Text_IO.Put(Item => Net_Sal,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line;

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "Net Salary:          $ ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => Net_Sal,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.New_Line(File => Pay_Stub_Out, Spacing => 1);


   Ada.Text_IO.Put(Item => Employee_Name (1 .. EN_Length));
   Ada.Text_IO.Put(Item => ", you are taking home ");
   Ada.Float_Text_IO.Put(Item => Net_Sal_Percent,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.Put(Item => "% of your salary, after taxes.");

   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => Employee_Name (1 .. EN_Length));
   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => ", you are taking home ");
   Ada.Float_Text_IO.Put(File => Pay_Stub_Out,
                         Item => Net_Sal_Percent,
                         Aft  => 2,
                         Exp  => 0);
   Ada.Text_IO.Put(File => Pay_Stub_Out, Item => "% of your salary, after taxes.");



END Payroll_Program;


