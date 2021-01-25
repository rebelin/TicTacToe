/**
 * Simulates a two-player tictactoe game
 * @author Rebecca Lin
 * @version 1.0
 */

import java.util.Scanner;
public class TicTacToe
{
  public static void main(String[] args)
  {
    // Declare in advance
    Scanner keys = new Scanner(System.in);
		boolean win = false;
    int turn;
		String place;

		System.out.println("Welcome to tic tac toe.");

    // Give user(s) the choice for board size
    System.out.print("What would you like the size (s) of the board (s x s) to be? s = ");
		int size = keys.nextInt();
		keys.nextLine();
		String[][] grid = init_grid(size);
		printBoard(grid);

    System.out.println("Each player: choose a different character to use as a marker.");

    // Create an array including everything but numbers, so that the player doesn't choose a number as a marker
    int [] characters = new int[84];

    // Add the ascii numbers of non-number characters
    for (int i = 0; i < 83; i++)
    {
      for(int n = 33; n < 48; n++)
      {
        characters[i] = n;
        i ++;
      }
      for(int c = 58; c < 127; c++)
      {
        characters[i] = c;
        i ++;
      }
    }

		// Ask for the players' characters/markers
		String p1 = valid_char(1, characters);
    String p2 = valid_char(2, characters);
    System.out.println();

    // Randomly determine who goes first
    if (who_is_first() == true)
    {
      turn = 1;
    }
    else
    {
      turn = 2;
    }

    // General gameplay that loops as long as the conditions are met
		while((win == false) && (turn < (size * size)))
		{
      // Use module to determine whose turn it is
      if (turn % 2 == 1)
      {
        System.out.print("Player 1, ");
      }
      else if (turn % 2 == 0)
      {
        System.out.print("Player 2, ");
      }

			placeSymbol(valid_cell_num(size), grid, size, turn, p1, p2);
			turn ++;
			win = checkWin(grid, p1, p2);
			printBoard(grid);
		}

		if (turn % 2 == 0 && win == true)
    {
			System.out.println("Player 1 wins!");
		}
		else if (turn % 2 == 1 && win == true)
    {
			System.out.println("Player 2 wins!");
		}
		else
    {
			System.out.println("You tied.");
    }
  }

  /** Randomly chooses who goes first
    * @return Boolean representing who goes first
    */
  public static boolean who_is_first()
  {
    // Player 1 (0.0 - 0.5); Player 2 (0.5 - 1.0)
    if (Math.random() < 0.5)
		{
      return true;
    }
	  else
    {
		  return false;
    }
  }

  /**
	* Adds the users' respective symbol on to the board
	* @param cell  The cell on the board
	* @param grid  The string array representing the tictactoe board
	* @param size  The given size of the board
	* @param first  The string that is player one's symbol
	* @param second  The string that is player two's symbol
	*/
	public static void placeSymbol(String cell, String[][] grid, int size, int turn, String first, String second)
  {
		String symbol = "";
		int spot = Integer.parseInt(cell);

	  int row = spot / size;
	  int column = spot % size;

    if (check_cell(grid, spot) == true)
    {
		  if (turn % 2 == 1)
      {
			 symbol = first;
		  }
		  else
      {
			 symbol = second;
		  }
      // Replace the cell with a player's character
		  grid[(spot - 1) / size][(spot - 1) % size] = symbol;
    }
    else
    {
      // Recurse in order to ensure the cell isn't reused
      System.out.print("That cell has already been used. Try again. ");
  		placeSymbol(valid_cell_num(size), grid, size, turn, first, second);
    }
	}

  /** Determines if a player's chosen character is valid
    * @param p The integer representing the player
    * @param arr The integer array of ascii numbers for non-numerical characters
    * @return The string representing the player's character
    */
  public static String valid_char(int p, int [] arr)
  {
    boolean validChar = false;
    String str = "";

    while (validChar != true)
    {
      System.out.print("Player " + p + ", please enter your character: ");
      Scanner input = new Scanner(System.in);
      str = input.nextLine();

      // Makes sure the player chooses only one character
      if (str.length() <= 1)
      {
        // Convert current character to an ASCII value
        char c = str.charAt(0);
        int ascii = (int) c;
        for (int i = 0; i < arr.length; i++)
        {
          // Checks if the chosen character
          if(arr[i] == ascii)
          {
            validChar = true;
            // Remove used character to avoid duplicates
            arr[i] = 0;
          }
        }
        if(validChar == false)
        {
          System.out.println("That character is not valid. Please enter a different one. (No numbers!)");
        }
      }
      else
      {
        System.out.println("Please enter one character.");
      }
    }
    return str;
  }

  /** Checks if the input is a valid cell number and asks again if it isn't
    * @param dim The dimension(s) of the board
    * @return The cell as an integer
    */
  public static String valid_cell_num(int dim)
  {
    Scanner keys = new Scanner(System.in);
    boolean validNum = false;
    String num = "";

	  while (validNum != true)
    {
      System.out.print("please choose/mark a cell number: ");
			num = keys.nextLine();

      // Check if input is an integer
      if (isInteger(num) == true)
      {
        int n = Integer.parseInt(num);
        // Any number that isn't within the grid's dimensions is invalid
			  if (n > dim * dim)
			  {
          System.out.println("That cell doesn't exist. Please pick an available cell number.");
        }
        else
        {
			    validNum = true;
        }
      }
      else
      {
        System.out.println("That's not a number. Try again: ");
      }
    }
    return num;
  }

  /** Checks if the coordinate in the list is an integer
    * @param grid Current board
    * @param cell The cell in the board
    * @return A boolean indicating if the coordinate is an integer
    */
  public static boolean check_cell(String [][] grid, int cell)
  {
    int dim = grid.length;

	  if (isInteger(grid[(cell - 1) / dim][(cell - 1) % dim]) == true)
    {
		  return true;
    }
	  else
    {
		  return false;
    }
  }

  /** Determines if input is an integer
    * @param input A inputed string
    * @return A boolean indicating if the input is an input
    */
  public static boolean isInteger(String input)
  {
    // Test if the string input is an integer
    try
    {
      Integer.parseInt(input);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
	  * Initializes an int array that is our board
	  * @param size The dimensions of a square board
	  * @return grid The initialized array
	  */
	public static String[][] init_grid(int size)
  {
    // Add integers in the cells as place holders
		String[][] grid = new String[size][size];
		int counter = 1;
		for (int i = 0; i < size; i++)
    {
		  for (int n = 0; n < size; n++)
      {
		    grid[i][n] = Integer.toString(counter);
	      counter++;
		  }
	  }
	 return grid;
	}

  /**
	  * Prints the tictactoe board
	  * @param arr The string array to be printed as a grid
	  */
	public static void printBoard(String[][] arr)
	{
		for (int row = 0; row < arr.length; row++)
		{
			for (int col = 0; col < arr[row].length; col++)
      {
				System.out.print(" –––––––");
      }
			System.out.println();
			System.out.print("|");
			for (int col = 0; col < arr[row].length; col++)
      {
				System.out.print(arr[row][col] + "\t |");
      }
			System.out.println();
		}
		for (int col = 0; col < arr[0].length; col++)
    {
			System.out.print(" –––––––");
    }
		System.out.println();
	}

  /**
	  * Checks for a win by calling upon helper functions
	  * @param array The string array that represents the current board
    * @param c1 String indicating player 1's character
    * @param c2 String indicating player 2's character
	  * @return win A boolean representing a player's (winning) status
	  */
	public static boolean checkWin(String[][] array, String c1, String c2)
  {
	 boolean win;
   // If any condition is true, there is a winner
	 if (checkDiag(array, c1) == true || checkVert(array) == true || checkHoriz(array) == true)
   {
     win = true;
	 }
   else if (checkDiag(array, c2) == true || checkVert(array) == true || checkHoriz(array) == true)
   {
     win = true;
   }
	 else
   {
     win = false;
   }
	 return win;
	}

  /**
	  * Helper function for checkwinner
    * Checks if either diagonal is homogenous to the character
	  * @param grid The String array representing the current board
    * @param c The string/character
	  * @return A boolean indicating if there is a diagonal win
	  */
	public static boolean checkDiag(String[][] grid, String c)
  {
	 int dim = grid.length;
   boolean answer = true;
   // Diagonal from left to right
	 for (int i = 0; i < dim; i++)
   {
    if (!grid[i][i].equals(c))
    {
      answer = false;
    }
	 }
   // Diagonal from right to left
   for(int i = 0; i < dim; i++)
   {
     if(!grid[i][dim - 1 - i].equals(c))
     {
       return false || answer;
     }
   }
   return true;
	}

  /**
	  * Helper function for checkwinner
    * Checks the possible vertical wins
	  * @param grid The String array representing the current board
	  * @return A boolean indicating if there is a vertical win
	  */
	public static boolean checkVert(String[][] grid)
  {
		int sum = 0;
		for (int column = 0; column < grid.length; column++)
    {
			String value = grid[0][column];
			for (int row = 0; row < grid.length; row++)
      {
				if (grid[row][column].equals(value))
        {
			 	 	sum++;
        }
			}
			if (sum == grid.length)
      {
				return true;
      }
			sum = 0;
		}
		return false;
	}

	/**
	 * Helper function for checkwinner
   * Checks the possible horizontal wins
	 * @param grid The String array representing the current board
	 * @return A boolean indicating if there is a horizontal win
	*/
	public static boolean checkHoriz(String[][] grid)
  {
		int sum = 0;
		for (int row = 0; row < grid.length; row++)
    {
			String value = grid[row][0];
			for (int column = 0; column < grid.length; column++)
      {
				if (grid[row][column].equals(value))
        {
			 	 	sum++;
        }
			}
			if (sum == grid.length)
      {
				return true;
      }
			sum = 0;
		}
		return false;
	}
}
