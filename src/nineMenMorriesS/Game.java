package nineMenMorriesS;

import java.util.Scanner;

public class Game {
	// Creating the playing Board
	Board game_board;

	Scanner scan = new Scanner(System.in);

	public Game() {
		// TODO Auto-generated constructor stub
		System.out.println("Choose the game level: 1: easy, 2: medium; 3: hard");
		int level = scan.nextInt();
		if(level == 1)
			this.game_board = new Board(4);
		else if(level == 2)
			this.game_board = new Board(6);
		else
			this.game_board = new Board(8);



	}
	
	// Deals with your moves from the keyboard
	private void personTurn() {
        
		MoveTo old_position = new MoveTo();
        MoveTo position = new MoveTo();
        // Enter player
        while (true) {
            System.out.print("Enter the position of the player you want to move: ");
            System.out.println();
            System.out.print("row: ");
            old_position.row = scan.nextInt();
            System.out.print("column: ");
            old_position.col = scan.nextInt();
            System.out.println();
            if(this.game_board.contain_person_position(old_position))
            	break;
            else
            	 System.out.println("Wrong Position, Please try again");

        }
        // Enter Row
        System.out.print("Enter the position you want to move to ");
        System.out.println();
        while (true) {
            System.out.print("row: ");
            position.row = scan.nextInt();
            if ((position.row >= 0) && (position.row < this.game_board.board.length)) {
                break;
            }
        }
        // Enter Column
        while (true) {
            System.out.print("column: ");
            position.col = scan.nextInt();
            System.out.println();
            if ((position.col >= 0) && (position.col <  this.game_board.board[0].length)) {
                break;
            }
        }
        // Place the player element inside its position if possible
        if (!this.game_board.check_move(old_position, position)) {
        	System.out.println("cannot place it here .. please try a gain");
        	personTurn();	
        }
        else {
        	this.game_board.slide(old_position, position, this.game_board.person);
        	//  If the new move makes three in one row or column, you get the chance to remove one of your opponent players
        	if(this.game_board.check_three_in_one(position)) {
        		while(true) {
	        		System.out.println("Three in one!!! Enter the location of your opponent that you want to remove");
	        		 while (true) {
	        	         System.out.print("row: ");
	        	         position.row = scan.nextInt();
	        	         if ((position.row >= 0) && (position.row < this.game_board.board.length)) {
	        	             break;
	        	         }
	        	     }
	        	     // Enter Column
	        	     while (true) {
	        	         System.out.print("column: ");
	        	         position.col = scan.nextInt();
	        	         System.out.println();
	        	         if ((position.col >= 0) && (position.col <  this.game_board.board[0].length)) {
	        	             break;
	        	         }
	        	     }
	        	     if(this.game_board.contain_computer_position(position)) {
	        	    	 this.game_board.remove_from_position(position);
	        	    	 break; 
	        	     }
        		}
        	}
        }		
    }
	
	// Deals with your computer's moves #### Your Code ###
	private void computerTurn() {
		this.game_board.computerPlay();
    }
	
	// Call both personTurn and computerTurn functions continuously
	public void play() {
        System.out.println(this.game_board);
        while (true) {
        	personTurn();
        	System.out.println("________Player Turn_________");
        	System.out.println();
            System.out.println(this.game_board);
            int finished = this.game_board.check_winner();
            
            // If the game is over
            if (finished != 0) {
            	// Check the winner
            	if(finished == -1)
            		System.out.println("I had just so bad luck, let's try again >_<!");
            	else if(finished == 1)
            		System.out.println("And the winner is... ME! yeah -O-");
            	break;	
            }
            
            // starting time 
            long start = System.currentTimeMillis(); 
            computerTurn();
            // ending time 
            long end = System.currentTimeMillis(); 
            
            // System.out.println("_____Computer Turn______");
            System.out.println(this.game_board);
            System.out.println("Time taken = " + (end - start) + "ms"); 
            finished = this.game_board.check_winner();
            if (finished != 0) {
            	if(finished == -1)
            		System.out.println("I had just so bad luck, let's try again >_<!");
            	else if(finished == 1)
            		System.out.println("And the winner is... ME! yeah -O-");
            	break;	
            }
        }
    }

	 
	// Start the game 
	public static void main(String[] args) 
	{		
		Game game = new Game();
		game.play();
	} 

}


