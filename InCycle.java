
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class InCycle extends Cycle {
    
    public InCycle() {  
        super();
    }
    public InCycle(AllNodes allNodes) {
        //super(allNodes);
        if(allNodes.getAllNodes().size()>0) {
            this.nodes.add(allNodes.getAllNodes().get(1));
        }
    }
    public double getCycleCost() {
        double cycleCost = 0;

        for(int i=0; i<nodes.size()-1; i++) {
                cycleCost+=nodes.get(i).computeDistance(nodes.get(i+1));
        }
        if(nodes.size() >1) {
                cycleCost+=nodes.get(nodes.size()-1).computeDistance(nodes.get(0));
        }
        return cycleCost;
    }
    public boolean containsNode(int number) {
        for(Node n : this.nodes) {
            if(n.getNumber() == number) {
                return true;
            }
        }
        return false;
    }
    public String toString() {
        String result = "";
        for(Node n : this.nodes) {
            result+=n.getNumber()+" "+n.getX()+" "+n.getY()+"\n";
        }
        return result;
    }
}
