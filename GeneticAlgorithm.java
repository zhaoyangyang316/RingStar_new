import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GeneticAlgorithm {

    private static double[] lambdas;
    private List<Solution> population;
    private AllNodes allNodes;
    private double alpha;

    public GeneticAlgorithm(String path, double[] lambdas) throws IOException {
        this.lambdas = lambdas;
        this.allNodes = new AllNodes(path);
        this.population = new ArrayList<Solution>();	
        initializePopulation();
    }

    public List<Solution> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Solution> population) {
        this.population = population;
    }

    public void initializePopulation() throws IOException {
        for(int i=0; i<lambdas.length; i++) {
                initializeOneIndividual(lambdas[i]);
        }
    }

    public void initializeOneIndividual(double lambda) {
        Solution solution = new Solution(allNodes.clone(), lambda);
        solution.build();
        population.add(solution);
    }

    /**
     * Cette fonction permet d'évaluer la pertinence d'un cycle plus la valeur 
     * retournée est grande moins la solution est bonne.
     * @param c
     * @return
     */
    public double evaluate(Solution c){
        return c.evaluate();
    }

    /**
     * Effectue un croisement entre deux cycles; le nouveau cycle obtenu est composé de la
     * moitié des sommets de chacun des cycles spécifiés
     * @param c1
     * @param c2
     * @return
     */
    public Solution croisement(Solution s1, Solution s2){

        Solution result = new Solution(allNodes.clone());
        for(int i=0; i<s1.getInCycle().getNodes().size()/2; i++) {
            result.addNodeToCycle(s1.getInCycle().getNodes().get(i), i);
        }
        int i=0;
        int nbreNoeudToInsert = s2.getInCycle().getNodes().size()/2;
        while(i<s2.getInCycle().getNodes().size() && nbreNoeudToInsert>0){
                if(!result.getInCycle().getNodes().contains(s2.getInCycle().getNodes().get(i))){
                    result.addNodeToCycle(s2.getInCycle().getNodes().get(i), result.getInCycle().getNodes().size());    
                    nbreNoeudToInsert--;
                }
                i++;
        }		
        result.getOutCycle().findAllAffectedNodes(result.getInCycle(), allNodes);
        return result;
    }
	
	
    /**
     * Selectionne les n meilleurs individus de la liste d'individu spécifiée
     * @param liste
     * @param n
     * @return
     */
    public void selection(int n){
        Collections.sort(this.population, new Comparator<Solution>(){
            @Override
            public int compare(Solution s1, Solution s2) {
                Double eval1 = s1.evaluate();
                Double eval2 = s2.evaluate();
                return eval1.compareTo(eval2);
            }
        });
        //Collections.reverse(solutions);//trie par ordre decroissant
        //on recupere les n premier elements
        if(this.population.size()>=n){
            this.population = this.population.subList(0, n);
        }
    }
	
    /**
     * Mutation de l'individu spécifié : 1 noeuds du cycle à muter est remplacé par un noeud pris au
     * hasard dans la liste des noeuds
     * @param cycleToMutate
     * @param possibleValues
     * @return
     */        
    public Solution mutation(Solution solutionToMutate){
        Solution result = solutionToMutate.clone();
        int nodeNumberToRemove = random(0, result.getInCycle().getNodes().size());
        int nodeNumberToInsert;
        if(result.getOutCycle().getNodes().size() > 0) {
            result.removeNodeFromCycle(nodeNumberToRemove);
            nodeNumberToInsert = random(0,result.getOutCycle().getNodes().size());
            result.addNodeToCycle(result.getOutCycle().getNodes().get(nodeNumberToInsert));
            result.getOutCycle().findAllAffectedNodes(result.getInCycle(), allNodes);
        }
        return result;
    }
    public void algoGenetique(){
        ArrayList<Solution> tempPopulation = new ArrayList<Solution>();
        int taillePopulation = 100;
        int maxUpdatePopulation = 30;
        int nbreIteration = 10; 
        int sizeOfInitialPopulation = this.population.size();
        int nbreMutations;
        int nbreCroisements;

        int i = 0;
        while(i<nbreIteration){
            nbreMutations = Math.min(sizeOfInitialPopulation, maxUpdatePopulation);
            nbreCroisements = Math.min(sizeOfInitialPopulation, maxUpdatePopulation);
            for(Solution s : this.population){
                if(nbreMutations!=0){
                    tempPopulation.add(mutation(population.get(random(0,population.size()))));
                    nbreMutations--;
                }
                if(nbreCroisements!=0){
                    //tempPopulation.add(croisement(population.get(random(0,population.size())),population.get(random(0,population.size()))));
                    nbreCroisements--;
                }
            }
            this.population.addAll(tempPopulation);
            this.selection(taillePopulation);
            System.out.println(this.population.get(0)+" "+this.population.get(0).evaluate());
            i++;
        }
        this.selection(1);
    }

    public int random(int min, int max) {
        return (int)(Math.random()*(max-min)+min);
    }
}
