import java.util.ArrayList;

public class Rook extends GamePiece{
    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){ return rookMoves(friendPieces, foePieces);}
    public Rook(Point p){ super(p);}
    public String getID(){ return "R";}
    public int offSet(){return 8;}
}