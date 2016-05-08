import java.util.ArrayList;

public class King extends GamePiece{
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
    public King(Point p){ super(p);}
    public String getID(){ return "K";}
    public int offSet(){return 7;}
}