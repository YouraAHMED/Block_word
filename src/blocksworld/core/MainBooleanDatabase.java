package blocksworld.core;

import blocksworld.BlockWordBooleanDatabase;
import bwgeneratordemo.Demo;
import datamining.Apriori;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;
import datamining.AssociationRule;
import modelling.BooleanVariable;

import java.util.*;

public class MainBooleanDatabase {

    public static void main(String[] args) {
        BlockWordBooleanDatabase bwdb = new BlockWordBooleanDatabase( 5 , 5);
        // Création d'une base de données d'états du monde des blocs
        BooleanDatabase db = new BooleanDatabase(bwdb.getVariables());

        int n = 1000;  // nombre d'états à générer
        Random random = new Random(); // générateur de nombres aléatoires pour la génération d'états
        for (int i = 0; i < n; i++) {
            // Drawing a state at random
            List<List<Integer>> state = Demo.getState(random);
            System.out.println("state" + state);


            // Converting state to instance
            Set<BooleanVariable> instance = bwdb.getInitialStateVariables(state);
            System.out.println("instance "+instance);



            // Adding state to database
            db.add(instance);
        }


        // Création de l'objet Apriori avec la base de données
        Apriori apriori = new Apriori(db);

        // Extraction des motifs avec une fréquence minimale de 2/3
        float frequenceMin = 0.75f;
        Set<Itemset> motifs = apriori.extract(frequenceMin);
        //System.out.println("motifs" + motifs);

        // Extraction des règles d'association avec une fréquence minimale de 2/3 et une confiance minimale de 95/100
        float confidenceMin = 0.75f;
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);
        Set<AssociationRule> rules = ruleMiner.extract(frequenceMin, confidenceMin);

        // Affichage des motifs
        System.out.println("Apriori:");
        for (Itemset motif : motifs) {
            System.out.println("Items: " + motif.getItems() + ", Fréquence: " + motif.getFrequency());
        }

        // Affichage des règles d'association
        System.out.println("\nRègles d'association:");
        for (AssociationRule rule : rules) {
            System.out.println("Prémisse: " + rule.getPremise() + ", Conclusion: " + rule.getConclusion() +
                    ", Fréquence: " + rule.getFrequency() + ", Confiance: " + rule.getConfidence());
        }

    }
}

