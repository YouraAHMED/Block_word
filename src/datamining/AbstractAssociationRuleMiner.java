package datamining;

import modelling.BooleanVariable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    /**
     * La base de donnees
     */
    protected BooleanDatabase database;

    /**
     * Constructeur de la classe
     *
     * @param database
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    /**
     *Methode permettant de trouver la frequence d'un ensemble d'items
     *
     * @param items
     * @param itemsets
     *
     * @return la frequence d'un ensemble d'items
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) { //si l'itemset contient les items
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("L'ensemble d'items n'apparait pas dans l'ensemble d'itemsets");
    }

    /**
     *Methode permettant de trouver la confiance d'une regle d'association
     *
     * @param premise
     * @param conclusion
     * @param itemsets
     *
     * @return la confiance d'une regle d'association
     */
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        Set<BooleanVariable> union = new HashSet<>(premise); //on fait l'union des deux ensembles (premise et conclusion
        union.addAll(conclusion);
        return frequency(union, itemsets) / frequency(premise, itemsets); //pour calculer la confiance, on divise la fréquence de l'union par la fréquence de la prémisse
    }
}
