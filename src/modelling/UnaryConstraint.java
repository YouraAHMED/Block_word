package modelling;

/**
 * Classe permettant de representer les contraintes de la forme v appartenant à s.
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.Map;
import java.util.Set;


public class UnaryConstraint implements Constraint {
    /**
     * v la variable de la contrainte.
     */
    private final Variable v;

    /**
     * S le sous ensemble de v.
     */
    private final Set<Object> s;

    /**
     * Constructeur de la classe UnaryConstraint
     *
     * @param v la variable
     * @param s le sous ensemble de
     */
    public UnaryConstraint(Variable v, Set<Object> s) {
        this.v = v;
        this.s = s;
    }

    @Override
    public Set<Variable> getScope() {

        return Set.of(this.v);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> affectation) throws IllegalArgumentException {

        if (affectation == null || !affectation.containsKey(this.v)) {

            throw new IllegalArgumentException("L'affectation ne contient pas toutes les variables de la contrainte");
        }

        return this.s.contains(affectation.get(this.v));
    }

    @Override
    public String toString() {
        return this.v + " in " + this.s;
    }
}
