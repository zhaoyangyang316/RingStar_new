//TEST the changes
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class Cycle {
    
    protected ArrayList<Node> nodes;
    
    public Cycle() {
        
        this.nodes = new ArrayList<Node>();
    }
    public Cycle(AllNodes allNodes) {
        
        this.nodes = new ArrayList<Node>();
        for(Node n : allNodes.getAllNodes().values())
        this.nodes.add(n);
    }
    public void addNode(Node n, int position) {
        this.nodes.add(position, n);
    }
    public void removeNode(Node n) {
        this.nodes.remove(n);
    }
    public void removeNode(int position) {
        this.nodes.remove(position);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    
    
}
