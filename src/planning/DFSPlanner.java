package planning;

/**
 * Classe representant un planificateur DFS
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.*;

public class DFSPlanner extends AbstractPlanner {

    /**
     * Constructeur de la classe
     *
     * @param initialState etat initial
     * @param actions      ensemble des actions possibles
     * @param goal         but à atteindre
     */
    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        super(initialState, actions, goal);
    }

    /**
     * Methode auxiliaire qui permet de trouver un plan grâce à l'algorithme BFS
     *
     * @return la liste des actions pour ramener au but
     */
    public List<Action> dfs(Map<Variable, Object> etat, LinkedList<Action> plan, List<Map<Variable, Object>> closed) {
        if (this.goal.isSatisfiedBy(etat)) {
            Collections.reverse(plan);
            return plan;
        }
        closed.add(etat);
        this.incrementNodeCount();
        for (Action a : this.actions) {
            if (a.isApplicable(etat)) {
                Map<Variable,Object> next = a.successor(etat);
                if (!closed.contains(next)) {
                    plan.push(a);
                    closed.add(next);
                    List<Action> result = dfs(next, plan, closed);
                    if (result != null) {
                        return result;
                    }
                    plan.pop();
                }
            }
        }
        return null;
    }


    @Override
    public List<Action> plan() {
        return dfs(this.initialState, new LinkedList<Action>(), new ArrayList<Map<Variable, Object>>());
    }


}
