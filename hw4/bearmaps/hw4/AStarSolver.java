package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;


import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ExtrinsicMinPQ<Vertex> Fringe = new ArrayHeapMinPQ<Vertex>();
    private Map<Vertex,Double> distTo = new HashMap<Vertex,Double>();
    private Map<Vertex,Vertex> edgeTo = new HashMap<Vertex,Vertex>();
    private SolverOutcome outcome;
    private List<Vertex> solution = new ArrayList<Vertex>();
    private double solutionWeight;
    private int numStatesExplored = 0;
    private double timeSpent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        Fringe.add(start,input.estimatedDistanceToGoal(start,end));
        distTo.put(start,0.0);
        while(Fringe.size()>0 && !Fringe.getSmallest().equals(end) && explorationTime()<=timeout) {
            Vertex p = Fringe.removeSmallest();
            numStatesExplored++;
            for(WeightedEdge<Vertex> edge: input.neighbors(p)) {
                relax(edge,input,end);
            }
        }
        if(Fringe.size()==0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if(Fringe.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            Vertex temp = end;
            while(!temp.equals(start)) {
                solution.add(0,temp);
                temp = edgeTo.get(temp);
            }
            solution.add(0,start);
            solutionWeight = distTo.get(end);
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }
        timeSpent = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        if(!distTo.containsKey(q)) {
            distTo.put(q,Double.POSITIVE_INFINITY);
        }
        if(!edgeTo.containsKey(q)) {
            edgeTo.put(q,p);
        }
        double w = e.weight();
        if(distTo.get(p)+w<distTo.get(q)) {
            distTo.replace(q,distTo.get(p)+w);
            edgeTo.replace(q,p);
            if(Fringe.contains(q)) {
                Fringe.changePriority(q,distTo.get(q)+input.estimatedDistanceToGoal(q,end));
            } else {
                Fringe.add(q,distTo.get(q)+input.estimatedDistanceToGoal(q,end));
            }
        }
    }


    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}

