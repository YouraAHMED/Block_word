package planning;
/**
 * Classe qui permet de representer un planificateur A*
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.*;

public class AStarPlanner extends AbstractPlanner {
    /**
     * L'heuristique du probleme
     */
    private final Heuristic heuristique;

    /**
     * Constructeur de la classe
     *
     * @param initialState
     * @param actions
     * @param goal
     * @param heuristique
     */
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristique) {
        super(initialState, actions, goal);
        this.heuristique = heuristique;
    }

    /**
     * Methode qui permet de trouver un plan en utilisant l'algorithme A*
     *
     * @return un plan
     */
    private List<Action> astar() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();

        PriorityQueue<Map<Variable, Object>> opened = new PriorityQueue<>(new Comparator<Map<Variable, Object>>() {
            @Override
            public int compare(Map<Variable, Object> etat1, Map<Variable, Object> etat2) {
                Float d1 = value.get(etat1);
                Float d2 = value.get(etat2);
                return Double.compare(d1, d2);
            }
        });

        opened.add(this.initialState);
        father.put(this.initialState, null);
        distance.put(this.initialState, 0.f);
        value.put(this.initialState, this.heuristique.estimate(this.initialState));

        while (!opened.isEmpty()) {
            this.incrementNodeCount();
            Map<Variable, Object> instantiation = opened.poll();
            if (this.goal.isSatisfiedBy(instantiation)) {
                return BFSPlanner.get_bfs_plan(father, plan, instantiation);
            } else {
                opened.remove(instantiation);
                for (Action a : this.actions) {
                    if (a.isApplicable(instantiation)) {
                        Map<Variable, Object> newEtat = a.successor(instantiation);
                        if (!distance.containsKey(newEtat)) {
                            distance.put(newEtat, Float.POSITIVE_INFINITY);
                        }
                        if (distance.get(newEtat) > distance.get(instantiation) + a.getCost()) {
                            distance.put(newEtat, distance.get(instantiation) + a.getCost());
                            father.put(newEtat, instantiation);
                            plan.put(newEtat, a);
                            value.put(newEtat, distance.get(newEtat) + this.heuristique.estimate(newEtat));
                            opened.add(newEtat);
                        }
                    }
                }
            }
        }
        return null;

    }

    @Override
    public List<Action> plan() {
        return astar();
    }
}
