package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Classe representant un solveur par backtrack
 *
 * @autor : <NumEtu 22108455> AHMED youra
 */
public class BacktrackSolver extends AbstractSolver {

    /**
     * Constructeur de la classe
     *
     * @param variables   ensemble des variables
     * @param contraintes ensemble des contraintes
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> contraintes) {
        super(variables, contraintes);
    }

    /**
     * Methode permettant de resoudre le probleme de satisfaction de contraintes
     *
     * @param instantiationPartielle une solution partielle
     * @param variablesNonInstancie  ensemble des variables non instanciees
     * @return une solution complete
     */
    public Map<Variable, Object> backTrack(Map<Variable, Object> instantiationPartielle, LinkedList<Variable> variablesNonInstancie) {
        //condition d'arrêt de la fonction recursive
        if (variablesNonInstancie.isEmpty()) {
            return instantiationPartielle;
        }
        //choisir une variable non encore instanciée
        Variable variable = variablesNonInstancie.poll();
        //choisir une valeur dans le domaine de la variable choisie
        for (Object value : variable.getDomain()) {
            Map<Variable, Object> newInstantiation = new HashMap<>(instantiationPartielle);
            newInstantiation.put(variable, value);
            if (isConsistent(newInstantiation)) {

                Map<Variable, Object> res = backTrack(newInstantiation, variablesNonInstancie);
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
        return this.backTrack(new HashMap<>(), new LinkedList<>(this.variables));
    }
}
