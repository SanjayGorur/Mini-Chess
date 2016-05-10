import java.util.ArrayList;

public class Queen extends GamePiece{
    private int xInc, yInc;

    //returns combined list of all rook or bishop moves
    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = rookMoves(friendPieces, foePieces);
        moves.addAll(bishopMoves(friendPieces, foePieces));
        return moves;
    }
    //constructor and ID definition
    public Queen(Point p) {
        super(p);
        randomXY();
    }
    //various class methods for class identity (4x pawn width and height) (Erratically jumps between horizontal/vertical speeds from 0 to 2, changing on average every second)
    public String getID(){ return "Q";}
    public int offSet(){return 6;}
    public void move(){
        if((int) (Math.random()*60)==0)
            randomXY();

        getLocation().incX(xInc).incY(yInc);
    }
    //randomizes direction
    private void randomXY(){
        xInc = (int) (Math.random()*3);
        yInc = (int) (Math.random()*3);

        if(Math.random()>.5)
            xInc*=-1;
        if(Math.random()>.5)
            yInc*=.1;
    }
    public int getWidth(){return 40;}
    public int getHeight(){return 40;}
}