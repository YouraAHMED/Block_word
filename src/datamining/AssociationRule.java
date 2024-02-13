package datamining;

import modelling.BooleanVariable;

import java.util.Set;

public class AssociationRule {
    /**
     * La premisse
     */
    private Set<BooleanVariable> premisse;

    /**
     * La conclusion
     */
    private Set<BooleanVariable> conclusion;

    /**
     * La confiance de la regle
     */
    private float confiance;

    /**
     * La frequence de la regle
     */
    private float frequence;

    /**
     * Constructeur de la classe
     *
     * @param premise
     * @param conclusion
     * @param confiance
     * @param frequence
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequence, float confiance) {
        this.premisse = premise;
        this.conclusion = conclusion;
        this.confiance = confiance;
        this.frequence = frequence;
    }

    /**
     * Methode permettant de recuperer la premisse de la regle
     *
     * @return la premisse de la regle
     */
    public Set<BooleanVariable> getPremise() {
        return premisse;
    }

    /**
     * Methode permettant de recuperer la conclusion de la regle
     *
     * @return la conclusion de la regle
     */
    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    /**
     * Methode permettant de recuperer la confiance de la regle
     *
     * @return la confiance de la regle
     */
    public float getConfidence() {
        return confiance;
    }

    /**
     * Methode permettant de recuperer la frequence de la regle
     *
     * @return la frequence de la regle
     */
    public float getFrequency() {
        return frequence;
    }

    @Override
    public String toString() {
        return "AssociationRule{" +
                "premisse=" + premisse +
                ", conclusion=" + conclusion +
                ", confiance=" + confiance +
                ", frequence=" + frequence +
                '}';
    }

}
