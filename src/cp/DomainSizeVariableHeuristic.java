package cp;

import modelling.Variable;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DomainSizeVariableHeuristic implements VariableHeuristic {

    /**
     * Boolean permettant de savoir si on préfère que les variables apparaisant dans le plus grand domaine ou dans le plus petit domaine
     */
    private final boolean grandOuPetit;

    /**
     * Constructeur de la classe
     *
     * @param grandOuPetit
     */
    public DomainSizeVariableHeuristic(boolean grandOuPetit) {
        this.grandOuPetit = grandOuPetit;
    }

    @Override
    public Variable best(Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ensembleDomaines) {
        Variable best = null;
        int domainSize = 0;
        for (Variable variable : ensembleVariables) {
            int domainSizeVariableCourante = ensembleDomaines.get(variable).size();

            if (best == null || (this.grandOuPetit && domainSizeVariableCourante > domainSize) || (!this.grandOuPetit && domainSizeVariableCourante < domainSize)) {
                best = variable;
                domainSize = domainSizeVariableCourante;
            }
        }
        return best;
    }
}
