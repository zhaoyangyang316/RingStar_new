
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
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
public class AllNodes implements Cloneable {
    
    private HashMap<Integer, Node> allNodes;

    public AllNodes(String file) {
        
        allNodes = new HashMap<Integer,Node>();
        this.readFile(file);
        this.computeDistances();
    }
    public AllNodes(AllNodes allNodes) {
        this.allNodes = new HashMap(allNodes.getAllNodes());
    }

    public HashMap<Integer, Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(HashMap<Integer, Node> allNodes) {
        this.allNodes = allNodes;
    }


    public void addNode(Node n) {
        this.allNodes.put(n.getNumber(), n);
    }  

    public void readFile(String path) {
        
        FileReader file = null;
        try {
            file = new FileReader(new File(path));
            BufferedReader buffer = new BufferedReader(file);
            String line;
            String[] data = new String[3];
            boolean sectionChange = false;
            while((line=buffer.readLine())!=null) {
                    if(sectionChange) {
                            
                            StringTokenizer st = new StringTokenizer(line);
                            int i=0;
                            while(st.hasMoreTokens()) {
                                    data[i] = st.nextToken();
                                    i++;
                            }
                            Node n = new Node(Integer.valueOf(data[0].trim()), Integer.valueOf(data[1].trim()), Integer.valueOf(data[2].trim()));
                            this.addNode(n);
                    } else {	
                            if(line.compareTo("NODE_COORD_SECTION")==0) {
                                    sectionChange = true;
                            }
                    }	
            }
        } catch (IOException ex) {
            Logger.getLogger(AllNodes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(AllNodes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void computeDistances() {
        for(Node n : this.allNodes.values()) {
            n.sortNodesDistances(allNodes);
        }
    }
    
    @Override
    public AllNodes clone(){
        AllNodes n = null;
        try {
            n = (AllNodes) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AllNodes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
