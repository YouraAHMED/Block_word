package planning;

/**
 * Interface Heuristic
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.Map;

public interface Heuristic {

    /**
     * Methode donnant une estimation du coup d'un plan optimal pour atteindre le but
     *
     * @param etat
     * @return une estimation du coup d'un plan optimal pour atteindre le but
     */
    float estimate(Map<Variable, Object> etat);
}
