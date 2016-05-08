import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessGame implements MouseListener {
    //Fields for the pieces, panel, and whether it's white's turn
    private static ArrayList<GamePiece> black, white, removedBlack, removedWhite;
    private static ChessPanel panel;
    private static boolean whiteTurn;

    //Returns the piece at a given point out of a given array list, returning null if none are found
    public static GamePiece findPiece(ArrayList<GamePiece> pieces, Point p){
        for(GamePiece piece: pieces)
            if(piece.getLocation().equals(p))
                return piece;
        return null;
    }

    //Returns king if the "friend" is in check, null otherwise
    public static GamePiece inCheck(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> enemyMoves = new ArrayList<>();
        for(GamePiece p: foePieces)
            if(p.getID().equals("K")) {//add simple king moves to avoid indirect recursion
                enemyMoves.add(new Point(p.getLocation()).incX(1));
                enemyMoves.add(new Point(p.getLocation()).incX(-1));
                enemyMoves.add(new Point(p.getLocation()).incY(1));
                enemyMoves.add(new Point(p.getLocation()).incY(-1));
                enemyMoves.add(new Point(p.getLocation()).incX(1).incY(1));
                enemyMoves.add(new Point(p.getLocation()).incX(1).incY(-1));
                enemyMoves.add(new Point(p.getLocation()).incX(-1).incY(1));
                enemyMoves.add(new Point(p.getLocation()).incX(-1).incY(-1));
            }
            else//add moves of all pieces to enemyMoves
                enemyMoves.addAll(p.getMoves(foePieces, friendPieces));

        //get king piece
        King k = new King(new Point(0,0));
        for(GamePiece p: friendPieces)
            if(p.getID().equals("K"))
                k = (King) p;

        //if any potential enemy moves kill the king (check), return the king
        for(Point pt: enemyMoves)
            if(pt.equals(k.getLocation()))
                return k;

        //otherwise, return null
        return null;
    }

    //return name of person in checkmate, null otherwise
    public static String checkmate(){
        //add all potential white moves to one list
        ArrayList<Point> moves = new ArrayList<>();
        for (GamePiece p : white)
            moves.addAll(trimMoves(p, white, black));
        //if there are no potential moves, white is in checkmate
        if (moves.size() == 0)
            return "White";



        //same for black
        moves = new ArrayList<>();
        for (GamePiece p : black)
            moves.addAll(trimMoves(p, black, white));
        if (moves.size() == 0)
            return "Black";

        //if none are in checkmate, return null
        return null;
    }

    //trim moves list to only moves that wouldn't put you in check
    private static ArrayList<Point> trimMoves(GamePiece p, ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces) {
        //get raw moves (before trimming)
        ArrayList<Point> moves = p.getMoves(friendPieces, foePieces);

        GamePiece temp;
        Point origLoc = p.getLocation();
        for (int i = 0; i < moves.size(); i++) {//move clicked piece to all possible locations
            //move clicked piece to location of move
            p.setLocation(moves.get(i));

            //if there is a foe at the potential move, save it in a temporary variable, and remove the foe
            int ind = foePieces.indexOf(findPiece(foePieces, moves.get(i)));
            temp = null;
            if (ind != -1)
                temp = foePieces.remove(ind);

            //if you are still in check, the move is illegal, and is removed from the list
            if (inCheck(friendPieces, foePieces) != null) {
                moves.remove(i);
                i--;
            }
            //if the foe was removed earlier, add it back
            if (ind != -1)
                foePieces.add(temp);
        }
        //reset the clicked piece to its original location after parsing move list
        p.setLocation(origLoc);

        //return all potential moves
        return moves;
    }

    //event on mousePress
    public void mousePressed(MouseEvent e){
        //get point on chess grid and active piece (highlighted)
        Point gridPoint = new Point(e.getX() / 50, (e.getY() - 25) / 50);
        GamePiece clicked = panel.activePiece();

        //define friend (moving) and foe lists
        ArrayList<GamePiece> friend, foe, foeRM;
        if(whiteTurn) {
            friend = white;
            foe = black;
            foeRM = removedBlack;
        }
        else{
            friend = black;
            foe = white;
            foeRM = removedWhite;
        }

        if(clicked == null){//if none are already clicked, select piece on grid
            clicked = findPiece(friend, gridPoint);

            if (clicked != null) {//if there is a piece at mouse click, activate the piece and set active moves to trimmed set of moves
                panel.activate(clicked);
                panel.setMoves(trimMoves(clicked, friend, foe));
            }
        }
        else{//if there is an active piece, get location and move
            //find if point clicked is on any legal moves
            boolean hasPoint = false;
            for(Point p: panel.getMoves())
                hasPoint = hasPoint || p.equals(gridPoint);

            //if move is legal
            if(hasPoint){
                //find the foe on that move (if any) and remove it
                for (int x = 0; x < foe.size(); x++)
                    if (foe.get(x).getLocation().equals(gridPoint))
                        foeRM.add(foe.remove(x));

                //move the clicked piece to the new location
                clicked.setLocation(gridPoint);

                //if the piece was a pawn moved to the edge, replace it with a queen
                if(clicked.getID().equals("P") && (clicked.getLocation().getY() == 0 || clicked.getLocation().getY() == 7)){
                    friend.remove(friend.indexOf(clicked));
                    friend.add(new Queen(clicked.getLocation()));
                }

                //give the turn to the other player
                whiteTurn = !whiteTurn;
            }
            //return board to a neutral (unhighlighted) state
            panel.deactivate();
        }

        //redraw board with new state after clicked
        panel.repaint();
    }

    //no other mouse events prompt any response
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}

    public static void main(String[] args){
        //make window
        JFrame frame = new JFrame("Ugly Chess");
        frame.setSize(new Dimension(610,425));

        //define lists of black/white pieces
        black = new ArrayList<>();
        white = new ArrayList<>();
        removedBlack = new ArrayList<>();
        removedWhite = new ArrayList<>();

        //add pawns
        for(int i = 0; i < 8; i++){
            black.add(new Pawn(new Point(i,1), false));
            white.add(new Pawn(new Point(i,6), true));
        }

        //instantiate black pieces
        black.add(new Rook(new Point(0,0)));
        black.add(new Rook(new Point(7,0)));
        black.add(new Knight(new Point(1,0)));
        black.add(new Knight(new Point(6,0)));
        black.add(new Bishop(new Point(2,0)));
        black.add(new Bishop(new Point(5,0)));
        black.add(new King(new Point(3,0)));
        black.add(new Queen(new Point(4,0)));

        //instantiate white pieces
        white.add(new Rook(new Point(0,7)));
        white.add(new Rook(new Point(7,7)));
        white.add(new Knight(new Point(1,7)));
        white.add(new Knight(new Point(6,7)));
        white.add(new Bishop(new Point(2,7)));
        white.add(new Bishop(new Point(5,7)));
        white.add(new King(new Point(3,7)));
        white.add(new Queen(new Point(4,7)));

        //instantiate panel, set settings for window
        panel = new ChessPanel(black, white, removedBlack, removedWhite);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //set to white's turn, add a mouseListener (this class implements it)
        whiteTurn = true;
        frame.addMouseListener(new ChessGame());
    }
}
