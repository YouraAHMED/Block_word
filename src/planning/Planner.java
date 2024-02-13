package planning;

/**
 * Interface Planner
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Planner {

    /**
     * Methode permettant de trouver un plan pour atteindre le but
     *
     * @return une liste d'actions qui permettent d'atteindre le but
     */
    List<Action> plan();

    /**
     * Methode permettant de retourner l'état initial
     *
     * @return l'état initial
     */
    Map<Variable, Object> getInitialState();

    /**
     * Methode permettant de retourner l'ensemble des actions possibles
     *
     * @return l'ensemble des actions possibles
     */
    Set<Action> getActions();

    /**
     * Methode permettant de retourner le but à atteindre
     *
     * @return le but à atteindre
     */
    Goal getGoal();


}
