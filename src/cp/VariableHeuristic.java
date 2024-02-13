package cp;

import modelling.Variable;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public interface VariableHeuristic {

    /**
     * Methode permettant de retourner la meilleure variable au sens de l'heuristique
     *
     * @param ensembleVariables
     * @param ensembleDomaines
     * @return la meilleure variable
     */
    Variable best(Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ensembleDomaines);
}
