import java.util.ArrayList;

public class Knight extends GamePiece{
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
    //constructor and ID definition
    public Knight(Point p){ super(p);}
    public String getID(){ return "N";}
    public int offSet(){return 7;}
}