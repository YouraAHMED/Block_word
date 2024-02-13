package test;

/**
 * Classe test permetant de faire les tests pour le package modelling
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modellingtests.BooleanVariableTests;
import modellingtests.VariableTests;


public class TestModelling {
    public static void main(String[] args) {
        boolean ok = true;
        ok = ok && VariableTests.testGetName();
        ok = ok && VariableTests.testGetDomain();
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        System.out.println(ok ? "All tests OK" : "At least one test KO");
    }
}
