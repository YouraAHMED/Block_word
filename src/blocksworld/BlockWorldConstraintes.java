package blocksworld;

import modelling.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockWorldConstraintes extends BlockWorld  {


    /**
     * Constructeur de la classe.
     *
     * @param nombreBlocks Le nombre de blocs dans le monde.
     * @param nombrePiles  Le nombre de piles dans le monde.
     */
    public BlockWorldConstraintes(int nombreBlocks, int nombrePiles) {
        super(nombreBlocks, nombrePiles);
    }

    /**
     * Méthode pour obtenir les contraintes liées aux variables "on".
     *
     * @return Un ensemble de contraintes de différence entre les variables "on".
     */
    public Set<DifferenceConstraint> getOnConstraint() {
        Set<DifferenceConstraint> onContraintes = new HashSet<>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(this.nombreBlocks, this.nombrePiles);
        Set<Variable> variables = blockWorldVariable.getOnVariables();

        // Boucle pour comparer chaque paire de variables
        for (Variable v1 : variables) {
            for (Variable v2 : variables) {
                if (v1 != v2) {
                    DifferenceConstraint differenceConstraint = new DifferenceConstraint(v1, v2);
                    onContraintes.add(differenceConstraint);
                }
            }
        }
        return onContraintes;
    }

    /**
     * Méthode pour obtenir les contraintes liées aux variables "fixed".
     *
     * @return Un ensemble de contraintes d'implication pour les variables "fixed".
     */
    public Set<Implication> getFixedContrainte() {
        Set<Implication> fixedContrainte = new HashSet<>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(this.nombreBlocks, this.nombrePiles);
        Set<Variable> variablesOn = blockWorldVariable.getOnVariables();
        Set<BooleanVariable> fixedVariables = blockWorldVariable.getFixedVariables();

        // Boucle pour comparer chaque paire de variables
        for (Variable onBlock : variablesOn) {
            for (Variable fixedVariable : fixedVariables) {
                if (onBlock != fixedVariable) {
                    String blockName2 = fixedVariable.getName().substring(6);
                    int blockId2 = Integer.parseInt(blockName2);
                    Implication implication = new Implication(onBlock, new HashSet<>(List.of(blockId2)), fixedVariable, new HashSet<>(List.of(true)));
                    fixedContrainte.add(implication);
                }
            }
        }
        return fixedContrainte;
    }

    /**
     * Méthode pour obtenir les contraintes liées aux variables "on" et "free".
     *
     * @return Un ensemble de contraintes d'implication pour les variables "on" et "free".
     */
    public Set<Implication> getConstraintBlockPile() {
        Set<Implication> blockPileContrainte = new HashSet<>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(this.nombreBlocks, this.nombrePiles);
        Set<Variable> onVariables = blockWorldVariable.getOnVariables();
        Set<BooleanVariable> freeVariables = blockWorldVariable.getFreeVariables();

        // Boucle pour comparer chaque paire de variables
        for (Variable onBlock :onVariables) {
            for (Variable freePile : freeVariables) {
                String pileName = freePile.getName().substring(4);
                int pileId = Integer.parseInt(pileName);
                Implication implication = new Implication(onBlock, new HashSet<>(List.of(pileId)), freePile, new HashSet<>(List.of(false)));
                blockPileContrainte.add(implication);
            }
        }
        return blockPileContrainte;
    }

    /**
     * Méthode pour obtenir l'ensemble de toutes les contraintes.
     *
     * @return Un ensemble de toutes les contraintes définies.
     */
    public Set<Constraint> getConstraint() {
        Set<Constraint> contraintes = new HashSet<>();
        contraintes.addAll(this.getOnConstraint());
        contraintes.addAll(this.getFixedContrainte());
        contraintes.addAll(this.getConstraintBlockPile());
        return contraintes;
    }
}

