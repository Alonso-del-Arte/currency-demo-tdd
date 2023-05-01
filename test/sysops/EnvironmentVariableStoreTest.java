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

    @Test
    void testGetNonexistentVariable() {
        String badLabel = "There should be no variable for this label";
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            String badVariable = EnvironmentVariableStore.getVariable(badLabel);
            System.out.println("Bad label \"" + badLabel
                    + "\" should not have retrieved variable \"" + badVariable
                    + "\"");
        });
        System.out.println("Trying to access variable for bad label \""
                + badLabel + "\" correctly caused NoSuchElementException");
        String excMsg = exception.getMessage();
        assert excMsg != null : "Message should not be null";
        System.out.println("\"" + excMsg + "\"");
        String msg = "Exception message should include label \"" + badLabel 
                + "\"";
        assert excMsg.contains(badLabel) : msg;
    }
    
}
