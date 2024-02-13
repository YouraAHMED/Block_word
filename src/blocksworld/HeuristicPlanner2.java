package blocksworld;

import modelling.Variable;
import planning.Heuristic;
import planning.Goal;

import java.util.Map;

public class HeuristicPlanner2 implements Heuristic {
    /**
     * Etat but
     */
    private Goal etatBut;

    /**
     * Variable
     */
    private BlockWorldVariable variable;

    /**
     * Constructeur de la classe
     *
     * @param etatBut
     * @param variable
     */
    public HeuristicPlanner2(Goal etatBut , BlockWorldVariable variable) {
        this.etatBut = etatBut;
        this.variable = variable;
    }

    /**
     * Estime la distance heuristique entre l'état actuel et l'état but en se basant sur la position des blocs.
     * Pour chaque bloc, vérifie s'il est à la bonne position et si le bloc au-dessus est correctement positionné.
     * Incrémente le compteur pour chaque différence.
     *
     * @param state L'état actuel représenté par une carte de variables et de valeurs.
     * @return Une estimation heuristique de la distance entre l'état actuel et l'état but.
     */
    @Override
    public float estimate(Map<Variable, Object> state) {
        float counter = 0;

        for (int blockId = 0; blockId < variable.getNombreBlocks(); blockId++) {
            Variable onVariable = variable.getOnVariable(blockId);

            if (!(state.get(onVariable).equals(etatBut.getInstanceP().get(onVariable)))) {
                counter++;

                Variable onTopVariable = variable.getOnVariable((blockId + 1) % variable.getNombreBlocks());
                if (state.get(onTopVariable).equals(blockId)) {
                    counter++;
                }
            }
        }

        return counter;
    }




}
