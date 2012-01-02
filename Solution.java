
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class Solution implements Cloneable {
    
    private double lambda;
    private InCycle inCycle;
    private OutCycle outCycle;
    private AllNodes allNodes;

    public Solution() {
        this.inCycle = new InCycle();
        this.outCycle = new OutCycle();
        this.allNodes = null;
        this.lambda = -1;
    }
    
    public Solution clone() {
        Solution solution = null;
        try {
            solution = (Solution) super.clone();

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return solution;
    }

    
    public Solution(AllNodes allNodes, double lambda) {
        this.lambda = lambda;
        this.allNodes = allNodes;
        this.inCycle = new InCycle(allNodes);
        this.outCycle = new OutCycle(allNodes);
        this.outCycle.findAllAffectedNodes(inCycle, allNodes);
    }
    public Solution(AllNodes allNodes) {
        this.lambda = 0;
        this.allNodes = allNodes;
        this.inCycle = new InCycle(allNodes);
        this.outCycle = new OutCycle(allNodes);
        this.outCycle.findAllAffectedNodes(inCycle, allNodes);
    }

    public AllNodes getAllNodes() {
        return allNodes;
    }

    public InCycle getInCycle() {
        return inCycle;
    }

    public double getLambda() {
        return lambda;
    }

    public OutCycle getOutCycle() {
        return outCycle;
    }
    
    public Tuple inc(Node insert) {
		
        double costSub=0;
        double costAdd=0;
        double min = Double.MAX_VALUE;
        double costDiff=0;
        Node n1;
        Node n2;
        Tuple result = new Tuple();

        for(int i=0; i<inCycle.getNodes().size(); i++) {

            if(i==inCycle.getNodes().size()-1) {
                    n2 = inCycle.getNodes().get(0);
            } else {
                    n2 = inCycle.getNodes().get(i+1);	
            }
            n1 = inCycle.getNodes().get(i);
            costSub = n1.computeDistance(n2);
            
            costAdd = insert.computeDistance(n1)+insert.computeDistance(n2);
            costDiff = costAdd-costSub;
            if(min>costDiff) {
                    min = costDiff;
                    result.setAllValues(n1, n2, insert, min);
            }   
        }
        return result;
    }
    
    public double dec(Node insert) {
        double result = 0;
        double distanceInsert;
        double distanceAffectedNode;
        for(Node n : this.outCycle.getNodes()) {
            distanceInsert = n.computeDistance(insert);
            distanceAffectedNode = n.computeDistance(n.getAffectedNode());
            if(distanceInsert < distanceAffectedNode) {
                result += distanceAffectedNode - distanceInsert;
            }
        }        

        return result;
    }
    public Tuple heuristique(Node n){
        Tuple result=new Tuple();
        Tuple temp = inc(n);
        double dec = dec(n);
        result.setCost(lambda*temp.getCost()-(1-lambda)*dec);
        result.setInsert(temp.getInsert());
        result.setN1(temp.getN1());
        result.setN2(temp.getN2());
        return result;
    }
    public void build() {

        Tuple min = new Tuple();

        while (min.getCost()<0 || this.inCycle.getNodes().size()<3) {
            min.setCost(Double.MAX_VALUE);
            for(Node n : this.outCycle.getNodes()) {
                Tuple t = this.heuristique(n);
                if(t.getCost()<min.getCost()) {
                    min.setAllValues(t.getN1(), t.getN2(), t.getInsert(), t.getCost());
                }
            }
            if((min.getCost()<0)||(this.inCycle.getNodes().size()<3 && min.getCost()!=Double.MAX_VALUE)){
                this.addNodeToCycle(min.getInsert(), this.inCycle.getNodes().indexOf(min.getN2()));
                this.outCycle.findAllAffectedNodes(inCycle, allNodes);
            }
        }		
    }
    public void addNodeToCycle(Node n, int position) {
        this.inCycle.addNode(n, position);
        this.outCycle.removeNode(n);
    }
    public void addNodeToCycle(Node n) {
        this.inCycle.getNodes().add(n);
        this.outCycle.removeNode(n);
    }
    public void removeNodeFromCycle(int number) {
        this.outCycle.getNodes().add(this.inCycle.getNodes().get(number));
        this.inCycle.removeNode(number);
    }
    public double evaluate() {
        return this.getInCycle().getCycleCost()+this.getOutCycle().getAffectationCost();
    }
}
