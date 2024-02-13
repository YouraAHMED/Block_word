package cp;

import modelling.Variable;

import java.util.Map;

public interface Solver {

    /**
     * Mathode permettant de retourner la solution du probleme
     *
     * @return la solution du probleme ou null si pas de solution
     */
    Map<Variable, Object> solve();
}
