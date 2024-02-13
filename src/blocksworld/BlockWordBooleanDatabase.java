package blocksworld;

import modelling.BooleanVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockWordBooleanDatabase {

    private final BlockWorldVariable blockWorldVariable;


    public BlockWordBooleanDatabase(int nombreBlocks, int nombrePiles) {
        this.blockWorldVariable = new BlockWorldVariable(nombreBlocks, nombrePiles);
    }

    /**
     * Methode permettant de recuperer la variable " qui est a true lorsque le bloc b est directement sur le bloc b′ (et false sinon)
     *
     * @return Un ensemble de variables "on" pour chaque bloc.
     */
    public Set<BooleanVariable> getOnVariables() {
        Set<BooleanVariable> onVariables = new HashSet<>();
        for (int i = 0; i < blockWorldVariable.getNombreBlocks(); i++) {
            for (int j = 0; j < blockWorldVariable.getNombreBlocks(); j++) {
                if (i != j) {
                    onVariables.add(new BooleanVariable("on_" + i + "," + j));
                }
            }
        }
        return onVariables;
    }

    /**
     * Methode permettant de recuperer la variable "on-tableb,p, prenant la valeur true lorsque le bloc b est sur la table dans la pile p (et false sinon)
     *
     * @return Un ensemble de variables "on" pour chaque bloc.
     */
    public Set<BooleanVariable> getOnTableVariables() {
        Set<BooleanVariable> onTableVariables = new HashSet<>();
        for (int i = 0; i < blockWorldVariable.getNombreBlocks(); i++) {
            for (int j = 0; j < blockWorldVariable.getNombrePiles(); j++) {
                onTableVariables.add(new BooleanVariable("on-table_" + i + "," + j));
            }
        }
        return onTableVariables;
    }

    /**
     * Methode permettant de recuperer la variable "fixedb" indiquant si un bloc est fixe.
     *
     * @return Un ensemble de variables "fixed" pour chaque bloc.
     */
    public Set<BooleanVariable> getFixedVariables() {
        Set<BooleanVariable> fixedVariables = new HashSet<>();
        for (int i = 0; i < blockWorldVariable.getNombreBlocks(); i++) {
            fixedVariables.add(new BooleanVariable("fixed_" + i));
        }
        return fixedVariables;
    }

    /**
     * Methode permettant de recuperer la variable "freep" indiquant si une pile est libre.
     *
     * @return Un ensemble de variables "free" pour chaque pile.
     */
    public Set<BooleanVariable> getFreeVariables() {
        Set<BooleanVariable> freeVariable = new HashSet<>();
        for (int i = 0; i < blockWorldVariable.getNombrePiles(); i++) {
            freeVariable.add(new BooleanVariable("free_" + i));
        }
        return freeVariable;
    }


    /**
     * Methode permettant de recuperer l'ensemble des variables de la base de donnees
     *
     * @return l'ensemble des variables de la base de donnees
     */
    public Set<BooleanVariable> getVariables() {
        Set<BooleanVariable> variables = new HashSet<>();
        variables.addAll(this.getOnVariables());
        variables.addAll(this.getOnTableVariables());
        variables.addAll(this.getFixedVariables());
        variables.addAll(this.getFreeVariables());
        return variables;
    }

    /**
     * Methode permettant d'obtenir l'etat initial du monde des blocs
     *
     * @param state
     * @return l'etat initial du monde des blocs
     */
    public Set<BooleanVariable> getInitialStateVariables(List<List<Integer>> state) {
        Set<BooleanVariable> initialStateVariables = new HashSet<>();

        for (int pile = 0; pile < state.size(); pile++) {
            List<Integer> block = state.get(pile);

            if (block.isEmpty()) {
                initialStateVariables.add(new BooleanVariable("free_" + pile));
            } else {
                initialStateVariables.add(new BooleanVariable("on-table_" + block.get(0) + "," + pile));

                for (int i = 0; i < block.size() - 1; i++) {
                    initialStateVariables.add(new BooleanVariable("on_" + block.get(i + 1) + "," + block.get(i)));
                    initialStateVariables.add(new BooleanVariable("fixed_" + block.get(i)));
                }
            }
        }

        return initialStateVariables;
    }





}