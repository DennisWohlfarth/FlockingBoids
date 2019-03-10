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
        test.getFlock().setStartingPosition("Random");
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
        test.getFlock().setFlockmateRadius(test.getFlock().getHeight()-10);
        System.out.println("Cohesion maximize");

        try {
            Thread.sleep(15000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        float diff;
        int rand;
        for(int i=0;i<100;i++)
        {
            try{
                rand = random.nextInt(random.nextInt(test.getFlock().getNumBoids()));
            }
            catch (Exception e){
                rand =0;
            }

            diff = test.getFlock().getBoids().get(rand).getPosition().x -
                    test.getFlock().getBoids().get(rand+1).getPosition().x;
            System.out.println(diff);
            assertTrue(diff <=5 && diff>=-5);
            //Sollte annähernd 0 sein aber durch die Laufzeit des Programms kommt es zu Höheren Differenzen
            //Falls es nicht klappt, einfach Differenz erhöhen
        }
        test.getFlock().setCohesionForce(0);
        test.getFlock().setSeparationForce(0.25f);
        test.getFlock().setAlignmentForce(0);
        test.getFlock().setFlockmateRadius(100);
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        for(int i=0;i<100;i++)
        {
            try{
                rand = random.nextInt(random.nextInt(test.getFlock().getNumBoids()));
            }
            catch (Exception e){
                rand =0;
            }
            diff = test.getFlock().getBoids().get(rand).getPosition().x -
                    test.getFlock().getBoids().get(rand+1).getPosition().x;
            System.out.println(diff);
            assertTrue(diff >=0.1 || diff<=-0.1);
            //Dürfen nicht übereinander sein, sich nicht zu Nahe kommen
            //Kann auch wegen der Laufzeit des Programmes zu Fehlern kommen
        }
        test.getFlock().setCohesionForce(0.1f);
        test.getFlock().setSeparationForce(0.1f);
        test.getFlock().setAlignmentForce(0.1f);
        test.getFlock().setFlockmateRadius(100);

        try {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


    }

}