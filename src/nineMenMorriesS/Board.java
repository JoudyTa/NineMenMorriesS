package nineMenMorriesS;

import java.util.ArrayList;
import java.util.Arrays;

import nineMenMorriesS.MoveTo;

public class Board {
    char[][] board;
    int level = 4;
    // (person) is your opponent the min player, and (computer) is the computer the max player
    char computer = 'b', person = 'w';
    int computer_count, person_count = 0;


    // Constructors
    public Board(int lev) {
        // TODO Auto-generated constructor stub
        this.board = new char[7][7];
        for (char[] row : this.board)
            Arrays.fill(row, '_');

        this.board[3][3] = 'o';

        this.board[0][0] = 'b';
        this.board[1][1] = 'w';
        this.board[2][2] = '.';
        this.board[4][4] = '.';
        this.board[5][5] = 'w';
        this.board[6][6] = '.';

        this.board[0][6] = 'w';
        this.board[1][5] = 'b';
        this.board[2][4] = '.';
        this.board[4][2] = '.';
        this.board[5][1] = 'b';
        this.board[6][0] = '.';

        this.board[3][0] = 'w';
        this.board[3][1] = 'b';
        this.board[3][2] = 'b';
        this.board[3][4] = 'w';
        this.board[3][5] = 'b';
        this.board[3][6] = 'w';


        this.board[0][3] = 'b';
        this.board[1][3] = 'b';
        this.board[2][3] = 'w';
        this.board[4][3] = 'b';
        this.board[5][3] = 'w';
        this.board[6][3] = 'w';

        this.level = lev;
        this.computer_count = 9;
        this.person_count = 9;
    }


    // Player Functions
    public boolean contain_person_position(MoveTo pos) {
        return this.board[pos.row][pos.col] == 'w';
    }

    public boolean contain_computer_position(MoveTo pos) {
        return this.board[pos.row][pos.col] == 'b';
    }

    public boolean check_move(MoveTo oldPos, MoveTo newPos) {
        if ((oldPos.row != newPos.row) & (oldPos.col != newPos.col))
            return false;

        if (oldPos.row == newPos.row) {
            if (oldPos.col > newPos.col) {
                for (int j = oldPos.col - 1; j >= newPos.col; j--) {
                    if ((this.board[oldPos.row][j] != '_') & (j != newPos.col))
                        return false;
                    else if (this.board[oldPos.row][j] == 'o')
                        return false;
                    else if ((this.board[oldPos.row][j] != '_') & (j == newPos.col))
                        return true;
                }
            } else if (oldPos.col < newPos.col) {
                for (int j = oldPos.col + 1; j <= newPos.col; j++) {
                    if ((this.board[oldPos.row][j] != '_') & (j != newPos.col))
                        return false;
                    else if (this.board[oldPos.row][j] == 'o')
                        return false;
                    else if ((this.board[oldPos.row][j] != '_') & (j == newPos.col))
                        return true;
                }
            }
        }

        if (oldPos.col == newPos.col) {
            if (oldPos.row > newPos.row) {
                for (int i = oldPos.row - 1; i >= newPos.row; i--) {
                    if ((this.board[i][oldPos.col] != '_') & (i != newPos.row))
                        return false;
                    else if (this.board[i][oldPos.col] == 'o')
                        return false;
                    else if ((this.board[i][oldPos.col] != '_') & (i == newPos.row))
                        return true;
                }
            } else if (oldPos.row < newPos.row) {
                for (int i = oldPos.row + 1; i <= newPos.row; i++) {
                    if ((this.board[i][oldPos.col] != '_') & (i != newPos.row))
                        return false;
                    else if (this.board[i][oldPos.col] == 'o')
                        return false;
                    else if ((this.board[i][oldPos.col] != '_') & (i == newPos.row))
                        return true;
                }
            }
        }

        return false;
    }
    //

    //	Move from position to another
    public void slide(MoveTo oldPos, MoveTo newPos, char value) {
        this.board[oldPos.row][oldPos.col] = '.';
        this.board[newPos.row][newPos.col] = value;
    }

    //	Undo your move
    public void trace_slide(MoveTo oldPos, MoveTo newPos, char value) {
        this.board[oldPos.row][oldPos.col] = value;
        this.board[newPos.row][newPos.col] = '.';
    }

    //	Check if there is three players on the same row or column
    public boolean check_three_in_one(MoveTo pos) {
        char value = this.board[pos.row][pos.col];
        int count = 1;
        //	Check rows
        for (int i = pos.row + 1; i < this.board.length; i++) {
            if ((this.board[i][pos.col] != '_') & (this.board[i][pos.col] != value))
                break;
            else if ((this.board[i][pos.col] == value))
                count++;
        }
        if (count == 3)
            return true;
        for (int i = pos.row - 1; i >= 0; i--) {
            if ((this.board[i][pos.col] != '_') & (this.board[i][pos.col] != value))
                break;
            else if ((this.board[i][pos.col] == value))
                count++;
        }
        if (count == 3)
            return true;

        //	Check columns
        count = 1;
        for (int j = pos.col + 1; j < this.board[0].length; j++) {
            if ((this.board[pos.row][j] != '_') & (this.board[pos.row][j] != value))
                break;
            else if ((this.board[pos.row][j] == value))
                count++;
        }
        if (count == 3)
            return true;
        for (int j = pos.col - 1; j >= 0; j--) {
            if ((this.board[pos.row][j] != '_') & (this.board[pos.row][j] != value))
                break;
            else if ((this.board[pos.row][j] == value))
                count++;
        }
        return count == 3;
    }

    //	Choose a random position for your opponent to remove him later from this position
    MoveTo random(char value) {
        MoveTo remove_position = new MoveTo();
        while (true) {
            remove_position.row = (int) ((Math.random() * (this.board.length)));
            remove_position.col = (int) ((Math.random() * (this.board.length)));
            if (this.board[remove_position.row][remove_position.col] == value) {
                break;
            }
        }
        return remove_position;
    }

    //	Delete player from certain position
    public void remove_from_position(MoveTo pos) {
        if (this.board[pos.row][pos.col] == 'b')
            this.computer_count--;
        else
            this.person_count--;

        this.board[pos.row][pos.col] = '.';

    }

    //	Undo delete the move
    public void trace_remove_from_position(MoveTo pos, char value) {
        if (value == 'b')
            this.computer_count++;
        else
            this.person_count++;

        this.board[pos.row][pos.col] = value;

    }


    //	Check if there is no more moves that can be done to a certain player, value could be (b) or (w)
    public boolean no_more_moves(char value) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] == value) {

                    // Check its column
                    for (int l = j + 1; l < this.board[0].length; l++) {
                        if ((this.board[i][l] != '_') & (this.board[i][l] != '.'))
                            break;
                        else if (this.board[i][l] == '.')
                            return false;
                    }
                    for (int l = j - 1; l > 0; l--) {
                        if ((this.board[i][l] != '_') & (this.board[i][l] != '.'))
                            break;
                        else if (this.board[i][l] == '.')
                            return false;
                    }
                    // Check its rows
                    for (int l = i + 1; l < this.board.length; l++) {
                        if ((this.board[l][j] != '_') & (this.board[l][j] != '.'))
                            break;
                        else if (this.board[l][j] == '.')
                            return false;
                    }
                    for (int l = i - 1; l > 0; l--) {
                        if ((this.board[l][j] != '_') & (this.board[l][j] != '.'))
                            break;
                        else if (this.board[l][j] == '.')
                            return false;
                    }
                }
            }
        }
        return true;
    }

    //	Get all possible moves from certain position
    public ArrayList<MoveTo> all_possible_moves(MoveTo pos) {
        MoveTo possible_move = new MoveTo();
        ArrayList<MoveTo> possible_moves = new ArrayList<MoveTo>();

        for (int l = pos.col + 1; l < this.board[0].length; l++) {
            if ((this.board[pos.row][l] != '_') & (this.board[pos.row][l] != '.'))
                break;
            else if (this.board[pos.row][l] == '.') {
                possible_move.col = l;
                possible_move.row = pos.row;
                possible_moves.add(possible_move);
                break;
            }


        }
        for (int l = pos.col - 1; l > 0; l--) {

            if ((this.board[pos.row][l] != '_') & (this.board[pos.row][l] != '.'))
                break;
            else if (this.board[pos.row][l] == '.') {
                possible_move.col = l;
                possible_move.row = pos.row;
                possible_moves.add(possible_move);
                break;
            }
        }
        // Check its rows
        for (int l = pos.row + 1; l < this.board.length; l++) {
            if ((this.board[l][pos.col] != '_') & (this.board[l][pos.col] != '.'))
                break;
            else if (this.board[l][pos.col] == '.') {
                possible_move.col = pos.col;
                possible_move.row = l;
                possible_moves.add(possible_move);
                break;
            }
        }
        for (int l = pos.row - 1; l > 0; l--) {
            if ((this.board[l][pos.col] != '_') & (this.board[l][pos.col] != '.'))
                break;
            else if (this.board[l][pos.col] == '.') {
                possible_move.col = pos.col;
                possible_move.row = l;
                possible_moves.add(possible_move);
                break;
            }
        }
        return possible_moves;
    }


    //	Check if there is a winner
    int check_winner() {

        if (person_count <= 2) {
            return 1;
        } else {
            if (no_more_moves('w')) {
                return 1;
            }

        }

        if (computer_count <= 2) {
            return -1;
        } else {
            if (no_more_moves('b')) {
                return -1;
            }
        }
        return 0;
    }

    //	Evaluate the current position
    int evaluation() {
        int eval = 0;
        MoveTo moveTo = new MoveTo();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == this.computer) {
                    moveTo.row = i;
                    moveTo.col = j;
                    if (check_three_in_one(moveTo)) {
                       i++;
                        eval += 100;
                    }
                    if (computer_count > 2 + (person_count / 2))
                        eval += 50;

                } else if (this.board[i][j] == this.person) {
                    moveTo.row = i;
                    moveTo.col = j;
                    if (check_three_in_one(moveTo)) {

                        eval -= 100;
                    }
                    if (computer_count < 2 + (person_count / 2))
                        eval -= 50;
                }
            }
        }

        return eval;
    }

    int AlphaBeta(int depth, boolean isMax, int alpha, int beta) {
        MoveTo oldPosition = new MoveTo();
        MoveTo removed_position = new MoveTo();
        ArrayList<MoveTo> possible_moves = new ArrayList<MoveTo>();

        // If the game is over, or we reached a certain depth
        if (depth == this.level || check_winner() != 0) {
            return this.evaluation();
        }


        // If this maximizer's move
        if (isMax) {
            int best = -10000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board.length; j++) {
                    if (board[i][j] == 'b') {
                        oldPosition.row = i;
                        oldPosition.col = j;
                        possible_moves = all_possible_moves(oldPosition);
                        for (int k = 0; k < possible_moves.size(); k++) {
                            boolean isVisited = false;
                            slide(oldPosition, possible_moves.get(k), 'b');
                            if (check_three_in_one(possible_moves.get(k))) {
                                removed_position = random('w');
                                remove_from_position(removed_position);
                                isVisited = true;
                            }
                            best = Math.max(best, AlphaBeta(depth + 1, false, alpha, beta));

                            trace_slide(oldPosition, possible_moves.get(k), 'b');
                            if (isVisited)
                                trace_remove_from_position(removed_position, 'w');
                            alpha = Math.max(best, alpha);
                            if (alpha >= beta)
                                break;
                        }
                    }
                }
            }

            return best;
        }

        // If this minimizer's move
        else {
            int best = 10000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board.length; j++) {
                    if (board[i][j] == 'w') {
                        oldPosition.row = i;
                        oldPosition.col = j;
                        possible_moves = all_possible_moves(oldPosition);
                        for (int k = 0; k < possible_moves.size(); k++) {
                            boolean isVisited = false;
                            slide(oldPosition, possible_moves.get(k), 'w');
                            if (check_three_in_one(possible_moves.get(k))) {
                                removed_position = random('b');
                                remove_from_position(removed_position);
                                isVisited = true;
                            }
                            best = Math.min(best, AlphaBeta(depth + 1, true, alpha, beta));

                            trace_slide(oldPosition, possible_moves.get(k), 'w');
                            if (isVisited)
                                trace_remove_from_position(removed_position, 'b');
                            beta = Math.min(best, beta);
                            if (alpha >= best)
                                break;
                        }
                    }
                }
            }

            return best;
        }
    }

    //	This is the minimax function. It considers all the possible ways the game can go and returns the value of the board at certain depth
    int minimax(int depth, boolean isMax) {
        MoveTo oldPosition = new MoveTo();
        MoveTo removed_position = new MoveTo();
        ArrayList<MoveTo> possible_moves = new ArrayList<MoveTo>();


        // If the game is over, or we reached a certain depth
        if (depth == this.level || check_winner() != 0) {
            return this.evaluation();
        }


        // If this maximizer's move
        if (isMax) {
            int best = -10000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board.length; j++) {
                    if (board[i][j] == 'b') {
                        oldPosition.row = i;
                        oldPosition.col = j;
                        possible_moves = all_possible_moves(oldPosition);
                        for (int k = 0; k < possible_moves.size(); k++) {
                            boolean isVisited = false;
                            slide(oldPosition, possible_moves.get(k), 'b');
                            if (check_three_in_one(possible_moves.get(k))) {
                                removed_position = random('w');
                                remove_from_position(removed_position);
                                isVisited = true;
                            }

                            best = Math.max(best, minimax(depth + 1, false));

                            trace_slide(oldPosition, possible_moves.get(k), 'b');
                            if (isVisited)
                                trace_remove_from_position(removed_position, 'w');
                        }
                    }
                }
            }

            return best;
        }

        // If this minimizer's move
        else {
            int best = 10000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board.length; j++) {
                    if (board[i][j] == 'w') {
                        oldPosition.row = i;
                        oldPosition.col = j;
                        possible_moves = all_possible_moves(oldPosition);
                        for (int k = 0; k < possible_moves.size(); k++) {
                            boolean isVisited = false;
                            slide(oldPosition, possible_moves.get(k), 'w');
                            if (check_three_in_one(possible_moves.get(k))) {
                                removed_position = random('b');
                                remove_from_position(removed_position);
                                isVisited = true;
                            }

                            best = Math.min(best, minimax(depth + 1, true));

                            trace_slide(oldPosition, possible_moves.get(k), 'w');
                            if (isVisited)
                                trace_remove_from_position(removed_position, 'b');
                        }
                    }
                }
            }

            return best;
        }
    }

    // optimal move for computer
    ArrayList<MoveTo> optimalMove() {
        int bestVal = -10000;
        int moveVal = 0;
        MoveTo bestMove = new MoveTo();
        MoveTo firstMove = new MoveTo();
        MoveTo oldPosition = new MoveTo();
        MoveTo removed_position = new MoveTo();
        ArrayList<MoveTo> possible_moves = new ArrayList<MoveTo>();
        ArrayList<MoveTo> best_moves = new ArrayList<MoveTo>();


        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == this.computer) {
                    oldPosition.row = i;
                    oldPosition.col = j;
                    possible_moves = this.all_possible_moves(oldPosition);
                    for (MoveTo pos : possible_moves) {
                        boolean isVisited = false;
                        slide(oldPosition, pos, 'b');
                        if (check_three_in_one(pos)) {
                            removed_position = random('b');
                            remove_from_position(removed_position);
                            isVisited = true;
                        }
                        moveVal = minimax(0, false);

                        if (moveVal > bestVal) {
                            firstMove.row = i;
                            firstMove.col = j;
                            bestMove.row = pos.row;
                            bestMove.col = pos.col;
                            bestVal = moveVal;

                        }
                        trace_slide(oldPosition, pos, 'b');
                        if (isVisited)
                            trace_remove_from_position(removed_position, 'b');
                    }
                }
            }
        }
        best_moves.add(firstMove);
        best_moves.add(bestMove);
        return best_moves;
    }

    // where the computer will start searching for its optimal move
    void computerPlay() {
        //	Get optimal move
        ArrayList<MoveTo> best_moves = this.optimalMove();
        MoveTo removed_position = new MoveTo();

        //	Print the optimal move
        System.out.println("_____Computer Turn______");
        System.out.println();
        System.out.print("Player : (");
        System.out.print(best_moves.get(0).row);
        System.out.print(", ");
        System.out.print(best_moves.get(0).col);
        System.out.print(")");

        System.out.print(" Moved To : (");
        System.out.print(best_moves.get(1).row);
        System.out.print(" , ");
        System.out.print(best_moves.get(1).col);
        System.out.println(")");
        System.out.println();

        //	Slide to the position of the optimal move
        this.slide(best_moves.get(0), best_moves.get(1), this.computer);
        // IF this move makes three in one row or column then we will remove one of the opponent players
        if (this.check_three_in_one(best_moves.get(1))) {
            removed_position = this.random(this.person);
            this.remove_from_position(removed_position);
            //	Print the player that has been removed
            System.out.print("Player : (");
            System.out.print(removed_position.row);
            System.out.print(" , ");
            System.out.print(removed_position.col);
            System.out.println(") Has been removed");
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.board.length; i++) {
            if (i == 0) {
                sb.append("   ");
                for (int j = 0; j < this.board[0].length; j++) {
                    sb.append(j);
                    sb.append("   ");
                }
                sb.append('\n');
            }
            sb.append(i);
            sb.append("  ");
            for (int j = 0; j < this.board[0].length; j++) {
                sb.append(this.board[i][j]);
                sb.append(" | ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
}
