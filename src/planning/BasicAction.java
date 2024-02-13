package planning;
/**
 * Classe representant une action
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.HashMap;
import java.util.Map;

public class    BasicAction implements Action {

    /**
     * La precondition de l'action
     */
    private final Map<Variable, Object> precondition;

    /**
     * L'effet de l'action
     */
    private final Map<Variable, Object> effect;

    /**
     * Le cout de l'action
     */
    private final int cost;

    /**
     * Constructeur de la classe BasicAction
     *
     * @param precondition la precondition de l'action
     * @param effect l'effet de l'action
     * @param cost le cout de l'action
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> etat) {
        if (etat == null) {
            return false;
        }
        if (!etat.keySet().containsAll(this.precondition.keySet())) {
            return false;
        }
        for (Variable v : this.precondition.keySet()) {
            if (!this.precondition.get(v).equals(etat.get(v))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat) {
        if (!this.isApplicable(etat)) {
            return null;
        }
        Map<Variable, Object> newEtat = new HashMap<>(etat);
        for (Variable v : this.effect.keySet()) {
            newEtat.put(v, this.effect.get(v));
        }
        return newEtat;
    }

    @Override
    public int getCost() {
        return this.cost;
    }


    @Override
    public String toString() {
        return "BasicAction{" +
                "precondition=" + precondition +
                ", effect=" + effect +
                ", cost=" + cost +
                '}';
    }
}
