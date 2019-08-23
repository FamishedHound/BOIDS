package pack_technical;

import processing.core.PApplet;
import processing.core.PVector;

public class AttackerPI {
    private PApplet parent;
    private boolean running=true;
    private PVector[] movement = new PVector[4];

    private int frameCount = 0;


    public AttackerPI(PApplet parent){
        this.parent=parent;
        movement[0]=new PVector(1,0);
        movement[1]=new PVector(-1,0);
        movement[2]=new PVector(0,1);
        movement[3]=new PVector(0,-1);

    }

    public void stopTheSimulation(){
        parent.noLoop();
    }

    public synchronized void saveFrame(){
       parent.saveFrame("./Frame/frame"+frameCount+".png" );
       frameCount+=1;
    }

    public PVector move(String direction){
        switch (direction){
            case "up":
                return movement[2];

            case "left":
                return movement[1] ;

            case "right":
                return movement[2] ;

            case "down":
                return movement[3] ;



        }

       return null;
    }

}
