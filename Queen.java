import java.util.ArrayList;

public class Queen extends GamePiece{
    //returns combined list of all rook or bishop moves
    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = rookMoves(friendPieces, foePieces);
        moves.addAll(bishopMoves(friendPieces, foePieces));
        return moves;
    }
    //constructor and ID definition
    public Queen(Point p){ super(p);}
    public String getID(){ return "Q";}
    public int offSet(){return 6;}
}