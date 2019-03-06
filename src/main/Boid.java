package main;

import processing.core.PVector;

public class Boid {
    private PVector position;
    private PVector speed;
    private PVector acceleration;
    private int[] color;


    public Boid(int x, int y, int [] rgb)
    {
        position = new PVector(x, y);
        acceleration = new PVector(0,0);
        speed = PVector.random2D();
        this.color = rgb;
    }

    public PVector getPosition() {
        return position;
    }

    public PVector getSpeed() {
        return speed;
    }

    public PVector getAcceleration() {
        return acceleration;
    }

    public int[] getColor() {
        return color;
    }
}
