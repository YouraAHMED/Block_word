package cp;

import modelling.Variable;

import java.util.*;

public class RandomValueHeuristic implements ValueHeuristic {

    /**
     * generateur aleatoire
     */
    private final Random random;

    /**
     * Constructeur de la classe
     *
     * @param random
     */
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> list = new ArrayList<>(domaine);
        Collections.shuffle(list, this.random);
        return list;
    }
}
