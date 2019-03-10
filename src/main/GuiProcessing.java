package main;

import processing.core.PApplet;


public class GuiProcessing extends PApplet {

    private static Controller flock = new Controller();

    public static void main(String[] args) {
        PApplet.main("main.GuiProcessing");
    }

    public void settings(){
        size(flock.getWidth(), flock.getHeight());
    }

    public void setup() {
        flock.start();
    }

    public void draw() {
        flock.tick();
        fill(255,40);
        rect(0,0,width,height);
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
        int [] rgb = flock.setColor(51);
        flock.addBoid(new Boid(mouseX, mouseY,rgb));
    }

    public Controller getFlock() {
        return flock;
    }

}
