import java.util.ArrayList;

public class King extends GamePiece{
    private int incX, incY;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //add all potential moves (adjacent)
        addMove(moves, new Point(getLocation()).incX(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incY(1), friendPieces);

        return moves;
    }
    //constructor and ID definition
    public King(Point p){
        super(p);
        incX = incY = 0;
    }
    //various methods for class identity, as well as methods to set increment (velocity) horizontally and vertically
    public String getID(){ return "K";}
    public int offSet(){return 7;}
    public void move(){getLocation().incX(incX).incY(incY);}
    public void setIncX(int x){incX = x;}
    public void setIncY(int y){incY = y;}
    public int getWidth(){return 10;}
    public int getHeight(){return 10;}
}