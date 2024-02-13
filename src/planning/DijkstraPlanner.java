package planning;

/**
 * Classe representant un planificateur Dijkstra
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.*;

public class DijkstraPlanner extends AbstractPlanner {

    /**
     * Constructeur de la classe
     *
     * @param initialState etat initial
     * @param actions      ensemble des actions possibles
     * @param goal         but à atteindre
     */
    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        super(initialState, actions, goal);
    }

    public List<Action> Dijkstra(Map<Variable, Object> etat, Map<Map<Variable, Object>, Action> plan) {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();

        PriorityQueue<Map<Variable, Object>> opened = new PriorityQueue<>(new Comparator<Map<Variable, Object>>() {
            @Override
            public int compare(Map<Variable, Object> etat1, Map<Variable, Object> etat2) {
                Double d1 = distance.get(etat1);
                Double d2 = distance.get(etat2);
                return Double.compare(d1, d2);
            }
        });

        PriorityQueue<Map<Variable, Object>> goals = new PriorityQueue<>(new Comparator<Map<Variable, Object>>() {
            @Override
            public int compare(Map<Variable, Object> etat1, Map<Variable, Object> etat2) {
                Double d1 = distance.get(etat1);
                Double d2 = distance.get(etat2);
                return Double.compare(d1, d2);
            }
        });

        this.resetNodeCount();
        father.put(etat, null);
        distance.put(etat, 0.0);
        opened.add(etat);

        while (!opened.isEmpty()) {
            Map<Variable, Object> instantiation = opened.poll();
            if (this.goal.isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
            }
            for (Action a : this.actions) {

                if (a.isApplicable(instantiation)) {

                    Map<Variable, Object> newEtat = a.successor(instantiation);
                    distance.computeIfAbsent(newEtat, (K) ->{
                        this.incrementNodeCount();
                        return Double.POSITIVE_INFINITY;
                    });
                    if (distance.get(newEtat) > distance.get(instantiation) + a.getCost()) {

                        distance.put(newEtat, distance.get(instantiation) + a.getCost());
                        father.put(newEtat, instantiation);
                        plan.put(newEtat, a);
                        opened.add(newEtat);
                    }
                }
            }

        }
        if (goals.isEmpty()) {
            return null;
        } else {

            return get_dijsktra_plan(father, plan, goals, distance);
        }
    }

    /**
     * Methode auxiliaire qui permet de reconstruire un plan
     *
     * @param father
     * @param plan
     * @param goals
     * @param distance
     * @return la liste des actions pour ramener au but
     */
    public List<Action> get_dijsktra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, PriorityQueue<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Double> distance) {
        List<Action> dijkstra_plan = new LinkedList<>();
        Map<Variable, Object> goal = goals.poll();
        while (father.get(goal) != null) {
            dijkstra_plan.add(0, plan.get(goal));
            goal = father.get(goal);
        }
        return dijkstra_plan;
    }

    @Override
    public List<Action> plan() {
        return Dijkstra(this.initialState, new HashMap<Map<Variable, Object>, Action>());
    }


}
