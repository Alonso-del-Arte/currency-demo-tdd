package currency;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyAmountTest {
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final Random RANDOM = new Random();

    @Test
    void testToStringZeroToNineCents() {
        for (short cents = 0; cents < 9; cents++) {
            MoneyAmount amount = new MoneyAmount(0, DOLLARS, cents);
            String expected = "$0.0" + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringTenToNinetyNineCents() {
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(0, DOLLARS, cents);
            String expected = "$0." + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

}
