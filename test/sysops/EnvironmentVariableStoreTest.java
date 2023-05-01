package sysops;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EnvironmentVariableStoreTest {

    @Test
    void testGetVariable() {
        System.out.println("getVariable");
        Map<String, String> properties = System.getenv();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String label = entry.getKey();
            String expected = entry.getValue();
            String actual = EnvironmentVariableStore.getVariable(label);
            String msg = "Expecting property for key " + label + " to be " 
                    + expected;
            assertEquals(expected, actual, msg);
        }
    }

}
