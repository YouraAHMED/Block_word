package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.Map;
import java.util.Set;

public abstract class AbstractSolver implements Solver {

    /**
     * ensemble des variables du probleme
     */
    protected Set<Variable> variables;

    /**
     * ensemble des contraintes du probleme
     */
    protected Set<Constraint> constraints;

    /**
     * Constructeur de la classe
     *
     * @param variables
     * @param constraints
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    /**
     * Methode permettant de savoir si une affectation satisfait toutes les contraintes qui portent sur des variables instanciees dans l'affectation
     *
     * @param affectation
     * @return true si l'affectation satisfait toutes les contraintes, false sinon
     */
    public boolean isConsistent(Map<Variable, Object> affectation) {
        if (affectation == null) {
            return false;
        }

        for (Constraint constraint : this.constraints) {
            if (constraint.getScope().stream().allMatch(affectation::containsKey)) {
                if (!constraint.isSatisfiedBy(affectation)) {
                    return false;
                }
            }
        }
        return true;
    }
}
