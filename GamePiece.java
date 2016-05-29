import java.util.ArrayList;

public abstract class GamePiece {
    //location of piece and time of capture
    private Point p;
    private int time, width, height;

    //constructor, location accessor/mutator methods
    public GamePiece(Point initp){
        p = initp;
        time = -1;
    }
    public Point getLocation(){ return p;}
    public void setLocation(Point newP){ p = newP;}

    //per-piece methods for the potential moves (to be parsed later for potential checks), the letter-ID of the piece (displayed on piece), and the offset (for centering ID)
    public abstract ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces);
    public abstract String getID();
    public abstract int offSet();

    //returns all possible rook-style moves
    public ArrayList<Point> rookMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //add potential unobstructed moves within the bounds of the board in increasing x values, adding location of obstruction only if it's an enemy
        Point loc = new Point(getLocation()).incX(1);
        while(loc.getX() <= 7 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with decreasing x
        loc = new Point(getLocation()).incX(-1);
        while(loc.getX() >= 0 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(-1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with increasing y
        loc = new Point(getLocation()).incY(1);
        while(loc.getY() <= 7 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incY(1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with decreasing y
        loc = new Point(getLocation()).incY(-1);
        while(loc.getY() >= 0 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incY(-1);
        }
        addIfFoe(moves, foePieces, loc);


        return moves;
    }

    //returns all possible bishop-style moves
    public ArrayList<Point> bishopMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //same as rook but with increasing x and y
        Point loc = new Point(getLocation()).incX(1).incY(1);
        while(loc.getX() <= 7 && loc.getY() <= 7 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(1).incY(1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with dec x, y
        loc = new Point(getLocation()).incX(-1).incY(-1);
        while(loc.getX() >= 0 && loc.getY() >= 0 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(-1).incY(-1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with inc x, dec y
        loc = new Point(getLocation()).incX(1).incY(-1);
        while(loc.getX() <= 7 && loc.getY() >= 0 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(1).incY(-1);
        }
        addIfFoe(moves, foePieces, loc);


        //same with dec x, inc y
        loc = new Point(getLocation()).incX(-1).incY(1);
        while(loc.getX() >= 0 && loc.getY() <= 7 && isEmpty(friendPieces, foePieces, loc)){
            moves.add(new Point(loc));
            loc.incX(-1).incY(1);
        }
        addIfFoe(moves, foePieces, loc);

        return moves;
    }
    
    //adds a move if the move is legal
    public void addMove(ArrayList<Point> moves, Point loc, ArrayList<GamePiece> friendPieces){
        //if the move is on to a friendly piece, stop checking
        for(GamePiece p: friendPieces)
            if (p.getLocation().equals(loc))
                return;

        //if the move is within the grid, add the move
        if(loc.getX() >= 0 && loc.getY() >= 0 && loc.getX() <= 7 && loc.getY() <= 7)
            moves.add(loc);
    }
    
    //adds a move if there is a foe on the spot
    public void addIfFoe(ArrayList<Point> moves, ArrayList<GamePiece> foePieces, Point loc){
        if(ChessGame.findPiece(foePieces, loc) != null)
            moves.add(new Point(loc));
    }

    //finds if a point is completely empty of pieces
    public boolean isEmpty(ArrayList<GamePiece> pc1, ArrayList<GamePiece> pc2, Point loc){return ChessGame.findPiece(pc1, loc) == null && ChessGame.findPiece(pc2, loc) == null;}

    //methods for dealing with time at which pieces are captured
    public GamePiece setTime(int seconds){
        time = seconds;
        return this;
    }
    public int getTime(){return time;}

    //method for determining the bullet-stage rules for movement and beginning position
    public void randomize(int xmin, int xmax, int ymin, int ymax){p = new Point((int) (Math.random()*(xmax-xmin)+xmin), (int) (Math.random()*(ymax-ymin)+ymin));}
    public abstract void move();

    //methods for setting and determining relative size of piece based on type
    public abstract int getWidth();
    public abstract int getHeight();
}
