package datamining;

import modelling.BooleanVariable;

import java.util.*;
import java.util.stream.Collectors;

public class Apriori extends AbstractItemsetMiner{



    /**
     * Constructeur de la classe
     *
     * @param database
     */
    public Apriori(BooleanDatabase database) {
        super(database);
    }

    /**
     * Methode permettant de trouver tous les items singletons dont la frequence est superieure a la frequence minimale
     *
     * @param frequence
     *
     * @return l'ensemble des items singletons dont la frequence est superieure a la frequence minimale
     */
    public Set<Itemset> frequentSingletons(float frequence) {
        Set<Itemset> singletons = new HashSet<>();
        for(BooleanVariable item : database.getItems()){
            if(frequency(Set.of(item)) >= frequence){
                singletons.add(new Itemset(Set.of(item), frequency(Set.of(item))));

            }
        }
        return singletons;
    }

    /**
     * Methode permettant de trouver un ensemble trié d'items en combinant deux ensembles d'itemes
     *
     * @param ensemble1
     * @param ensemble2
     *
     * @return l'ensemble trié d'items
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> ensemble1, SortedSet<BooleanVariable> ensemble2) {
        SortedSet<BooleanVariable> union = new TreeSet<BooleanVariable>(COMPARATOR);
        if(ensemble1.isEmpty() || ensemble2.isEmpty()){
            return null;
        }
        if (ensemble1.size() == ensemble2.size()) {
            if(!ensemble1.last().equals(ensemble2.last())) {
                if (ensemble1.headSet(ensemble1.last()).equals(ensemble2.headSet(ensemble2.last()))) {
                    union.addAll(ensemble1);
                    union.add(ensemble2.last());
                    return union;
                }
            }
        }
        return null;
    }

    /**
     * Methode permettant de savoir si tous les sous ensembles obtenus en supprimant exactement un element de l'ensemble d'items sont contenu dans la collection d'ensembles d'items
     *
     * @param ensembleItems
     * @param collectionEnsembleItems
     *
     * @return true si tous les sous ensembles obtenus en supprimant exactement un element de l'ensemble d'items sont contenu dans la collection d'ensembles d'items, false sinon
     */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> ensembleItems, Collection<SortedSet<BooleanVariable>> collectionEnsembleItems) {
        for (BooleanVariable item : ensembleItems) {
            SortedSet<BooleanVariable> sousEnsemble = new TreeSet<BooleanVariable>(COMPARATOR);
            sousEnsemble.addAll(ensembleItems);
            sousEnsemble.remove(item);
            if (!collectionEnsembleItems.contains(sousEnsemble)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Itemset> extract(float frequenceMin) {
        Set<Itemset> frequentSingletons = this.frequentSingletons(frequenceMin);
        Set<Itemset> itemsets = new HashSet<>(frequentSingletons);
        List<SortedSet<BooleanVariable>> frequentSets = new ArrayList<>();

        for (Itemset itemset : frequentSingletons) {
            SortedSet<BooleanVariable> item = new TreeSet<>(COMPARATOR);
            item.addAll(itemset.getItems());
            frequentSets.add(item);
        }

        while (!frequentSets.isEmpty()) {
            List<SortedSet<BooleanVariable>> newFrequentSets = new ArrayList<>();
            for (int i = 0; i < frequentSets.size(); i++) {
                for (int j = i + 1; j < frequentSets.size(); j++) {
                    SortedSet<BooleanVariable> combined = combine(frequentSets.get(i), frequentSets.get(j));
                    if (combined != null) {
                        if (allSubsetsFrequent(combined, frequentSets)) {
                            float frequency = frequency(combined);
                            if (frequency >= frequenceMin) {
                                newFrequentSets.add(combined);
                                itemsets.add(new Itemset(combined, frequency));
                            }
                        }
                    }
                }
            }
            //on met a jour la liste des ensembles d'items frequents
            frequentSets = newFrequentSets;
        }

        return itemsets;
    }

    @Override
    public String toString() {
        return "Apriori{" +
                "database=" + database +
                '}' + "\n";
    }
}
