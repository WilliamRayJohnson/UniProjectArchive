--William Ray Johnson
--9/21/14
WITH Ada.Text_IO;
USE Ada.Text_IO;
WITH Ada.Integer_Text_IO;
USE Ada.Integer_Text_IO;


PROCEDURE Guessing_Game IS

   --User Variables
   Username          : String(1 .. 20);
   UN_Length         : Integer;
   Play_Or_Quit      : String(1 .. 1);

   --Game data
   Game_Data         : File_Type;
   GD_File_Name      : CONSTANT String := "guess.txt";
   Game_Selected     : Integer;
   The_Number        : Integer;
   Number_Of_Guesses : Integer;
   Low_Range         : Integer;
   High_Range        : Integer;
   Guess             : Integer;
   High_Or_Low       : String(1 .. 4);
   HL_Length         : Integer;
   Game_Clear        : Boolean;

   --Loop Variables
   GS_Count          : Integer   := 1;


BEGIN
   Put(Item => "Welcome to the Program!");
   New_Line(Spacing => 3);

   --Ask for users name
   Put(Item => "What is your name?: ");
   Get_Line(Item => Username, Last => UN_Length);
   New_Line;

   Put(Item => "Let's begin the game");
   New_Line(Spacing => 2);


   Main_Loop:
      LOOP
      --Select Game
      Put(Item => "Which game do you want to load? (Enter 1 - 10): ");
      Get(Item => Game_Selected);
      New_Line(Spacing => 2);


      Put(Item => "Loading game data...");
      New_Line(Spacing => 2);
      --Load data from .txt to program variables
      Open(File => Game_Data, Mode => In_File, Name => GD_File_Name);
      GS_Count := 1;



      Game_Select:
         LOOP
         IF GS_Count = Game_Selected THEN
            Get(File => Game_Data, Item => The_Number);
            Get(File => Game_Data, Item => Low_Range);
            Get(File => Game_Data, Item => High_Range);
            Get(File => Game_Data, Item => Number_Of_Guesses);
            EXIT Game_Select;

         END IF;
         Skip_Line(File => Game_Data);
         GS_Count := GS_Count + 1;

      END LOOP Game_Select;

      Put(Item => "Game data loaded");
      New_Line(Spacing => 2);

      --Set game clear flag to false for new game
      Game_Clear := False;

      Game_Loop:
         LOOP
         --Tell user how many guesses are left
         Put(Item => "You have ");
         Put(Item => Number_Of_Guesses, Width => 1);
         Put(Item => " guesses left.");
         New_Line(Spacing => 2);


         --Ask user for guess
         Put(Item => "Please enter a guess between ");
         Put(Item => Low_Range, Width => 1);
         Put(Item => " and ");
         Put(Item => High_Range, Width => 1);
         Put(Item => ": ");
         Get(Item => Guess);
         New_Line;

         --Determine if guess is correct
         IF Guess = The_Number THEN
            Game_Clear := True;
         ELSE
            Number_Of_Guesses := Number_Of_Guesses - 1;

         END IF;

         --Determine if guess was too high or low
         IF Game_Clear = False THEN
            IF Guess > The_Number THEN
               High_Or_Low := "high";
               HL_Length   := 4;

            ELSE
               High_Or_Low := "low ";
               HL_Length   := 3;

            END IF;

            --Sorry user
            Put(Item => "Sorry, " & Username(1 .. UN_Length) & ", your guess is too ");
            Put(Item => High_Or_Low(1 .. HL_Length));
            Put(Item => "!");
            New_Line(Spacing => 2);


         END IF;

         Exit Game_Loop when Number_of_Guesses = 0 or Game_Clear;

      END LOOP Game_Loop;

      --Close File since game is over
      Close(File => Game_Data);

      --Print text based on if user won or lost
      IF Game_Clear = False THEN
         Put(Item => "Sorry, but you have run out of guesses and the game is over.");
         New_Line(Spacing => 2);

      ELSE
         Put(Item => "Congratulations, " & Username(1 .. UN_Length) & "! Your guess is correct!");
         New_Line(Spacing => 2);

      END IF;

      --Ask user if they would like to play again
      Put(Item => "Would you like to (p)lay again or (q)uit? ");
      Get(Item => Play_Or_Quit);
      New_Line;


      --Play or Quit?
      IF Play_Or_Quit = "q" THEN
         EXIT Main_Loop;

      END IF;


      END LOOP Main_Loop;

END Guessing_Game;

