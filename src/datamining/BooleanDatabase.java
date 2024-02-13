package datamining;

import modelling.BooleanVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe representant une base de donnees decrite par des attributs booleens
 *
 */
public class BooleanDatabase {

    /**
     * L'ensemble des items de la base de donnees
     */
    private Set<BooleanVariable> items;

    /**
     * l'ensemble des transactions de la base de donnees
     */
    private List<Set<BooleanVariable>> transactions;

    /**
     * Constructeur de la classe
     *
     * @param items
     */
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<Set<BooleanVariable>>();
    }

    /**
     * Methode permettant d'ajouter 
     */
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Methode permettant de recuperer l'ensemble des items de la base de donnees
     *
     * @return l'ensemble des items de la base de donnees
     */
    public Set<BooleanVariable> getItems() {
        return items;
    }

    /**
     * Methode permettant de recuperer l'ensemble des transactions de la base de donnees
     *
     * @return l'ensemble des transactions de la base de donnees
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "BooleanDatabase{" +
            "items=" + items +
            ", transactions=" + transactions +
            '}';
    }

}
