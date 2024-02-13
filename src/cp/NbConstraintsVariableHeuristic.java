package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    /**
     * Ensemble de contraintes du probleme
     */
    private final Set<Constraint> constraints;

    /**
     * Boolean permettant de savoir si on préfère que les variables apparaisant dans le plus de contraintes ou dans le moins de contraintes
     */
    private final boolean plusOuMoins;

    /**
     * Constructeur de la classe
     *
     * @param constraints
     * @param plusOuMoins
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean plusOuMoins) {
        this.constraints = constraints;
        this.plusOuMoins = plusOuMoins;
    }

    @Override
    public Variable best(Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ensembleDomaines) {
        Variable best = null;
        int nbConstraints = 0;
        for (Variable variable : ensembleVariables) {
            int nbConstraintsVariableCourante = 0;
            for (Constraint constraint : this.constraints) {
                if (constraint.getScope().contains(variable)) { // Si la variable courante apparait dans la contrainte courante
                    nbConstraintsVariableCourante++;
                }
            }
            // Si on n'a pas encore de meilleure variable ou si on préfère les variables apparaisant dans le plus de contraintes et que la variable courante
            // apparait dans plus de contraintes que la meilleure variable courante ou si on préfère les variables apparaisant dans le moins de contraintes et
            // que la variable courante apparait dans moins de contraintes que la meilleure variable courante
            if (best == null || (this.plusOuMoins && nbConstraintsVariableCourante > nbConstraints) || (!this.plusOuMoins && nbConstraintsVariableCourante < nbConstraints)) {
                best = variable;
                nbConstraints = nbConstraintsVariableCourante;
            }

        }
        return best;
    }
}
