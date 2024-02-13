package blocksworld;

import modelling.BooleanVariable;
import modelling.Variable;

public class BlockWorld {
    protected int nombreBlocks;
    protected int nombrePiles;

    public BlockWorld(int nombreBlocks, int nombrePiles) {
        this.nombreBlocks = nombreBlocks;
        this.nombrePiles = nombrePiles;
    }

    public int getIdFromOnVariable(Variable onVariable) {
        String name = onVariable.getName().substring(3);
        return Integer.parseInt(name);
    }

    public int getIdFromFixedVariable(BooleanVariable fixedVariable) {
        String name = fixedVariable.getName().substring(6);
        return Integer.parseInt(name);
    }

    public int getIdFromFreeVariable(BooleanVariable freeVariable) {
        String name = freeVariable.getName().substring(4);
        return Integer.parseInt(name);
    }


}
