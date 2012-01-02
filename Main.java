
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benoit
 */
public class Main {
    
    public static void main(String [ ] args) throws IOException {
        
        double[] lambdas = {0.1, 0.3, 0.5, 0.7, 0.9};
        String path = "data/test.tsp";
        GeneticAlgorithm g = new GeneticAlgorithm(path, lambdas);
        g.algoGenetique();
        System.out.println(g.getPopulation().get(0)+" "+"et\n"+g.getPopulation().get(0).evaluate());
    }
}
