package datamining;
import modelling.BooleanVariable;

import java.util.Set;

/**
 * Classe ItemSet
 *
 */
public class Itemset {

    /**
     * l'ensemble des items de l'itemset
     */
    private Set<BooleanVariable> items;

    /**
     * La frequence de l'itemset
     */
    private float frequence;

    /**
     * Constructeur de la classe
     *
     * @param items
     * @param frequence
     */
    public Itemset(Set<BooleanVariable> items, float frequence) {
        this.items = items;
        this.frequence = frequence;
    }

    /**
     * Methode permettant de recuperer l'ensemble des items de l'itemset
     *
     * @return l'ensemble des items de l'itemset
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Methode permettant de recuperer la frequence de l'itemset
     *
     * @return la frequence de l'itemset
     */

    public float getFrequency() {
        return frequence;
    }

    @Override
    public String toString() {
        return "Itemset{" +
                "items=" + items +
                ", frequence=" + frequence +
                '}';
    }
}
