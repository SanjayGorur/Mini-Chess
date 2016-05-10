import java.util.ArrayList;

public class Rook extends GamePiece{
    private int direction;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){ return rookMoves(friendPieces, foePieces);}
    //constructor; randomizes direction as either left or right
    public Rook(Point p) {
        super(p);
        if (Math.random() > .5)
            direction = 2;
        else
            direction = -2;
    }
    //various class methods for class identity (4x pawn height, pawn width) (2x pawn speed horizontally)
    public String getID(){ return "R";}
    public int offSet(){return 8;}
    public void move(){getLocation().incX(direction);}
    public int getWidth(){return 10;}
    public int getHeight(){return 40;}
}