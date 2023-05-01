package sysops;

import java.util.NoSuchElementException;

public class EnvironmentVariableStore {
    
    public static String getVariable(String label) {
        return System.getenv(label);
    }

}
