package main;

public class Point {
    private Boid boid;
    private boolean hasBoid = false;
    private int x;
    private int y;

    public Point(int i, int y)
    {

    }

    public Boid getBoid() {
        return boid;
    }

    public void setBoid(Boid boid) {
        this.boid = boid;
    }
}
