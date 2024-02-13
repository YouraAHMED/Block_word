package modelling;
/**
 * Classe représentant une variable booléenne.
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.Set;


public class BooleanVariable extends Variable {

    /**
     * Constructeur de la classe Variable.
     *
     * @param nom nom de la variable
     */
    public BooleanVariable(String nom) {

        super(nom, Set.of(true, false));
    }
}
