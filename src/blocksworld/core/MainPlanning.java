package blocksworld.core;



import blocksworld.BlockWorldAction;
import blocksworld.BlockWorldVariable;
import blocksworld.HeuristiPlanner1;
import blocksworld.HeuristicPlanner2;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import modelling.Variable;
import planning.*;

import java.util.*;


public class MainPlanning {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur le nombre de blocs et de piles
        System.out.println("Entrez le nombre de blocs : ");
        int nbBlocks = scanner.nextInt();
        System.out.println("Entrez le nombre de piles : ");
        int nbPiles = scanner.nextInt();

        // Générer une pile initiale aléatoire
        List<ArrayList<Integer>> pilesInitiales = PlannerWrapper.generateRandomInitialPiles(nbBlocks, nbPiles);

        // Créer une instance de BlockWorldVariable avec le nombre de blocs et de piles spécifiés
        BlockWorldVariable variable = new BlockWorldVariable(nbBlocks, nbPiles);

        // Instanciation correspondant à l'état initial
        Map<Variable, Object> etatInitial = PlannerWrapper.instancierEtat(pilesInitiales, nbBlocks, nbPiles);

        System.out.println("Etat initial : " + etatInitial);

        // Exemple d'état final (partiellement spécifié)
        Map<Variable, Object> instatiationPartielle = new HashMap<>();
        instatiationPartielle.put(variable.getOnVariable(2), 0);
        instatiationPartielle.put(variable.getOnVariable(1), 2);
        instatiationPartielle.put(variable.getOnVariable(4), 5);
        instatiationPartielle.put(variable.getOnVariable(5), 3);

        Goal etatFinal = new BasicGoal(instatiationPartielle);

        BlockWorldAction action = new BlockWorldAction(nbBlocks, nbPiles);

        // Demander à l'utilisateur le choix du planificateur
        System.out.println("Choisissez le planificateur (astar, dijkstra, bfs, dfs) : ");
        String choixPlanificateur = scanner.next();

        AbstractPlanner planner;
        if ("astar".equalsIgnoreCase(choixPlanificateur)) {
            // Demander à l'utilisateur le choix de l'heuristique
            System.out.println("Choisissez l'heuristique (1 ou 2) pour AStar : ");
            int choixHeuristique = scanner.nextInt();

            if (choixHeuristique == 1) {
                planner = new AStarPlanner(etatInitial, action.getAllActions(), etatFinal, new HeuristiPlanner1(etatFinal, variable));
            } else if (choixHeuristique == 2) {
                planner = new AStarPlanner(etatInitial, action.getAllActions(), etatFinal, new HeuristicPlanner2(etatFinal, variable));
            } else {
                System.out.println("Choix d'heuristique non valide. Terminé.");
                return;
            }
        } else if ("dijkstra".equalsIgnoreCase(choixPlanificateur)) {
            planner = new DijkstraPlanner(etatInitial, action.getAllActions(), etatFinal);
        } else if ("bfs".equalsIgnoreCase(choixPlanificateur)) {
            planner = new BFSPlanner(etatInitial, action.getAllActions(), etatFinal);
        } else if ("dfs".equalsIgnoreCase(choixPlanificateur)) {
            planner = new DFSPlanner(etatInitial, action.getAllActions(), etatFinal);
        } else {
            System.out.println("Choix de planificateur non valide. Terminé.");
            return;
        }

        // Créer une instance de PlannerWrapper avec les paramètres nécessaires
        PlannerWrapper plannerWrapper = new PlannerWrapper(choixPlanificateur, etatInitial, planner, variable, nbBlocks);

        // Exécuter le planificateur
        plannerWrapper.run();


    }




    /**
     *
     * @param etatInitial
     * @param variable
     * @param n
     * @return
     */
    static BWState<Integer> makeBWState(Map<Variable, Object> etatInitial, BlockWorldVariable variable, int n) {
                // Building state
                BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
                for (int b = 0; b < n; b++) { //pour chaque block
                    Variable onB = variable.getOnVariable(b);
                    Integer underValue = (Integer) etatInitial.get(onB);
                    int under = (underValue != null) ? underValue.intValue() : -1;
                    if (under >= 0) { // if the value is a block (as opposed to a stack)
                        builder.setOn(b, under);
                    }
                }
                return builder.getState();
            }


}

