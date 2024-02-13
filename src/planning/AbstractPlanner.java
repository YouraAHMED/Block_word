package planning;

/**
 * Classe abstraite qui permet de representer un planificateur
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractPlanner implements Planner {

    /**
     * l'etat initial du probleme
     */
    protected Map<Variable, Object> initialState;

    /**
     * L'ensemble des actions du probleme
     */
    protected Set<Action> actions;

    /**
     * l'objectif du probleme
     */
    protected Goal goal;

    /**
     * le nombre de noeuds explorés
     */
    protected int sonde;

    /**
     * Constructeur de la classe
     *
     * @param initialState
     * @param actions
     * @param goal
     */

    public AbstractPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;

    }

    /**
     * Methode qui compte le cout total du plan
     *
     * @param actions
     * @return
     */

    public static int computeTotalCost(List<Action> actions) {
        int totalCost = 0;
        for (Action action : actions) {
            totalCost += action.getCost();
        }
        return totalCost;
    }

    /**
     * Methode qui permet de recuperer l'etat initial
     *
     * @return l'etat initial
     */


    public Map<Variable, Object> getInitialState() {
        if (this.initialState != null) {
            return this.initialState;
        }
        return null;
    }

    /**
     * Methode qui permet de recuperer l'ensemble des actions
     *
     * @return l'ensemble des actions
     */

    public Set<Action> getActions() {
        return this.actions;
    }

    /**
     * Methode qui permet de recuperer l'objectif
     *
     * @return l'objectif
     */

    public Goal getGoal() {
        if (this.goal != null) {
            return this.goal;
        }
        return null;
    }



    /**
     * Methode qui permet de recuperer le nombre de noeuds
     *
     * @return
     */
    public int getNodeCount() {
        return this.sonde;
    }

    /**
     * Methode permettant d'incrementer le nombre de noeuds
     * @return
     */
    public int incrementNodeCount() {
        return this.sonde++;
    }

    /**
     * Methode qui permet de remettre a zero le nombre de noeuds
     *
     * @return
     */
    public int resetNodeCount() {
        return this.sonde = 0;
    }

    @Override
    public String toString() {
        return "AbstractPlanner{\n\n" + getClass().getSimpleName() + "\n" + "\n" +
                "initialState=" + initialState + "\n" + "\n" +
                ", actions=" + actions + "\n" + "\n" +
                ", goal=" + goal + "\n" + "\n" +
                '}';
    }
}

