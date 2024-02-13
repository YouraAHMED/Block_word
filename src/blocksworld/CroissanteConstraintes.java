package blocksworld;

import modelling.Constraint;
import modelling.UnaryConstraint;
import modelling.Variable;

import java.util.HashSet;
import java.util.Set;

public class CroissanteConstraintes {
    private int nombreBlocks;
    private int nombrePiles;

    public CroissanteConstraintes(int nombreBlocks, int nombrePiles) {
        this.nombreBlocks = nombreBlocks;
        this.nombrePiles = nombrePiles;
    }

    public Set<Constraint> getCroissanceConstraint() {
        Set<Constraint> croissanceConstraint = new HashSet<>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(this.nombreBlocks, this.nombrePiles);
        Set<Variable> onVariables = blockWorldVariable.getOnVariables();
        //parcourir les variables et recuperer leur indice
        for (Variable block : onVariables) {
            int id1 = Integer.parseInt(block.getName().substring(3));

                    Set<Object> newDomaine = new HashSet<>();
                    for (Object o : block.getDomain()) {
                        if ((int) o < id1) {
                            newDomaine.add(o);
                        }
                    }
                    croissanceConstraint.add(new UnaryConstraint(block,newDomaine));

                }

        return croissanceConstraint;
        }


}