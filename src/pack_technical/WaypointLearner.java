package pack_technical;

import pack_AI.AI_type;
import pack_boids.Boid_generic;
import pack_boids.Boid_standard;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

public class WaypointLearner {
    private ArrayList<PVector> potentialWaypoints = new ArrayList<>();
    private Random rand = new Random();
    private float minX = 20.0f;
    private float maxX = 1000.0f;
    private AI_type ai;
    private ArrayList<Boid_generic> defenders;
    private PApplet parent;
    private PatrollingScheme scheme;

    public WaypointLearner(PApplet parent,AI_type ai, ArrayList<Boid_generic> defenders) {
        this.ai = ai;
        this.defenders = copyTheStateOfAttackBoids(defenders);
        this.parent=parent;
        this.scheme =  new PatrollingScheme(ai.getWayPointForce());

        //Generate Random waypoints
        for (int x = 0; x < rand.nextInt(100) + 20; x++) {
            potentialWaypoints.add(new PVector(rand.nextFloat() * (maxX - minX) + minX, rand.nextFloat() * (maxX - minX) + minX));

        }


    }


    public ArrayList<Boid_generic> copyTheStateOfAttackBoids(ArrayList<Boid_generic> boids) {
        ArrayList<Boid_generic> boidListClone = new ArrayList<>();
        //System.out.println(boids);

        for (Boid_generic boid : boids) {
            //nadaj im tutaj acceleration velocity etc..
            Boid_generic bi = new Boid_standard(parent, boid.getLocation().x, boid.getLocation().y, 6, 10);
            bi.setAi(ai);
            bi.setAcceleration(boid.getAcceleration());
            bi.setVelocity(boid.getVelocity());
            bi.setLocation(boid.getLocation());
            boidListClone.add(bi);
        }

        return boidListClone;

    }
}
