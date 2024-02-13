package blocksworld;

import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConstraintesRegularity {
    private int nombreBlocks;
    private int nombrePiles;

    public ConstraintesRegularity(int nombreBlocks, int nombrePiles) {
        this.nombreBlocks = nombreBlocks;
        this.nombrePiles = nombrePiles;

    }

    public Set<Constraint> getRegularityConstraints(){
        Set<Constraint> regularityConstrainte = new HashSet<>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(this.nombreBlocks, this.nombrePiles);
        Set<Variable> onVariables = blockWorldVariable.getOnVariables();
        //recuperer le domaine des piles
        Set<Integer> domainePile = new HashSet<>();
        for(int i = 0; i< nombrePiles;i++){
            domainePile.add(-(i+1));
        }
        //parcourir les variables
        for(Variable block1:onVariables){
            for (Variable block2:onVariables){
                if(block1!=block2){
                    String blockName1 = block1.getName().substring(3);
                    String blockName2 = block2.getName().substring(3);
                    int blockId1 = Integer.parseInt(blockName1);
                    int blockId2 = Integer.parseInt(blockName2);
                    //recuperer la constante qui permet de passer du premier block au second
                    int ecart = blockId2 - blockId1;
                    //creer le domaine du second block
                    Set<Object> domaineBlock2 = new HashSet<>();
                    domaineBlock2.add((ecart+blockId2));
                    domaineBlock2.addAll(domainePile);

                    //creer une implication qui permet de passer d'une variable à une autre
                    regularityConstrainte.add(new Implication(block1,new HashSet<>(List.of(blockId2)),block2,domaineBlock2));

                }
            }
        }
        return regularityConstrainte;


    }
}
