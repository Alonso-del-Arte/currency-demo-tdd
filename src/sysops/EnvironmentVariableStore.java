package sysops;

import java.util.NoSuchElementException;

public class EnvironmentVariableStore {
    
    public static String getVariable(String label) {
        String variable = System.getenv(label);
        if (variable == null) {
            String excMsg = "Variable for label \"" + label 
                    + "\" is not present on this system";
            throw new NoSuchElementException(excMsg);
        }
        return variable;
    }

}
