import java.util.ArrayList;

public class Pawn extends GamePiece{
    //boolean for whether the pawn is white (defines direction of movement)
    private boolean white;
    private int direction;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //define direction of movement
        int inc;
        if(white)
            inc = -1;
        else
            inc = 1;

        //add point directly in front if empty, 2 in front if in original position and empty
        Point testmv = new Point(getLocation()).incY(inc);
        if (isEmpty(friendPieces, foePieces, testmv)){
            moves.add(new Point(testmv));
            if(((!white && getLocation().getY()==1) || (white && getLocation().getY()==6)) && isEmpty(friendPieces, foePieces, testmv.incY(inc)))
                moves.add(new Point(testmv));
        }

        //add diagonals if enemies are found there
        testmv = new Point(getLocation()).incY(inc).incX(1);
        if (ChessGame.findPiece(foePieces, testmv) != null)
            moves.add(new Point(testmv));

        if (ChessGame.findPiece(foePieces, testmv.incX(-2)) != null)
            moves.add(new Point(testmv));

        return moves;
    }
    //constructor (moves either up or down)
    public Pawn(Point p, boolean wPiece){
        super(p);
        white = wPiece;
        if(Math.random()>.5)
            direction=1;
        else
            direction=-1;
    }
    //various class methods for class identity (King size) (~1.5x king speed)
    public String getID(){ return "P";}
    public int offSet(){return 9;}
    public void move(){getLocation().incY(direction);}
    public int getWidth(){return 10;}
    public int getHeight(){return 10;}
}