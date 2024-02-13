package datamining;

import java.util.Set;

public interface ItemsetMiner {
    /**
     * Methode permettant de recuperer la base de donnees
     *
     * @return la base de donnees
     */
    BooleanDatabase getDatabase();

    /**
     * Methode permettant de retourner l'ensemble des itemsets non vides ayant au moins une frequence indiquee
     *
     * @param frequenceMin
     * @return l'ensemble des itemsets non vides ayant au moins une frequence indiquee
     */
    Set<Itemset> extract(float frequenceMin);
}
