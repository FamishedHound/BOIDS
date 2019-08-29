package pack_technical;

import pack_1.ParameterGatherAndSetter;
import pack_boids.Boid_generic;
import processing.core.PApplet;
import processing.core.PVector;
import py4j.GatewayServer;
import sun.plugin.javascript.navig.Array;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

public class AttackerAPI  {

    private int saved=0;
    private PApplet parent;

    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean a){
        running=a;
    }

    private boolean running=true;
    public String[] args;
    private PVector position ;
    private int frameCount = 0;
    private GatewayServer gatewayServer;
    private boolean frameCreated=false;
    private float reward;
    private int action=-1;
    public boolean getFrameCreated(){
        return frameCreated;
    }
    public void set_frameCreated(boolean a){
        frameCreated=a;
    }
    private CollisionHandler col;
    public int[][] pix;
    private ArrayList<Boid_generic> defender;
    private ArrayList<Boid_generic> attacker ;
    private long startTime;
    ParameterGatherAndSetter gatherer;
    public PVector getAttackVector() {
        return attackVector;
    }
    //Global vars:
    private int howManyAngles = 36;

    private PVector[] movement = new PVector[howManyAngles];

    private PVector attackVector=null;
    public void stopSimulation(){
        parent.noLoop();
    }

    public void generateAFrame(){

        parent.loop();
    }

    public AttackerAPI(PApplet parent, PVector position, CollisionHandler col, ArrayList<Boid_generic> attacker , ArrayList<Boid_generic> defender,String[] args,GameManager manager){
        this.args=args;
        this.col=col;
        this.parent=parent;
        this.position=position;
        this.attacker =attacker;
        this.defender=defender;
        initializeMovement();
        this.startTime=System.nanoTime();

        this.gatherer=new ParameterGatherAndSetter(manager,col,args,0);
        //stack = new SharedStackJ2P();
    }
    //public SharedStackJ2P getStack(){
    //    return stack;
   // }

    public int returnMe1(){
        return 1;
    }

    public void stopTheSimulation(){
        parent.noLoop();
    }
    public void closeTheSimulation(){
        reward=0;
        col.setLose(false);
        col.setVictory(false);
        frameCount=0;
        startTime = System.nanoTime();
        for( Boid_generic be : defender){
            PVector acceleration = be.getAcceleration();
            PVector velocity = be.getVelocity();
            PVector location = new PVector(450,510);

            be.setLocation(location);
            be.setVelocity(new PVector(0,0));
            be.setAcceleration(new PVector(0,0));
        }
        for (Boid_generic attack : attacker){
            PVector acceleration = attack.getAcceleration();
            PVector velocity = attack.getVelocity();
            PVector location = new PVector(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
            setPosition(location);
            attack.setLocation(location);
            attack.setVelocity(new PVector(0,0));
            attack.setAcceleration(new PVector(0,0));
        }
        //parent.loop();
    }
    public String getFileInfo(int v){
        try {
            return gatherer.generateEndingStatement(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getReward(){


        BigInteger timeout = new BigInteger("600");
        long check = startTime-System.nanoTime();
        BigInteger estimatedTime = BigInteger.valueOf(startTime-System.nanoTime());
        int compareValue = estimatedTime.compareTo(timeout);
        if (col.isVictory()){
            try {
                gatherer.generateEndingStatement(1);
                return 9000;
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (col.isLose() || compareValue == 1){
            try {
                gatherer.generateEndingStatement(0);
                return -9000;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;

    }
    public void establishConnection() {
        ListenerApplication application = new ListenerApplication();
        gatewayServer = new GatewayServer(this);
        gatewayServer.start();
        application.notifyAllListeners();
        System.out.println("Gateway Server Started");
    }

    public void setPosition(PVector vector){
        position=vector;
    }



    public int isSaved(){
        return saved;
    }
    public  synchronized void saveFrame()  {
        saved=0;
       parent.saveFrame("./Frame/frame"+frameCount+".png");
       frameCount++;
       saved=1;


            //this.pix=pix;



    }
    public byte[] getByteArray() {
        // Set up a ByteBuffer called intBuffer
        int iMax=1080;
        int jMax=1920;
        ByteBuffer intBuffer = ByteBuffer.allocate(4*iMax*jMax); // 4 bytes in an int
        intBuffer.order(ByteOrder.LITTLE_ENDIAN); // Java's default is big-endian

        // Copy ints from intArray into intBuffer as bytes
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++){
                intBuffer.putInt(pix[i][j]);
            }
        }

        // Convert the ByteBuffer to a byte array and return it
        byte[] byteArray = intBuffer.array();
        return byteArray;
    }

    public PVector move(int action){

        attackVector = movement[action];
       return movement[action];
    }

    public void initializeMovement(){
        double current =0;
        double step = 360/howManyAngles;
        for (int i=0;i<howManyAngles;i++){
            movement[i] = new PVector((float)Math.sin(Math.toRadians(current)),(float)Math.cos(Math.toRadians(current)));
            current=current+step;
        }

    }

}
