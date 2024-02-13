package modelling;

/**
 * Classe permettant de representer les contraintes de la forme (v1 appartenant s1) => (v2 appartenant s2)
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.Map;
import java.util.Set;


public class Implication implements Constraint {
    /**
     * v1 la premiere variable
     */
    private final Variable v1;

    /**
     * V2 la deuxieme variable
     */
    private final Variable v2;

    /**
     * S1 le sous ensemble de v1
     */
    private final Set<Object> s1;

    /**
     * S2 le sous ensemble de v2
     */
    private final Set<Object> s2;

    /**
     * Constructeur de la classe Implication
     *
     * @param v1 la premiere variable
     * @param v2 la deuxieme variable
     * @param s1 le sous ensemble de v1
     * @param s2 le sous ensemble de v2
     */
    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        this.v1 = v1;
        this.s1 = s1;
        this.v2 = v2;
        this.s2 = s2;
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
        if (this.s1.contains(affectation.get(this.v1))) {
            return this.s2.contains(affectation.get(this.v2));
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + this.v1 + " in " + this.s1 + ") => (" + this.v2 + " in " + this.s2 + ")";
    }
}
