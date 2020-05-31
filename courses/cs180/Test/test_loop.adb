WITH Ada.Text_IO;




PROCEDURE Test_Loop IS
   Count : Integer := 0;


BEGIN

   Print_Loop:
      LOOP
      Ada.Text_IO.Put(Item => '5');
      Ada.Text_IO.New_Line;
      Count := Count + 1;
      EXIT Print_Loop;

   end loop print_loop;

END Test_Loop;

