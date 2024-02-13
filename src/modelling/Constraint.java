package modelling;
/**
 * Interface Constraint
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.Map;
import java.util.Set;

public interface Constraint {
    /**
     * Methode permettant de recuperer l'ensemble des variables de la contrainte
     *
     * @return l'ensemble des variables de la contrainte
     */
    Set<Variable> getScope();

    /**
     * Methode permettant de savoir si la contrainte est satisfaite ou non
     *
     * @param affectation l'affectation des variables
     * @return true si la contrainte est satisfaite, false sinon
     */
    boolean isSatisfiedBy(Map<Variable, Object> affectation) throws IllegalArgumentException;
}
