package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeuristicMACSolver extends MACSolver {

    /**
     * Heuristique de selection des variables
     */
    private final VariableHeuristic variableHeuristic;

    /**
     * Heuristique de selection des valeurs
     */
    private final ValueHeuristic valueHeuristic;

    /**
     * Constructeur de la classe
     *
     * @param variables
     * @param constraints
     * @param variableHeuristic
     * @param valueHeuristic
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }


    @Override
    protected Map<Variable, Object> macSolver(Map<Variable, Object> instantiationPartielle, Set<Variable> variablesNonInstancie, Map<Variable, Set<Object>> domains) {
        //conditions d'arrêt de la fonction recursive
        if (variablesNonInstancie.isEmpty()) {
            return instantiationPartielle;
        }

        if (!getArcConsistency().ac1(domains)) {
            return null;
        }
        //choisir une variable non encore instancié
        Variable variable = this.variableHeuristic.best(variablesNonInstancie, domains);
        variablesNonInstancie.remove(variable);
        //choisir une valeur dans le domaine de la variable choisie
        List<Object> domaine = this.valueHeuristic.ordering(variable, domains.get(variable));
        return solutionComplete(instantiationPartielle, variablesNonInstancie, domains, variable, domaine);
    }


}
