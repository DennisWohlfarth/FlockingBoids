package main;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    /////////////////////////////////////////////////////////////////////
    //SETTINGS
    /////////////////////////////////////////////////////////////////////
    private int width = 1000;
    private int height = 600;
    private int numBoids = 200;
    private int flockmateRadius = 60;
    private int separationDistance = 30;
    private float maxVelocity = 2.0f;
    private float separationForce = 0.03f;
    private float alignmentForce = 0.03f;
    private float cohesionForce = 0.03f;
    private String startingPosition = "CircleIn"; //Is possible: Random, CircleRandom, CircleIn, Sine, Phyllotaxis
    private String coloring ="ByMovement"; //Is possible: ByMovement, Rainbow
    private ArrayList<Boid> boids;
    //////////////////////////////////////////////////////////////////////


    private float radius = 5.0f;
    private int [] colorRGB = new int[]{0,255,0};
    boolean colorUp = true;

    Random random = new Random();

    Controller() {
        boids = new ArrayList<>();
    }
    public void start() {
        for (int i=0;i<numBoids;i++){
            int [] rgb = setColor(51);
            int [] startXY = new int[2];
            switch (startingPosition){
                case "Random":
                    startXY = initializeRandom();
                    boids.add(new Boid(startXY[0],startXY[1],rgb));
                    break;
                case "CircleRandom":
                    startXY = initializeCircleRandom(i);
                    boids.add(new Boid(startXY[0],startXY[1],rgb));
                    break;
                case "CircleIn":
                    startXY = initializeCircleRandom(i);
                    boids.add(new Boid(startXY[0],startXY[1],rgb));
                    double angle = i * 2 * Math.PI / numBoids;
                    boids.get(i).getSpeed().add(-(int)(200 * Math.sin(angle)),-(int)(200 * Math.cos(angle))).normalize();
                    break;
                case "Sine":
                    startXY = initializeSine(i);
                    boids.add(new Boid(startXY[0],startXY[1],rgb));
                    int [] radialVS = radialVelocity(i/numBoids);
                    boids.get(i).getSpeed().add(radialVS[0], radialVS[1]).normalize().mult(maxVelocity);
                    break;
                case "Phyllotaxis":
                    startXY = initializePhyllotaxis(i);
                    boids.add(new Boid(startXY[0],startXY[1],rgb));
                    int [] radialVP = radialVelocity(i/numBoids);
                    boids.get(i).getSpeed().add(radialVP[0], radialVP[1]).normalize().mult(maxVelocity);

                    break;
                default:
                    System.out.println("Starting Position invalid");
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
                    diff.normalize();
                    diff.div(distance);
                    separate.add(diff);
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
        colorRGB[0]=b.getColor()[0];
        colorRGB[1]=b.getColor()[1];
        colorRGB[2]=b.getColor()[2];
        if(coloring == "ByMovement")
        {
            int [] rgb = setColor(5);
            b.setColor(rgb);
        }

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

    public int [] initializeRandom() {
        return new int[]{random.nextInt(width), random.nextInt(height)};
    }
    public int [] initializeCircleRandom(int i){
        double angle = i * 2 * Math.PI / numBoids;
        double x = 200 * Math.sin(angle);
        double y = 200 * Math.cos(angle);
        return new int[] {(int)x + width / 2, (int)y + height / 2};
    }

    public int [] initializeSine(int i) {
        double angle = 2 * Math.PI * i / numBoids;
        double x = width * i / numBoids;
        double y = height / 2 + Math.sin(angle) * height / 4;
        return new int[]{(int)x,(int)y};
    }

    public int [] initializePhyllotaxis(int i) {
        double θ = Math.PI * i * (Math.sqrt(5) - 1);
        double r = Math.sqrt(i) * 200 / Math.sqrt(numBoids);
        return new int[]{(int)(width / 2 + r * Math.cos(θ)),(int)(height / 2 - r * Math.sin(θ))};
    }

    public int[] radialVelocity(int p) {
        double x = Math.sin(2 * Math.PI * p);
        double y = Math.cos(2 * Math.PI * p);
        return new int[]{(int)x,(int)y};
    }

    public int[] setColor(int step){
        int [] color = new int[3];
        if(colorUp==true)
        {
            if(colorRGB[0] < 255){
                colorRGB[0] = colorRGB[0]+step;
            }
            else if(colorRGB[1] >0)
            {
                colorRGB[1] = colorRGB[1]-step;
            }
            else if(colorRGB[2] <255)
            {
                colorRGB[2] = colorRGB[2]+step;
            }
            else
            {
                colorUp=false;
            }
        }
        else {
            if(colorRGB[0] > 0){
                colorRGB[0] = colorRGB[0]-step;
            }
            else if(colorRGB[1] <255)
            {
                colorRGB[1] = colorRGB[1]+step;
            }
            else if(colorRGB[2] >0)
            {
                colorRGB[2] = colorRGB[2]-step;
            }
            else
            {
                colorUp=true;
            }
        }
        color[0]=colorRGB[0];
        color[1]=colorRGB[1];
        color[2]=colorRGB[2];
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

    public int getNumBoids() {
        return numBoids;
    }

    public void setSeparationForce(float separationForce) {
        this.separationForce = separationForce;
    }

    public void setAlignmentForce(float alignmentForce) {
        this.alignmentForce = alignmentForce;
    }

    public void setCohesionForce(float cohesionForce) {
        this.cohesionForce = cohesionForce;
    }

    public void setFlockmateRadius(int flockmateRadius) {
        this.flockmateRadius = flockmateRadius;
    }

    public void setStartingPosition(String startingPosition) {
        this.startingPosition = startingPosition;
    }
}
