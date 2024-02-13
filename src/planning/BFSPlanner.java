package planning;

/**
 * Classe representant un planificateur BFS
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.*;

public class BFSPlanner extends AbstractPlanner {

    /**
     * Constructeur de la classe
     *
     * @param initialState etat initial
     * @param actions      ensemble des actions possibles
     * @param goal         but à atteindre
     */
    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        super(initialState, actions, goal);
    }

    /**
     * Methode auxiliaire qui permet de reconstruire un plan
     *
     * @return la liste des actions pour ramener au but
     */
    public static List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> parents, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        List<Action> bfs_plan = new LinkedList<>();
        Map<Variable, Object> current = goal;

        while (parents.get(current) != null) {
            bfs_plan.add(plan.get(current));
            current = parents.get(current);

        }

        Collections.reverse(bfs_plan);
        return bfs_plan;
    }

    /**
     * Methode auxiliaire qui permet de trouver un plan
     *
     * @return la liste des actions pour ramener au but
     */
    private List<Action> bfs(Map<Variable, Object> etat, Map<Map<Variable, Object>, Action> plan) {
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(etat);

        Queue<Map<Variable, Object>> opened = new LinkedList<>();
        opened.add(etat);

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(etat, null);

        if (this.goal.isSatisfiedBy(etat)) {
            return new LinkedList<Action>();
        }

        while (!opened.isEmpty()) {
            Map<Variable, Object> current = opened.poll();
            this.incrementNodeCount();
            closed.add(current);
            for (Action action : this.actions) {
                if (action.isApplicable(current)) {
                    Map<Variable, Object> newEtat = action.successor(current);
                    if (!closed.contains(newEtat) && !opened.contains(newEtat)) {
                        this.incrementNodeCount();
                        father.put(newEtat, current);
                        plan.put(newEtat, action);
                        if (this.goal.isSatisfiedBy(newEtat)) {
                            return get_bfs_plan(father, plan, newEtat);
                        }
                        opened.add(newEtat);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Action> plan() {

        return bfs(this.initialState, new HashMap<Map<Variable, Object>, Action>());
    }

}


