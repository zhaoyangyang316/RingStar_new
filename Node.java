
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class Node {
    
    private int number;
    private int x;
    private int y;
    private Node affectedNode;
    private ArrayList<Node> nodesDistances;

    public Node(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
        affectedNode = null;
        nodesDistances = new ArrayList<Node>();
    }

    public int getNumber() {
        return number;
    }    
    public Node getAffectedNode() {
        return affectedNode;
    }

    public void setAffectedNode(Node affectedNode) {
        this.affectedNode = affectedNode;
    }

    public ArrayList<Node> getNodesDistances() {
        return nodesDistances;
    }

    public void setNodesDistances(ArrayList<Node> nodesDistances) {
        this.nodesDistances = nodesDistances;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public double computeDistance(Node n) {  
        double a = Math.pow(Math.abs(this.getX()-n.getX()), 2);
        double b = Math.pow(Math.abs(this.getY()-n.getY()), 2);
        return Math.sqrt(a+b);
    }
    public void sortNodesDistances(HashMap<Integer,Node> nodes) {
        for(Node n : nodes.values()) {
            this.nodesDistances.add(n);
        }
        Collections.sort(this.nodesDistances, new Comparator(){
			  
            public int compare(Object o1, Object o2) {
                Node p1 = (Node) o1;
                Node p2 = (Node) o2;
                Double distanceP1 = computeDistance(p1);
                Double distanceP2 = computeDistance(p2);
               return distanceP1.compareTo(distanceP2);
            }
        });
        if(this.nodesDistances.size()>0) {
            this.nodesDistances.remove(0);
        }
    }
    public void findAffectedNode(InCycle inCycle, AllNodes allNodes) {
        Node n = allNodes.getAllNodes().get(this.getNumber());
        for(Node neighbor : n.getNodesDistances()) {
            if(inCycle.containsNode(neighbor.getNumber())) {
                this.affectedNode = neighbor;
                break;
            }
        }
    }
    
    
    
    
    
    
}
