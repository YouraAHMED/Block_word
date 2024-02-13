package blocksworld;

import modelling.Variable;
import planning.Goal;
import planning.Heuristic;

import java.util.Map;

public class HeuristiPlanner1 implements Heuristic {

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
        public HeuristiPlanner1(Goal etatBut , BlockWorldVariable variable) {
            this.etatBut = etatBut;
            this.variable = variable;
        }


    /**
     * Estime la distance heuristique entre l'état actuel et l'état but en se basant sur la position des blocs.
     * @param etat
     * @return
     */
    @Override
    public float estimate(Map<Variable, Object> etat) {
            float nbBlockMalPlace = 0;
            for (int i = 0; i < variable.getNombreBlocks(); i++) {
                Object etatValue = etat.get(variable.getOnVariable(i));
                Object goalValue = etatBut.getInstanceP().get(variable.getOnVariable(i));

                if (etatValue != null && !etatValue.equals(goalValue)) {
                    nbBlockMalPlace++;
                }
            }
            return nbBlockMalPlace;
        }
}

