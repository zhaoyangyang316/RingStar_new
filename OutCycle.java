
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class OutCycle extends Cycle {
    
    public OutCycle() {
        
        super();
    }
    public OutCycle(AllNodes allNodes) {
        
        super(allNodes);
        if(this.nodes.size()>0) {
            this.nodes.remove(0);
        }
    }
    public double getAffectationCost(){
        double result=0;
        for(Node n : this.nodes){
            result+=n.computeDistance(n.getAffectedNode());
        }
        return result;
    }
    public void findAllAffectedNodes(InCycle inCycle, AllNodes allNodes) {
        for(Node n : this.nodes) {
            n.findAffectedNode(inCycle, allNodes);
        }
    }
}
