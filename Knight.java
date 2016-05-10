import java.util.ArrayList;

public class Knight extends GamePiece{
    private int xInc, yInc;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //add all potential moves (l-shapes)
        addMove(moves, new Point(getLocation()).incX(2).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(2).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-2).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-2).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(2), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(-2), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(2), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(-2), friendPieces);

        return moves;
    }
    //constructor and ID definition (if |x|==2, |y|==1, and vice versa, randomizing - versus +, causing uneven diagonal travel slightly faster than bishop
    public Knight(Point p){
        super(p);

        switch((int) (Math.random()*4)){
            case 0:
                xInc = -1;
                break;
            case 1:
                xInc = -2;
                break;
            case 2:
                xInc = 2;
                break;
            case 3:
                xInc = 1;
                break;
        }

        if(Math.abs(xInc)==1)
            yInc=2;
        else
            yInc=1;

        if(Math.random()>.5)
            yInc*=-1;
    }
    //various class methods for class identity (Pawn size) (~1.5x pawn speed)
    public String getID(){ return "N";}
    public int offSet(){return 7;}
    public void move(){getLocation().incX(xInc).incY(yInc);}
    public int getWidth(){return 10;}
    public int getHeight(){return 10;}
}