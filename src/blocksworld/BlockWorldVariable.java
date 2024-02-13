package blocksworld;
/**
 * Classe permettant d'avoir l'ensemble des variables du monde des blocks.
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.BooleanVariable;
import modelling.Variable;

import java.util.HashSet;
import java.util.Set;

public class BlockWorldVariable extends BlockWorld {


    /**
     * Constructeur de la classe BlockWorldVariable.
     * @param nombreBlocks Le nombre de blocs dans le monde.
     * @param nombrePiles Le nombre de piles dans le monde.
     */
    public BlockWorldVariable(int nombreBlocks, int nombrePiles) {
        super(nombreBlocks, nombrePiles);
    }

    /**
     * Obtient l'ensemble complet de variables pour le monde des blocs.
     * @return Un ensemble de variables (les variables "on", "fixed", et "free").
     */
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();
        // Ajoute les variables "free", "on", et "fixed" aux variables globales.
        variables.addAll(this.getFreeVariables());
        variables.addAll(this.getOnVariables());
        variables.addAll(this.getFixedVariables());
        return variables;
    }

    /**
     * Obtient les variables "on" représentant la position des blocs.
     * @return Un ensemble de variables "on" pour chaque bloc.
     */
    public Set<Variable> getOnVariables() {
        Set<Variable> onVariables = new HashSet<>();
        // Crée une variable "on" pour chaque bloc.
        for (int blockId = 0; blockId < nombreBlocks; blockId++) {
            Set<Object> domain = new HashSet<>();

            // Ajoute les entiers de -p à n-1 dans le domaine.
            for (int i = -nombrePiles; i <= nombreBlocks - 1; i++) {
                if (i != blockId) { // Exclure la valeur "b".
                    domain.add(i);
                }
            }
            onVariables.add(new Variable("on_" + blockId, domain));
        }
        return onVariables;
    }

    /**
     * Obtient les variables booléennes "fixed" indiquant si un bloc est fixe.
     * @return Un ensemble de variables "fixed" pour chaque bloc.
     */
    public Set<BooleanVariable> getFixedVariables() {
        Set<BooleanVariable> fixedVariables = new HashSet<>();
        // Crée une variable "fixed" pour chaque bloc.
        for (int blockId = 0; blockId < nombreBlocks; blockId++) {
            fixedVariables.add(new BooleanVariable("fixed_" + blockId));
        }
        return fixedVariables;
    }

    /**
     * Obtient les variables booléennes "free" indiquant si une pile est libre.
     * @return Un ensemble de variables "free" pour chaque pile.
     */
    public Set<BooleanVariable> getFreeVariables() {
        Set<BooleanVariable> freeVariable = new HashSet<>();
        // Crée une variable "free" pour chaque pile.
        for (int pileId = 0; pileId <nombrePiles; pileId++) {
            freeVariable.add(new BooleanVariable("free" + (-(pileId +1))));


        }
        return freeVariable;
    }

    /**
     * Obtient les variables "on" selon l'id du bloc.
     * @param idBlock
     * @return la variable "on" du bloc.
     */
    public Variable getOnVariable(int idBlock){
        Set<Variable> onVariables = this.getOnVariables();
        for(Variable block : onVariables){
            String name = block.getName().substring(3);
            int blockId1 = Integer.parseInt(name);
            if(blockId1 == idBlock){
                return block;
            }

        }
        return null;

    }

    /**
     * Obtient les variables "fixed" selon l'id du bloc.
     * @param idBlock
     * @return la variable "fixed" du bloc.
     */
    public BooleanVariable getFixed(int idBlock){
        Set<BooleanVariable> fixedVariables = this.getFixedVariables();
        for(BooleanVariable block : fixedVariables){
            String name = block.getName().substring(6);
            int blockId1 = Integer.parseInt(name);
            if(blockId1 == idBlock){
                return block;
            }

        }
        return null;

    }

    /**
     * Obtient les variables "free" selon l'id du bloc.
     * @param idBlock
     * @return la variable "free" du bloc.
     */
    public BooleanVariable getFree(int idBlock){
        Set<BooleanVariable> freeVariables = this.getFreeVariables();
        for(BooleanVariable block : freeVariables){
            String name = block.getName().substring(4);
            int blockId1 = Integer.parseInt(name);
            if(blockId1 == idBlock){
                return block;
            }

        }
        return null;

    }

    /**
     * Methode permettant de retourner le nombre de blocs.
     * @return
     */
    public int getNombreBlocks() {
        return nombreBlocks;
    }

    /**
     * Methode permettant de retourner le nombre de piles.
     * @return
     */
    public int getNombrePiles() {
        return nombrePiles;
    }

}


