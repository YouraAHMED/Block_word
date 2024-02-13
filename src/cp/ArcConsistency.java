package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.*;

public class ArcConsistency {

    /**
     * Ensemble de contraintes
     */
    private final Set<Constraint> constraints;

    /**
     * Constructeur de la classe
     *
     * @param constraints
     */
    public ArcConsistency(Set<Constraint> constraints) {
        this.constraints = constraints;
        for (Constraint constraint : constraints) {
            if (constraint.getScope().size() > 2) {
                throw new IllegalArgumentException("Les contraintes doivent etre unaires ou binaires");
            }
        }
    }

    /**
     * Methode permettant de supprimer en place les valeurs des domaines pour lesquelles il existe une containte unaire qui n'est pas satisfaite par cette valeur
     *
     * @param ED ensemble des domaines des variables
     * @return false si au moins un domaine a été vidé, true sinon
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> ED) {
        for (Constraint constraint : this.constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() == 1) {
                Variable var = scope.iterator().next();
                Iterator<Object> valueIterator = ED.get(var).iterator();
                while (valueIterator.hasNext()) {
                    Object value = valueIterator.next();
                    Map<Variable, Object> assignment = new HashMap<>();
                    assignment.put(var, value);
                    if (!constraint.isSatisfiedBy(assignment)) {
                        valueIterator.remove(); // Supprimer la valeur du domaine de la variable
                    }
                }


            }
        }
        for (Set<Object> domain : ED.values()) {
            if (domain.isEmpty()) {
                return false; // Si le domaine de n'importe quelle variable est vide, retournez false
            }
        }


        return true;
    }

    /**
     * Methode permettant de supprimer en place, toutes les valeurs v1 de D1 pour lesquels il n’existe aucune
     * valeur v2 de D2 supportant v1 pour toutes les contraintes portant sur v1 et v2
     *
     * @param variable1 premiere variable
     * @param domaine1  domaine de la premiere variable
     * @param variable2 deuxieme variable
     * @param domaine2  domaine de la deuxieme variable
     * @return true si au moins une valeur a été supprimée de domaine1, false sinon
     */
    public boolean revise(Variable variable1, Set<Object> domaine1, Variable variable2, Set<Object> domaine2) {
        boolean Del = false;

        for (Object value1 : new HashSet<>(domaine1)) {  // Utilisez une copie du domaine de v1
            boolean viable = false;
            for (Object value2 : new HashSet<>(domaine2)) {  // Utilisez une copie du domaine de v2
                boolean toutSatisfait = true;
                for (Constraint constraint : this.constraints) {
                    if (constraint.getScope().size() == 2 && constraint.getScope().contains(variable1) && constraint.getScope().contains(variable2)) {
                        Map<Variable, Object> assignment = new HashMap<>();
                        assignment.put(variable1, value1);
                        assignment.put(variable2, value2);
                        if (!constraint.isSatisfiedBy(assignment)) {
                            toutSatisfait = false;
                            break;
                        }
                    }
                }
                if (toutSatisfait) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                domaine1.remove(value1);
                Del = true;
            }
        }

        return Del;
    }


    /**
     * Methode permettant de filtrer tous les domaines des variables
     *
     * @param ED ensemble des domaines des variables
     * @return false si au moins un domaine a été vidé, true sinon
     */
    public boolean ac1(Map<Variable, Set<Object>> ED) {
        boolean change = false;
        if (!this.enforceNodeConsistency(ED)) {
            return false;
        }
        do {
            change = false;
            for (Variable xi : ED.keySet()) {
                for (Variable xj : ED.keySet()) {
                    if (!xi.equals(xj)) {
                        if (revise(xi, ED.get(xi), xj, ED.get(xj))) {
                            change = true;
                        }
                    }
                }
            }
        } while (change);

        for (Variable x : ED.keySet()) {
            if (ED.get(x).isEmpty()) {
                return false;

            }
        }
        return true;
    }
}
