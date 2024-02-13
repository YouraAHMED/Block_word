package planning;

/**
 * Classe representant un but
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.Variable;

import java.util.Map;

public class BasicGoal implements Goal {

    /**
     * Une instanciation partielle des variables
     */
    private final Map<Variable, Object> instanceP;

    /**
     * Constructeur de la classe
     *
     * @param instanceP l'instaciation partielle
     */
    public BasicGoal(Map<Variable, Object> instanceP) {
        this.instanceP = instanceP;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> etat) {
        if (etat == null) {
            return this.instanceP == null;
        }
        for (Variable v : this.instanceP.keySet()) {

            if (!this.instanceP.get(v).equals(etat.get(v))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Methode qui permet de retourner l'instance partielle
     * @return
     */
    public Map<Variable, Object> getInstanceP() {
        return instanceP;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "instanceP=" + instanceP +
                '}';
    }
}
