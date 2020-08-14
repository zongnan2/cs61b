package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature{
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r,g,b);
    }

    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Clorus replicate() {
        energy = 0.5*energy;
        double babyEnergy = energy;
        return new Clorus(babyEnergy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for(Direction direction: neighbors.keySet()) {
            if(neighbors.get(direction).name().equals("empty")) {
                emptyNeighbors.addLast(direction);
            }
        }

        for(Direction direction: neighbors.keySet()) {
            if(neighbors.get(direction).name().equals("plip")) {
                plipNeighbors.addLast(direction);
            }
        }

        // Rule 1
        if(emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if(plipNeighbors.size() != 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }

        // Rule 3
        if(energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));

    }

    private Direction randomEntry(Deque<Direction> a) {
        int randomIndex = (int) (Math.random()*a.size());
        Direction toReturn = null;
        for(Direction item: a) {
            if(randomIndex > 0) {
                randomIndex--;
                continue;
            }
            toReturn = item;
        }
        return toReturn;
    }
}
