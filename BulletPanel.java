import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/* Wholly superfluous class. Rendering is done actively in ChessGame and the game logic would
 * be better handled in that class, but the code was already messy enough, so this class gave
 * necessary structure. Additionally, there was a slight error on disregarding the class, so
 * reorginization would be time consuming for no real benefit, since no others will be working
 * with this code.
 */

public class BulletPanel extends JPanel{
    //fields for the unadded black/white pieces, added black/white, and current time in milliseconds
    private ArrayList<GamePiece> blackRemaining, whiteRemaining, black, white;
    private int curTime, timeSinceTransfer;

    //constructor, takes arguments for the black, white arraylists and corresponding kings
    public BulletPanel(ArrayList<GamePiece> bl, ArrayList<GamePiece> wh, GamePiece blk, GamePiece whk){
        //set fields for unadded pieces
        blackRemaining = bl;
        whiteRemaining = wh;

        //add kings to pieces
        black = new ArrayList<>();
        black.add(blk);
        white = new ArrayList<>();
        white.add(whk);

	//set background and time since trasfer of pieces
        setBackground(Color.WHITE);
    	timeSinceTransfer = 0;
    }

    //paint a particular piece as an oval according to its width and height fields
    public static void paintPiece(Graphics g, GamePiece p, Color out, Color fill){
        g.setColor(fill);
        g.fillOval(p.getLocation().getX(), p.getLocation().getY(), p.getWidth(), p.getHeight());
        g.setColor(out);
        g.drawOval(p.getLocation().getX(), p.getLocation().getY(), p.getWidth(), p.getHeight());
    }

    //paint all pieces and the basic board
    public void paintComponent(Graphics gr){
        super.paintComponent(gr);

        //cast to Graphics2D and set anti-aliasing for text and shapes
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //draw basic sides and boundaries
        g.setColor(Color.BLACK);
        g.fillRect(300,0,350,450);
        g.drawRect(25, 25, 250, 350);
        g.setColor(Color.WHITE);
        g.drawRect(325, 25, 250, 350);

        //paint all foe pieces if there are any
        if(black.size()>1)
            for(GamePiece p: black.subList(1,black.size()))
                paintPiece(g, p, Color.WHITE, Color.BLACK);
        if(white.size()>1)
            for(GamePiece p: white.subList(1,white.size()))
                paintPiece(g, p, Color.BLACK, Color.WHITE);

        //paint the kings
        paintPiece(g, black.get(0), Color.WHITE, Color.RED);
        paintPiece(g, white.get(0), Color.BLACK, Color.CYAN);
    }

    //update the game logic
    public void updateGame(int FPS, boolean suddenDeath){
        //update the time so that the target FPS corresponds to one second (curTime/1000)
        curTime+=1000/FPS;
        timeSinceTransfer+=1000/FPS;

        //move pieces if they are running
        if(suddenDeath || whiteTime()>0)
            movePieces(white, 25, 265, 25, 365);
        if(suddenDeath || blackTime()>0)
            movePieces(black, 325, 565, 25, 365);

        //transfer pieces every 1/10 second
        if(timeSinceTransfer >= 100){
            timeSinceTransfer = 0;
            transferPieces(blackRemaining, black, 325, 565, 25, 365);
            transferPieces(whiteRemaining, white, 20, 265, 25, 365);
        }
    }

    //moves pieces within their boundaries by their intrinsic behaviors
    public void movePieces(ArrayList<GamePiece> pcs, int xmin, int xmax, int ymin, int ymax){
        Point loc;
        for(GamePiece p: pcs){
            //move the piece (varies by subclass)
            p.move();

            //if out of the grid, teleport (left boundary to right and vice versa)
            loc = p.getLocation();
            if(loc.getX()<xmin)
                loc = new Point(xmax, loc.getY());
            else if(loc.getX()>xmax)
                loc = new Point(xmin, loc.getY());
            if(loc.getY()<ymin)
                loc = new Point(loc.getX(), ymax);
            else if(loc.getY()>ymax)
                loc = new Point(loc.getX(), ymin);

            //set the location to the new one within the boundaries
            p.setLocation(loc);
        }
    }
    //transfer in any pieces according to the current time and their capture time by a 1:1 ratio
    private void transferPieces(ArrayList<GamePiece> rem, ArrayList<GamePiece> pcs, int xmin, int xmax, int ymin, int ymax){
        for(int i = 0; i < rem.size(); i++)
            if(rem.get(i).getTime() <= curTime/100){
                rem.get(i).randomize(xmin, xmax, ymin, ymax);
                pcs.add(rem.remove(i));
            }
    }

    //accessor methods for pieces and time left
    public ArrayList<GamePiece> black(){return black;}
    public ArrayList<GamePiece> white(){return white;}
    public int timeLeft(ArrayList<GamePiece> rem, ArrayList<GamePiece> pcs){
        int max = 0;
        for(GamePiece p: rem)
            if(p.getTime() > max)
                max = p.getTime();

        for(GamePiece p: pcs)
            if(p.getTime() > max)
                max = p.getTime();

        return 60000+max*100-curTime;
    }
    public int blackTime(){return timeLeft(blackRemaining, black);}
    public int whiteTime(){return timeLeft(whiteRemaining, black);}
}
