package planning;
/**
 * Interface Action
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.Map;

public interface Action {
    /**
     * Methode permettant de savoir si l'action est applicable dans l'état courant
     *
     * @param etat
     * @return true si l'action est applicable, false sinon
     */
    boolean isApplicable(Map<Variable, Object> etat);

    /**
     * Methode permettant de calculer le nouvel état
     *
     * @param etat
     * @return le nouvel état
     */
    Map<Variable, Object> successor(Map<Variable, Object> etat);

    /**
     * Methode permettant de calculer le cout de l'action
     *
     * @return le cout de l'action
     */
    int getCost();
}
