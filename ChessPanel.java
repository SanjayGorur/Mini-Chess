import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class ChessPanel extends JPanel {
    //fields for black/white pieces, active (clicked) piece, active (highlighted) moves
    private ArrayList<GamePiece> black, white, removedBlack, removedWhite;
    private GamePiece active;
    private ArrayList<Point> moves;

    //constructer, defines black/white pieces
    public ChessPanel(ArrayList<GamePiece> bl, ArrayList<GamePiece> wh, ArrayList<GamePiece> rb, ArrayList<GamePiece> rw){
        black = bl;
        white = wh;
        removedBlack = rb;
        removedWhite = rw;
        setBackground(new Color(255,225,175));

        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("Rondo.wav")));
            clip.loop(99999);
        }
        catch (Exception exc)
        {
            System.out.println("Sound error (likely a mishandled wav file).");
        }
    }

    //paints black squares of grid
    private void paintGrid(Graphics g){
        for (int i = 0; i < 400; i += 50)
            for (int j = i%100; j < 400; j += 100)
                g.fillRect(i, j, 50, 50);
    }

    private void paintPiece(Graphics g, GamePiece p, Point loc, Color out, Color fill){
        g.setColor(fill);
        g.fillOval(loc.getX(), loc.getY(), 50, 50);
        g.setColor(out);
        g.drawOval(loc.getX(), loc.getY(), 50, 50);
        g.drawString(p.getID(), loc.getX()+p.offSet(), loc.getY()+44);
    }

    //paints a set of pieces with specified outline/letter colors (out) and main colors (fill)
    private void paintPieces(Graphics g, ArrayList<GamePiece> pieces, Color out, Color fill){
        Point loc;
        for(GamePiece p: pieces) {
            loc = p.getLocation();
            loc = new Point(loc.getX()*50, loc.getY()*50);
            paintPiece(g, p, loc, out, fill);
        }
    }
    private void paintRemoved(Graphics g, int start, int inc, ArrayList<GamePiece> removed, Color out, Color fill) {
        Point loc = new Point(400, start);
        for (GamePiece p : removed) {
            paintPiece(g, p, loc, out, fill);
            if (loc.incX(50).getX() >= 600)
                loc = new Point(400, loc.getY() + inc);
        }
    }


    //activates a piece (highlight on click), or deactivates a piece and potential moves
    public void activate(GamePiece p){
        active = p;
    }
    public void deactivate(){
        active = null;
        moves = null;
    }

    //returns active piece
    public GamePiece activePiece(){
        return active;
    }

    //accessor and mutator for list of potential moves
    public void setMoves(ArrayList<Point> p){
        moves = p;
    }
    public ArrayList<Point> getMoves(){
        return moves;
    }

    //colors the letter of the specified piece the specified color, as long as the piece is not null
    private void highlight(Graphics g, Color c, GamePiece p){
        if(p != null){
            g.setColor(c);
            g.drawString(p.getID(), p.getLocation().getX()*50+p.offSet(), p.getLocation().getY()*50+44);
        }
    }

    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        //cast to Graphics2D and set anti-aliasing for text and shapes
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        //paint grid as custom color (burnt sienna)
        g.setColor(new Color(153,51,0));
        paintGrid(g);

        //paints all potential moves (if any) as custom blue
        Color activeColor = new Color(102, 0, 255, 150);
        if(moves != null){
            g.setColor(activeColor);
            for(Point p: moves)
                g.fillRect(p.getX()*50, p.getY()*50, 50, 50);
        }

        //set font for pieces
        g.setFont(new Font("Arial", Font.BOLD, 50));

        //define color of white portion of pieces
        Color whColor = new Color(255,200,150);

        //paint all pieces
        paintPieces(g, black, whColor, Color.DARK_GRAY);
        paintPieces(g, white, Color.DARK_GRAY, whColor);
        paintRemoved(g, 350, -50, removedBlack, whColor, Color.DARK_GRAY);
        paintRemoved(g, 0, 50, removedWhite, Color.DARK_GRAY, whColor);

        //highlight any active or checked pieces
        highlight(g, activeColor, active);
        highlight(g, Color.RED, ChessGame.inCheck(black, white));
        highlight(g, Color.RED, ChessGame.inCheck(white, black));


        //write checkmate if the game has come to an end.
        String checkmate = ChessGame.checkmate();
        if(checkmate != null){
            g.setColor(Color.RED);
            g.drawString(checkmate + " has lost.", 50, 225);
        }
    }
}