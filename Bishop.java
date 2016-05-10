import java.util.ArrayList;
public class Bishop extends GamePiece{
    private int xInc, yInc;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){ return bishopMoves(friendPieces, foePieces);}
    //constructor, sets location and randomises direction in x and y directions (|x|==|y| meaning 45 degree diagonal travel)
    public Bishop(Point p){
        super(p);

        if(Math.random()>.5)
            xInc=1;
        else
            xInc=-1;

        if(Math.random()>.5)
            yInc=1;
        else
            yInc=-1;
    }
    //various methods for class identity (2x pawn width/height) (1x pawn speed in X and Y)
    public String getID(){ return "B";}
    public int offSet(){return 8;}
    public void move(){getLocation().incX(xInc).incY(yInc);}
    public int getWidth(){return 20;}
    public int getHeight(){return 20;}
}