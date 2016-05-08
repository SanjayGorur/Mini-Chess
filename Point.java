public class Point {
    //instance variables for x and y coordinates
    private int x, y;

    //constructor for new point
    public Point(int xinit, int yinit){
        x = xinit;
        y = yinit;
    }
    //constructor to copy other point, avoiding aliasing
    public Point(Point p){
        x = p.getX();
        y = p.getY();
    }

    //accessor methods for x and y
    public int getX(){ return x;}
    public int getY(){ return y;}

    //increments x or y by specified amount, modifying the object and returning the new point
    public Point incX(int n){
        x+=n;
        return this;
    }
    public Point incY(int n){
        y+=n;
        return this;
    }

    //returns whether the x and y coordinates of the two points are equal
    public boolean equals(Point p){ return x == p.getX() && y == p.getY();}
}
