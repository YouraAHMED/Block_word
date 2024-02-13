package datamining;

import java.util.Set;

public interface AssociationRuleMiner {

    /**
     * Methode permettant de recuperer la base de donnees
     *
     * @return la base de donnees
     */
    BooleanDatabase getDatabase();

    /**
     * Methode permettant de recuperer la liste des regles d'association supereur a une frequence et une confiance donnees
     *
     * @param frequenceMin
     * @param confianceMin
     * @return la liste des regles d'association
     */
    Set<AssociationRule> extract(float frequenceMin, float confianceMin);
}
