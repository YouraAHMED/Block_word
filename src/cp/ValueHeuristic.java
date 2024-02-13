package cp;

import modelling.Variable;

import java.util.List;
import java.util.Set;

public interface ValueHeuristic {

    /**
     * Methode permettant de retourner une liste contenant les valeurs du domaine ordonnées selon l'heuristique
     *
     * @param variable
     * @param domaine
     * @return la liste des valeurs du domaine ordonnées selon l'heuristique
     */
    List<Object> ordering(Variable variable, Set<Object> domaine);
}
