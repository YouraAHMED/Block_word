package datamining;

import modelling.BooleanVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{

    private final Apriori apriori;

    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
        this.apriori = new Apriori(database);
    }


    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        // Utilisons un ensemble pour stocker toutes les prémices
        Set<Set<BooleanVariable>> premises = new HashSet<>();

        // Tous les sous-ensembles des items
        Set<Set<BooleanVariable>> subsets = subsets(items);

        // Ajoutons tous les sous-ensembles sauf l'ensemble vide et les items eux-mêmes
        for (Set<BooleanVariable> subset : subsets) {
            if (!subset.isEmpty() && !subset.equals(items)) {
                premises.add(subset);
            }
        }

        return premises;
    }

    // Méthode utilitaire pour générer tous les sous-ensembles
    private static <T> Set<Set<T>> subsets(Set<T> set) {
        // Convertissons l'ensemble en liste pour pouvoir itérer dessus
        List<T> sousList = new ArrayList<>(set);
        // Taille de l'ensemble
        int n = sousList.size();

        // Utilisons un ensemble pour stocker tous les sous-ensembles
        Set<Set<T>> result = new HashSet<>();

        // Générons tous les sous-ensembles (1 << n est équivalent à 2^n)
        for (int i = 0; i < (1 << n); i++) {
            Set<T> subset = new HashSet<>(); // Sous-ensemble courant
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) { // Si le j-ème bit est à 1 c'est a dire que l'élément j est dans le sous-ensemble
                    subset.add(sousList.get(j)); // Ajoutons l'élément j au sous-ensemble
                }
            }
            result.add(subset);
        }

        return result;
    }


    @Override
    public Set<AssociationRule> extract(float frequencyMin, float confidenceMin) {
        Set<AssociationRule> associationRules = new HashSet<>();
        Set<Itemset> frequentItemsets = this.apriori.extract(frequencyMin);
        for (Itemset itemset : frequentItemsets) {
            Set<Set<BooleanVariable>> conclusions = allCandidatePremises(itemset.getItems());
            float frequency = frequency(itemset.getItems(), frequentItemsets);
            for (Set<BooleanVariable> conclusion : conclusions) {
                Set<BooleanVariable> premise = new HashSet<>(itemset.getItems());
                premise.removeAll(conclusion);
                float confidence = confidence(premise, itemset.getItems(), frequentItemsets);
                if (confidence >= confidenceMin) {
                    associationRules.add(new AssociationRule(premise, conclusion, frequency, confidence));
                }
            }
        }
        return associationRules;
    }




    @Override
    public String toString() {
        return "BruteForceAssociationRuleMiner{" +
                "apriori=" + apriori +
                '}';
    }
}
