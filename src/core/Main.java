package core;

import modelling.Variable;
import planning.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        System.out.println("Veuillez entrer le nombre de variables : ");
        Scanner scanner = new Scanner(System.in);
        int variables = scanner.nextInt();

        System.out.println("Veuillez entrer le nombre d'actions : ");
        int numberOfActions = scanner.nextInt();

        // Fermez le scanner pour libérer les ressources
        scanner.close();

        RandomPlanner problem = RandomPlanner.generateRandomPlanningProblemBoolean(variables, numberOfActions);
        Planner dfs = new DFSPlanner(problem.getInitialState(), problem.getActions(), problem.getGoal());
        Planner bfs = new BFSPlanner(problem.getInitialState(), problem.getActions(), problem.getGoal());
        Planner dijkstra = new DijkstraPlanner(problem.getInitialState(), problem.getActions(), problem.getGoal());
        Heuristic heuristic = new Heuristic() {
            @Override
            public float estimate(Map<Variable, Object> etat) {
                return 0;
            }
        };
        Planner astar = new AStarPlanner(problem.getInitialState(), problem.getActions(), problem.getGoal(), heuristic);

        List<AbstractPlanner> planners = new ArrayList<>();
        //planners.add((AbstractPlanner) dfs);
        //planners.add((AbstractPlanner) bfs);
        //planners.add((AbstractPlanner) dijkstra);
        planners.add((AbstractPlanner) astar);


        System.out.println("Nombre d'actions : " + problem.getActions().size());
        System.out.println("Nombre de variables : " + variables);
        System.out.println("\n\n");

        for (AbstractPlanner planner : planners) {
            System.out.println("Planner : " + planner.getClass().getSimpleName());
            System.out.println("Résolution...");

            long startTime = System.nanoTime();
            List<Action> actions = planner.plan();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);

            System.out.println("Durée totale : " + duration / 1000000 + " milliseconde");

            if (actions != null) {
                System.out.println("Nombre d'actions dans le plan : " + actions.size());
                System.out.println("Coût total du plan : " + AbstractPlanner.computeTotalCost(actions));
            } else {
                System.out.println("Aucun plan trouvé.");
            }

            System.out.println("Nombre de nœuds visités : " + planner.getNodeCount());
            System.out.println("\n\n\n");
        }


    }
}