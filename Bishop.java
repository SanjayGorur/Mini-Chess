import java.util.ArrayList;

public class Bishop extends GamePiece{
    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){ return bishopMoves(friendPieces, foePieces);}
    public Bishop(Point p){ super(p);}
    public String getID(){ return "B";}
    public int offSet(){return 8;}
}