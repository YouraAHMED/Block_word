package planning;

/**
 * Interface Goal
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.Map;

public interface Goal {

    /**
     * Methode permettant de savoir si l'etat est satisfait
     *
     * @param etat
     * @return true si l'etat est satisfait, false sinon
     */

    boolean isSatisfiedBy(Map<Variable, Object> etat);

    Map<Variable, Object> getInstanceP();

}
