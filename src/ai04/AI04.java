package ai04;

import java.util.Scanner;

/**
 *
 * @author macandcheese
 */
public class AI04 {

    private static boolean playAgain = true;
    
    public static void main(String[] args){
        ticTacToe ttt;
        
        while(playAgain) {
            playAgain = false;
            message();
            ttt = new ticTacToe();
        }
    }
    
    public static void message() {
        System.out.println("What game would you like to play?");
        System.out.println();
        System.out.println(" 1) Tic-tac-toe.");
        System.out.println(" 2) Global Thermal Nuclear War.");
        System.out.print("Choice: ");
        
        Scanner s = new Scanner(System.in);
        String input = s.next();
        while(!input.equals("1") && !input.equals("2")) {
            System.out.println("That is not a choice. Select again.");
            System.out.print("Choice: ");
            input = s.next();
        }
        
        if(input.equals("1")) {
            return;
        }
        else if (input.equals("2")) {
            System.out.println("Sorry, I cannot allow that.");
            return;
        }
    }
    
    static class ticTacToe {
        
        boolean winner = false;
        int[] board = new int[9];
        int movesLeft = 8;
        int aimove = 0;
        
        ticTacToe() {
            System.out.println("Lets play Tic-tac-toe.");
            System.out.println();
            startGame();
        }
        
        void startGame() {
            int player;
            while(!winner) {
                printBoard();
                player = 1;
                playerTurn();
                checkForWin(player);
                if(winner) {
                    return;
                }
                player = 2;
                aiTurn();
                checkForWin(player);
            }
        }
        
        void playAgain() {
            System.out.println("Would you like to play again?");System.out.println();
            System.out.println(" 1) Yes");
            System.out.println(" 2) No");
            System.out.print("Choice: ");

            Scanner s = new Scanner(System.in);
            String input = s.next();
            while(!input.equals("1") && !input.equals("2")) {
                System.out.println("That is not a choice. Select again.");
                System.out.print("Choice: ");
                input = s.next();
            }

            if(input.equals("1")) {
                AI04.playAgain = true;
            }
        }
        
        void checkForWin(int player) {
            if(board[0] == player && board[1] == player && board[2] == player || 
               board[3] == player && board[4] == player && board[5] == player || 
               board[6] == player && board[7] == player && board[8] == player) {
                if(player == 1) {
                    System.out.println("You win!");
                }
                else {
                    System.out.println("AI Wins!");
                }
                playAgain();
                winner = true;
            }
            else if(board[0] == player && board[3] == player && board[6] == player || 
                    board[1] == player && board[4] == player && board[7] == player || 
                    board[2] == player && board[5] == player && board[8] == player) {
                if(player == 1) {
                    System.out.println("You win!");
                }
                else {
                    System.out.println("AI Wins!");
                }
                playAgain();
                winner = true;
            }
            else if(board[0] == player && board[4] == player && board[8] == player || 
               board[2] == player && board[4] == player && board[6] == player) {
                if(player == 1) {
                    System.out.println("You win!");
                }
                else {
                    System.out.println("AI Wins!");
                }
                playAgain();
                winner = true;
            }
            else if(board[0] != 0 && board[1] != 0 && board[2] != 0 &&
                    board[3] != 0 && board[4] != 0 && board[5] != 0 &&
                    board[6] != 0 && board[7] != 0 && board[8] != 0) {
                System.out.println("Draw!");
                playAgain();
                winner = true;
            }
        }
        
        void aiTurn() {
            minimax(board, movesLeft, true);
            board[aimove] = 2;
            if(movesLeft != 0)
                movesLeft -= 2;
        }
        
        boolean emptySpaces(int[] a) {
            for(int i = 0; i < a.length; i ++) {
                if(a[i] == 0) {
                    return true;
                }
            }
            return false;
        }
        
        boolean isWinner(int[] node) {
            int player = 0;
            for(int i = 0; i < 2; i++) {
                player++;
                if(node[0] == player && node[1] == player && node[2] == player || 
                   node[3] == player && node[4] == player && node[5] == player || 
                   node[6] == player && node[7] == player && node[8] == player) {
                    return true;
                }
                else if(node[0] == player && node[3] == player && node[6] == player || 
                        node[1] == player && node[4] == player && node[7] == player || 
                        node[2] == player && node[5] == player && node[8] == player) {
                    return true;
                }
                else if(node[0] == player && node[4] == player && node[8] == player || 
                   node[2] == player && node[4] == player && node[6] == player) {
                    return true;
                }
            }
            return false;
        }
        
        int minimax(int[] inBoard, int depth, boolean maxPlayer) {
            
            int[] node = inBoard.clone();
            int score;
            int bestMove = 0;
            
            if(depth == 0 || !emptySpaces(node) || isWinner(node)) {
                score = heuristic(node);
                return score;
            }
            
            if(maxPlayer) {
                int bestValue = -9999;
                for(int i = 0; i < node.length; i++) {
                    if(node[i] == 0) {
                        node[i] = 2;
                        score = minimax(node,depth-1,false);
                        node[i] = 0;
                        if(score > bestValue) {
                            bestValue = score;
                            bestMove = i;
                        }
                    }
                }
                aimove = bestMove;
                return bestValue;
            }
            else {
                int bestValue = 9999;
                for(int i = 0; i < node.length; i++) {
                    if(node[i] == 0) {
                        node[i] = 1;
                        score = minimax(node,depth-1,true);
                        node[i] = 0;
                        if(score < bestValue) {
                            bestValue = score;
                        }
                    }
                }
                return bestValue;
            }
        }
        
        private int heuristic(int[] node) {
            int heuristic = 0;
            
            heuristic += scoreLine(node[0],node[1],node[2]);
            heuristic += scoreLine(node[3],node[4],node[5]);
            heuristic += scoreLine(node[6],node[7],node[8]);
            heuristic += scoreLine(node[0],node[3],node[6]);
            heuristic += scoreLine(node[1],node[4],node[7]);
            heuristic += scoreLine(node[2],node[5],node[8]);
            heuristic += scoreLine(node[0],node[4],node[8]);
            heuristic += scoreLine(node[2],node[4],node[6]);
            
            return heuristic;
        }
        
        private int scoreLine(int a, int b, int c) {
            int score = 0;
            
            switch (a) {
                case 0:
                    if(b == 0) {
                        if(c == 1) {
                            score = -1;
                        }
                        else if(c == 2) {
                            score = 1;
                        }
                    }
                    else if(b == 1) {
                        if(c == 1) {
                            score = -10;
                        }
                        else if(c == 0) {
                            score = -1;
                        }
                    }
                    else {
                        if(c == 0) {
                            score = 1;
                        }
                        else if(c == 2) {
                            score = 10;
                        }
                    }   break;
                case 1:
                    if(b == 0) {
                        if(c == 0) {
                            score = -1;
                        }
                        else if(c == 1) {
                            score = -10;
                        }
                    }
                    else if(b == 1) {
                        if(c == 0) {
                            score = -10;
                        }
                        else if(c == 1) {
                            score = -100;
                        }
                    }   break;
                default:
                    if(b == 0) {
                        if(c == 0) {
                            score = 1;
                        }
                        else if(c == 2) {
                            score = 10;
                        }
                    }
                    else if(b == 2) {
                        if(c == 0) {
                            score = 10;
                        }
                        else if(c == 2) {
                            score = 100;
                        }
                    }   break;
            }
            
            return score;
        }
        
        void playerTurn() {
            System.out.print("Your move: ");
            Scanner s = new Scanner(System.in);
            String move = s.next();
            while(!isInteger(move) || Integer.parseInt(move) < 1 || Integer.parseInt(move) > 9 || !validMove(Integer.parseInt(move))) {
                System.out.println("Not a valid move. Try again.");
                System.out.print("Your move: ");
                move = s.next();
            }
            board[Integer.parseInt(move)-1] = 1;
        }
        
        boolean validMove(int a) {
            if(board[a-1] == 0) {
                return true;
            } 
            return false;
        }
        
        boolean isInteger(String s) {
            try {
                Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                return false;
            }
            catch (NullPointerException e) {
                return false;
            }
            return true;
        }
        
        void printBoard() {
            for(int i = 0; i < board.length; i++) {
                System.out.print(" ");
                switch(board[i]) {
                    case 0: System.out.print(i + 1);
                            break;
                    case 1: System.out.print("X");
                            break;
                    case 2: System.out.print("O");
                            break;
                    default: System.out.println("ERROR");
                }
                System.out.print(" ");
                if((i+1) % 3 != 0) {
                    System.out.print("|");
                }
                else {
                    System.out.println();
                    if((i+1) != board.length) {
                        System.out.println("---+---+---");
                    }
                }
            }
        }
    }
}
