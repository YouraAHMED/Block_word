package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.*;

public class MACSolver extends AbstractSolver {

    private final ArcConsistency arcConsistency;

    public MACSolver(Set<Variable> variable, Set<Constraint> contraintes) {
        super(variable, contraintes);
        this.arcConsistency = new ArcConsistency(contraintes);
    }


    public ArcConsistency getArcConsistency() {
        return arcConsistency;
    }


    protected Map<Variable, Object> macSolver(Map<Variable, Object> instantiationPartielle, Set<Variable> variablesNonInstancie, Map<Variable, Set<Object>> domaines) {
        //conditions d'arrêt de la fonction recursive
        if (variablesNonInstancie.isEmpty()) {
            return instantiationPartielle;
        }

        if (!getArcConsistency().ac1(domaines)) {
            return null;
        }
        //choisir une variable encore instancié
        Variable variable = variablesNonInstancie.iterator().next();
        variablesNonInstancie.remove(variable);
        //choisir une valeur dans le domaine de la variable choisie
        Set<Object> domaine = domaines.get(variable);
        return solutionComplete(instantiationPartielle, variablesNonInstancie, domaines, variable, domaine);
    }

    /**
     * Methode permettant de trouver une solution au probleme
     *
     * @param instantiationPartielle
     * @param variablesNonInstancie
     * @param domaines
     * @param variable
     * @param domaine
     * @return la solution du probleme
     */
    protected Map<Variable, Object> solutionComplete(Map<Variable, Object> instantiationPartielle, Set<Variable> variablesNonInstancie, Map<Variable, Set<Object>> domaines, Variable variable, Iterable<Object> domaine) {
        for (Object value : domaine) {
            Map<Variable, Object> newInstantiation = new HashMap<>(instantiationPartielle);
            newInstantiation.put(variable, value);
            if (isConsistent(newInstantiation)) {
                Map<Variable, Set<Object>> newDomains = new HashMap<>(domaines);
                newDomains.put(variable, new HashSet<>(Collections.singletonList(value)));
                Map<Variable, Object> res = macSolver(newInstantiation, variablesNonInstancie, newDomains);
                if (res != null) {
                    return res;
                }
            }
        }
        variablesNonInstancie.add(variable);
        return null;
    }


    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable variable : this.variables) {
            ED.put(variable, new HashSet<>(variable.getDomain()));
        }
        return macSolver(new HashMap<>(), new HashSet<>(this.variables), ED);
    }

}
