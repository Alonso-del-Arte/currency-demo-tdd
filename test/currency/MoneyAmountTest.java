package currency;

import static currency.CurrencyChooser.chooseCurrency;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyAmountTest {
    
    private static final Currency DINARS = Currency.getInstance("LYD");
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final Currency EUROS = Currency.getInstance("EUR");
    
    private static final Currency YEN = Currency.getInstance(Locale.JAPAN);
    
    static final Random RANDOM = new Random();
    
    @Test
    void testGetUnits() {
        System.out.println("getUnits");
        int expected = RANDOM.nextInt(32768) - 16384;
        MoneyAmount amount = new MoneyAmount(expected, chooseCurrency());
        long actual = amount.getUnits();
        assertEquals(expected, actual);
    }
    
    @Test
    void testGetFullAmountInCents() {
        System.out.println("getFullAmountInCents");
        int dollars = RANDOM.nextInt(524288);
        short cents = (short) RANDOM.nextInt(100);
        MoneyAmount amount = new MoneyAmount(dollars, DOLLARS, cents);
        int expected = dollars * 100 + cents;
        long actual = amount.getFullAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    void testGetFullAmountInCentsYen() {
        int expected = RANDOM.nextInt(524288);
        MoneyAmount amount = new MoneyAmount(expected, YEN);
        long actual = amount.getFullAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    void testGetFullAmountInCentsDinars() {
        int dinars = RANDOM.nextInt(524288);
        short darahim = (short) RANDOM.nextInt(1000);
        MoneyAmount amount = new MoneyAmount(dinars, DINARS, darahim);
        int expected = dinars * 1000 + darahim;
        long actual = amount.getFullAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    void testGetDivisions() {
        System.out.println("getDivisions");
        short expected = (short) RANDOM.nextInt(100);
        MoneyAmount amount = new MoneyAmount(0, EUROS, expected);
        long actual = amount.getDivisions();
        assertEquals(expected, actual);
    }
    
    @Test
    void testGetCurrency() {
        System.out.println("getCurrency");
        Currency expected = chooseCurrency();
        MoneyAmount amount = new MoneyAmount(0, expected);
        Currency actual = amount.getCurrency();
        assertEquals(expected, actual);
    }
    
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
    
    @Test
    void testDollarsPlusZeroToNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        String part = "$" + dollarQty + ".0";
        for (short cents = 0; cents < 9; cents++) {
            MoneyAmount amount = new MoneyAmount(dollarQty, DOLLARS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testDollarsPlusTenToNinetyCentsCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        String part = "$" + dollarQty + '.';
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(dollarQty, DOLLARS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testConstructorRejectsPseudoCurrencies() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (Currency currency : currencies) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                Throwable exception 
                        = assertThrows(IllegalArgumentException.class, () -> {
                    MoneyAmount badAmount = new MoneyAmount(0, currency);
                    System.out.println("Instantiated amount " 
                            + badAmount.toString() + " with currency "  
                            + currency.getDisplayName() + " which has " 
                            + fractDigits + " fractional digits");
                });
                String excMsg = exception.getMessage();
                assert excMsg != null : "Message should not be null";
                String symbol = currency.getSymbol();
                String msg = "Message should include symbol " + symbol 
                        + " for pseudo-currency " + currency.getDisplayName();
                assert excMsg.contains(symbol) : msg;
            }
        }
        
        
        
        
        
        
        
        
        
        
    }

}
