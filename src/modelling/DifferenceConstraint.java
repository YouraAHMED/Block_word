package modelling;

/**
 * Classe permettant de representer des contraintes de la forme v1!= v2
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint {
    /**
     * La premiere variable de la contrainte
     */
    private final Variable v1;
    /**
     * La deuxieme variable de la contrainte
     */
    private final Variable v2;

    /**
     * Constructeur de la classe DifferenceConstraint
     *
     * @param v1 la premiere variable de la contrainte
     * @param v2 la deuxieme variable de la contrainte
     */
    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        if (this.v1.equals(this.v2)) {
            return Set.of(this.v1);
        }
        return Set.of(this.v1, this.v2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> affectation) throws IllegalArgumentException {
        if (affectation == null || !affectation.containsKey(this.v1) || !affectation.containsKey(this.v2)) {
            throw new IllegalArgumentException("L'affectation ne contient pas toutes les variables de la contrainte");
        }
        return !affectation.get(this.v1).equals(affectation.get(this.v2));
    }

    @Override
    public String toString() {
        return this.v1 + " != " + this.v2;
    }
}
