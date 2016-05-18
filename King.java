import java.util.ArrayList;

public class King extends GamePiece{
    private int left, right, up, down;

    public ArrayList<Point> getMoves(ArrayList<GamePiece> friendPieces, ArrayList<GamePiece> foePieces){
        ArrayList<Point> moves = new ArrayList<>();

        //add all potential adjacent moves
        addMove(moves, new Point(getLocation()).incX(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(1).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incX(-1).incY(1), friendPieces);
        addMove(moves, new Point(getLocation()).incY(-1), friendPieces);
        addMove(moves, new Point(getLocation()).incY(1), friendPieces);

        //get temporary arraylist without king for simultaneous comparison w/norm
        ArrayList<GamePiece> tempFriends = new ArrayList<>(friendPieces); 
        for(int i=0; i<tempFriends.size(); i++)
            if(tempFriends.get(i).getID().equals("K"))
                tempFriends.remove(i); 

        //go through castling logic if the king is in its original location
        if(getLocation().getX()==4 && (getLocation().getY()==0 || getLocation().getY()==7)){
            GamePiece temp;
            Point orig = new Point(getLocation()), tempPoint;
            
            //incrementally move the king right, stopping when obstructed or in check
            getLocation().incX(1);
            while(getLocation().getX()<8 && isEmpty(tempFriends, foePieces, getLocation()) && ChessGame.inCheck(friendPieces, foePieces)==null) 
                getLocation().incX(1);
            
            //if the king stopped at the rook at the right side, add kingside castling
            temp = ChessGame.findPiece(tempFriends, getLocation());
            if(getLocation().getX()==7 && temp != null && temp.getID().equals("R"))
                moves.add(new Point(orig).incX(2));
            
            //same queenside (left)
            setLocation(new Point(orig).incX(-1));    
            while(getLocation().getX()>0 && isEmpty(tempFriends, foePieces, getLocation()) && ChessGame.inCheck(friendPieces, foePieces)==null) 
                getLocation().incX(-1);

            temp = ChessGame.findPiece(tempFriends, getLocation());
            if(getLocation().getX()==0 && temp != null && temp.getID().equals("R"))
                moves.add(new Point(orig).incX(-2));

            //set location back to beginning location
            setLocation(orig);
        } 

        return moves;
    }
    //constructor and ID definition
    public King(Point p){
        super(p);
        left = right = up = down = 0;
    }
    //various methods for class identity, as well as methods to set increment (velocity) horizontally and vertically
    public String getID(){ return "K";}
    public int offSet(){return 7;}
    public void move(){getLocation().incX(right-left).incY(down-up);}
    public void setLeft(int x){left = x;}
    public void setRight(int x){right = x;}
    public void setUp(int x){up = x;}
    public void setDown(int x){down = x;}
    public int getWidth(){return 10;}
    public int getHeight(){return 10;}
}
