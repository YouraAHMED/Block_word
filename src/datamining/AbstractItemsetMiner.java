package datamining;

import modelling.BooleanVariable;

import java.util.Comparator;
import java.util.Set;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    /**
     * la base de donnees
     */
    protected BooleanDatabase database;

    /**
     * Attribut pour comparer les items
     */
    public static final Comparator<BooleanVariable> COMPARATOR =
        (var1, var2) -> var1.getName().compareTo(var2.getName());

    /**
     * Constructeur de la classe
     *
     * @param database
     */
    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }


    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    /**
     * Methode retournant la frequence d'un ensemble d'items dans la base de donnees
     *
     * @param items
     *
     * @return la frequence d'un ensemble d'items dans la base de donnees
     */
    public float frequency(Set<BooleanVariable> items) {
        float compteurItem = 0;
        for(Set<BooleanVariable> item : database.getTransactions()){
            if(item.containsAll(items)){
                compteurItem++;
            }
        }
        return compteurItem/database.getTransactions().size();
        
    }

    /**
     * Methode qui permet de trouver tous les sous ensembles d'un ensemble d'items de taille k qui sont frequents
     * @param frequenceMin
     * @return
     */

    @Override
    public Set<Itemset> extract(float frequenceMin) {
        return null;
    }
}
