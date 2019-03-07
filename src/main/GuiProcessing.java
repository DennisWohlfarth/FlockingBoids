package main;

import processing.core.PApplet;


public class GuiProcessing extends PApplet {

    private static Controller flock = new Controller();
    private static String[] args1 = new String[2];

    public static void main(String[] args) {
        args1 = args;
        PApplet.main("main.GuiProcessing");
    }

    public void settings(){
        size(flock.getWidth(), flock.getHeight());
    }

    public void setup() {
        flock.start();
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
        int [] rgb = flock.setColor(51);
        flock.addBoid(new Boid(mouseX, mouseY,rgb));
    }

    public Controller getFlock() {
        return flock;
    }

}
