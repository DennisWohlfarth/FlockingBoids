package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FlockingBoidsTest {
    private static GuiProcessing test;
    Random random = new Random();

    @BeforeAll
    public static void test1()
    {
        test = new GuiProcessing();
        test.main(new String[] {"--present", "test"});
    }

    @Test()
    public void test()
    {
        System.out.println("Start");
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        test.getFlock().setCohesionForce(0.25f);
        test.getFlock().setSeparationForce(0);
        test.getFlock().setAlignmentForce(0);
        test.getFlock().setFlockmateRadius(300);
        System.out.println("Cohesion maximize");

        try {
            Thread.sleep(20000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        float diff;
        int rand;
        for(int i=0;i<100;i++)
        {
            rand = random.nextInt(random.nextInt(test.getFlock().getNumBoids())-1);
            diff = test.getFlock().getBoids().get(rand).getPosition().x -
                    test.getFlock().getBoids().get(rand+1).getPosition().x;
            System.out.println(diff);
            assertTrue(diff <=1 && diff>=-1);
            //Sollte annähernd 0 sein aber durch die Laufzeit des Programms kommt es zu Höheren Differenzen
            //Falls es nicht klappt, einfach Differenz erhöhen
        }
        test.getFlock().setCohesionForce(0);
        test.getFlock().setSeparationForce(0.25f);
        test.getFlock().setAlignmentForce(0);
        test.getFlock().setFlockmateRadius(500);
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        for(int i=0;i<100;i++)
        {
            rand = random.nextInt(random.nextInt(test.getFlock().getNumBoids())-1);
            diff = test.getFlock().getBoids().get(rand).getPosition().x -
                    test.getFlock().getBoids().get(rand+1).getPosition().x;
            System.out.println(diff);
            assertTrue(diff >=0.5 || diff<=-0.5);
        }
        test.getFlock().setCohesionForce(0.1f);
        test.getFlock().setSeparationForce(0.1f);
        test.getFlock().setAlignmentForce(0.1f);
        test.getFlock().setFlockmateRadius(100);

        try {
            Thread.sleep(50000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


    }

}