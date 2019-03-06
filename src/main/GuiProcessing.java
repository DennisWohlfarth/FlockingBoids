package main;

import processing.core.PApplet;


public class GuiProcessing extends PApplet {

    private Controller flock;

    public static void main(String[] args) {
        PApplet.main("main.GuiProcessing");
    }

    public void settings(){
        flock = new Controller();
        size(flock.getWidth(), flock.getHeight());
    }

    public void setup() {
        flock.start("Rainbow");
    }

    public void draw() {
        //background(255);
        fill(255,40);
        rect(0,0,width,height);
        flock.tick();
        //background(75);
        for(Boid b : flock.getBoids()){
            pushMatrix();
            translate(b.getPosition().x, b.getPosition().y);
            noStroke();
            fill(b.getColor()[0], b.getColor()[1], b.getColor()[2]);
            circle(0,0,flock.getRadius());
            popMatrix();
        }
    }

    public void mousePressed() {
        int [] rgb = flock.setColor();
        flock.addBoid(new Boid(mouseX, mouseY,rgb));
    }
}