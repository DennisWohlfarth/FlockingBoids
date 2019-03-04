package main;

import java.util.Random;

public class Controller {
    private int fieldSize = 50;
    private Point [][] field;
    private int numBoids;
    Random rand = new Random();

    public Controller()
    {
        for(int i=0; i<fieldSize; i++){
            field = new Point[fieldSize][fieldSize];
            for(int j=0; j<fieldSize; j++)
            {
                field[i][j] = new Point(i, j);
            }
        }
    }
    public void tick()
    {

    }
    public void initializeRandom()
    {
        for(int i = 0; i<numBoids;i++)
        {
            int rand1 = rand.nextInt(fieldSize);
            int rand2 = rand.nextInt(fieldSize);
            if(field[rand1][rand2].getBoid()==null)
            {
                field[rand1][rand2].setBoid(new Boid(rand1, rand2));
            }
        }
    }


    public int getFieldSize() {
        return fieldSize;
    }

    public Point[][] getField() {
        return field;
    }
}
