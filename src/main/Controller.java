package main;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private int width = 1000;
    private int height = 600;
    private int numBoids = 200;
    private float maxVelocity = 5.0f;
    private int separationDistance = 30;
    private int flockmateRadius = 60;
    private ArrayList<Boid> boids;
    private float separationForce = 0.03f;
    private float alignmentForce = 0.03f;
    private float cohesionForce = 0.03f;
    private float radius = 10.0f;
    private int colorCounter1=0;
    private int colorCounter2 =0;
    private int colorCounter3 =0;
    Random random = new Random();

    Controller() {
        boids = new ArrayList<>();
    }
    public void start(String color) {
        if(color == "Rainbow")
        {
            for (int i=0;i<numBoids;i++){
                int [] rgb = setColor();
                boids.add(new Boid(random.nextInt(width), random.nextInt(height),rgb));
            }
        }
    }

    public void tick() {
        for (Boid b : boids) {
            PVector separate = new PVector(0, 0, 0);
            PVector alignment = new PVector(0, 0);
            PVector cohesion = new PVector(0, 0);
            PVector sumAlignment = new PVector(0, 0);
            PVector sumCohesion = new PVector(0, 0);
            int countSeperate = 0, count = 0;
            for (Boid b2 : boids) {
                float distance = PVector.dist(b.getPosition(), b2.getPosition());
                if ((distance > 0) && (distance < separationDistance)) {
                    PVector diff = PVector.sub(b.getPosition(), b2.getPosition());
                    separate.add(diff.normalize().div(distance));
                    countSeperate++;
                }
                if ((distance > 0) && (distance < flockmateRadius)) {
                    sumAlignment.add(b2.getSpeed());
                    sumCohesion.add(b2.getPosition());
                    count++;
                }
            }
            if (countSeperate > 0) {
                separate.div((float) countSeperate);
            }
            if (separate.mag() > 0) {
                separate.normalize();
                separate.mult(maxVelocity);
                separate.sub(b.getSpeed());
                separate.limit(separationForce);
            }
            if (count > 0) {
                sumAlignment.div((float) count);
                sumAlignment.normalize();
                sumAlignment.mult(maxVelocity);
                alignment = PVector.sub(sumAlignment, b.getSpeed());
                alignment.limit(alignmentForce);
                sumCohesion.div(count);
                cohesion = seek(sumCohesion, b);
            }
            separate.mult(1.5f);
            alignment.mult(1.0f);
            cohesion.mult(1.0f);
            b.getAcceleration().add(separate);
            b.getAcceleration().add(alignment);
            b.getAcceleration().add(cohesion);
            update(b);
            checkBorders(b);
        }
    }

    public void update(Boid b) {
        b.getSpeed().add(b.getAcceleration());
        b.getSpeed().limit(maxVelocity);
        b.getPosition().add(b.getSpeed());
        b.getAcceleration().mult(0);
    }

    public PVector seek(PVector target, Boid b) {
        PVector desired = PVector.sub(target, b.getPosition());
        desired.normalize();
        desired.mult(maxVelocity);
        PVector steer = PVector.sub(desired, b.getSpeed());
        steer.limit(cohesionForce);
        return steer;
    }

    public void checkBorders(Boid b) {
        if (b.getPosition().x < -radius) b.getPosition().x = width + radius;
        if (b.getPosition().y < -radius) b.getPosition().y = height + radius;
        if (b.getPosition().x > width + radius) b.getPosition().x = -radius;
        if (b.getPosition().y > height + radius) b.getPosition().y = -radius;
    }
    public int[] setColor(){
        int [] color = new int[3];
        if(colorCounter1 < 255){
            colorCounter1 = colorCounter1+51;
        }
        else if(colorCounter2<255){
            colorCounter1=0;
            colorCounter2 = colorCounter2+51;
        }
        else if(colorCounter3<255){
            colorCounter1=0;
            colorCounter2=0;
            colorCounter3 = colorCounter3+51;
        }
        else{
            colorCounter1=0;
            colorCounter2=0;
            colorCounter3=0;
        }
        color[0]=colorCounter1;
        color[1]=colorCounter2;
        color[2]=colorCounter3;
        return color;

    }



    public void addBoid(Boid b) {
        boids.add(b);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Boid> getBoids() {
        return boids;
    }

    public float getRadius() {
        return radius;
    }

}
