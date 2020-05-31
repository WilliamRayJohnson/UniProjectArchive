--William Ray Johnson
--10/22/14
WITH Ada.Text_IO;
USE Ada.Text_IO;
WITH Ada.Integer_Text_IO;
USE Ada.Integer_Text_IO;
WITH Ada.Float_Text_IO;
USE Ada.Float_Text_IO;

PROCEDURE Checks_And_Deposits IS

	Function Transaction_Type return integer is
		--Will ask user for transaction type and return 1 or 0
		--1 being a check and 0 being a deposit

            Trans_Type : Character;
            Trans_Return: Integer;

	Begin
		Put(Item => "Enter a transaction type (C or D): ");
		Get(Item => Trans_Type);
		If Trans_Type = 'c'  or Trans_Type = 'C' Then
			Trans_Return := 1;
		Elsif Trans_Type = 'D' or Trans_Type = 'd' Then
                  Trans_Return := 0;
            END IF;

            Return Trans_Return;

	End Transaction_Type;

	Function Initial_Balance return Float is
		--Will ask user for initial balance
		--and return what was input

		Balance : Float;

	Begin
		Put(Item => "Enter the initial balance: ");
            Get(Item => Balance);

		return Balance;

	End Initial_Balance;

	Function Num_Of_Transactions return Integer is
		--Will ask user for number of transactions
		--and return what was input

		Transactions : Integer;

	Begin
		Put(Item => "Enter the number of transactions: ");
		Get(Item => Transactions);
		New_Line;

		return Transactions;

	End Num_Of_Transactions;

	Function Check_Amount return Float is
		--Ask user for how much the check is
		--and return what is input

		Amount : Float;

	Begin
		Put(Item => "Enter the check amount: ");
		Get(Item => Amount);

		return Amount;

	End Check_Amount;

	Function Deposit_Amount return Float is
		--Ask user for how much the deposit is
		--and return what is input

		Amount : Float;

	Begin
		Put(Item => "Enter the deposit amount: ");
		Get(Item => Amount);

		return Amount;

	End Deposit_Amount;

	Function Deposit(Munnys : in Float) return Float is
		--Calls Deposit_Amount and adds it to current balance

		Deposit : Float;
		New_Bal : Float;

	Begin
		Deposit := Deposit_Amount;
		New_Bal := Munnys + Deposit;

		Return New_Bal;

	End Deposit;

	Function Check(Munnys : in Float) return Float is
		--Calls Check_Amount and subtracts it from current balance

		Check   : Float;
		New_Bal : Float;

	Begin
		Check   := Check_Amount;
		New_Bal := Munnys - Check;

		Return New_Bal;

	End Check;

	Function New_Balance(Trans_Type_Var : in Integer; Munnys : in Float) return Float is
		--Based on Transaction type will either add or subtract money from balance

	Begin
		If Trans_Type_Var = 1 then
			return Check(Munnys => Munnys);
		Else
			return Deposit(Munnys => Munnys);
		End if;

	End New_Balance;

	Function Service_Charge(Num_of_Chks : in Integer; Munnys : in Float) return Float is
		--Determines if Service charge is needed and subtracts accordingly

		Serv_Char 			: Constant Float   := 0.79;
		Num_Of_Free_Chks	: Constant Integer := 2;
		New_Bal				: Float;

	Begin
		if Num_of_Chks > Num_Of_Free_Chks Then
			New_Bal := Munnys - Serv_Char;
		Else
			New_Bal := Munnys;
		End if;

		return New_Bal;

	End Service_Charge;

	Function Overdraft(Munnys : in Float; Trans_Type : in Integer) return Float is
		--Determine if balance is negative and charge if it is

		Ovdf_Charge		: Constant Float := 25.00;
		New_Bal			: Float;

	Begin
		If Munnys < 0.00 and Trans_Type = 1 then
			New_Bal := Munnys - Ovdf_Charge;
		Else
			New_Bal := Munnys;
		End if;

		Return New_Bal;

	End Overdraft;

	PROCEDURE Warning(Munnys : in Float) is
		--If warning if balance is low

		Warn_Limit		: Constant Float := 10.00;

	Begin
		If Munnys <= 0.00 then
			Put(Item => "Warning: You are overdrawn!");
		Elsif Munnys <= Warn_Limit then
			Put(Item => "Warning: Low Balance");

		End if;

	End Warning;

	--Main variables
	Int_Bal	    : Float;
	Trans_Num	: Integer;
	Trans_Type	: Integer;
	Tol_Checks	: Integer := 0;
	New_Baln	: Float;

Begin

	Int_Bal := Initial_Balance; --ask user for balance
	Trans_Num:= Num_Of_Transactions; --ask user for # of transactions
	New_Baln := Int_Bal; --makes copy of the initial balance for changes

	Transactioins_Loop:
		For i in 1 .. Trans_Num Loop
			Trans_Type  := Transaction_Type;   --Gets type as 1 or 0
			Tol_Checks	:= Tol_Checks + Trans_Type; --Checks are 1 and deposits are 0 so if you add it you get total checks
			New_Baln	:= New_Balance(Trans_Type_Var => Trans_Type, Munnys => New_Baln); --Calculates new balance based on current balance and transaction type
			New_Baln	:= Service_Charge(Num_of_Chks => Tol_Checks, Munnys => New_Baln); --Subtracts service charge if necessary
			New_Baln	:= Overdraft(Munnys => New_Baln, Trans_Type => Trans_Type); --Subtracts overdraft if necessary
			Put(Item => "New Balance: $");
			Put(Item => New_Baln, Fore => 0, Aft => 2, Exp => 0);
			New_Line;
			Warning(Munnys => New_Baln); --if balance is within a certain range, then a warning will be issued
			New_Line;
			New_Line;

		End Loop Transactioins_Loop;


End Checks_And_Deposits;


