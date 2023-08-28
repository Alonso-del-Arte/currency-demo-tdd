package currency;

import static currency.CurrencyChooser.chooseCurrency;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyAmountTest {
    
    private static final Currency DINARS 
            = Currency.getInstance(Locale.forLanguageTag("ar-LY"));
    
    static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
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
        for (short cents = 0; cents < 10; cents++) {
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
    void testToStringDollarsPlusZeroToNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        String part = "$" + dollarQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            MoneyAmount amount = new MoneyAmount(dollarQty, DOLLARS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDollarsPlusTenToNinetyCentsCents() {
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
    void testToStringNegativeDollarAmountPlusZeroToNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        String part = "-$" + dollarQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            MoneyAmount amount = new MoneyAmount(-dollarQty, DOLLARS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringNegativeDollarAmountPlusTenToNinetyNineCents() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        String part = "-$" + dollarQty + '.';
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(-dollarQty, DOLLARS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringEurosZeroToNineCents() {
        for (short cents = 0; cents < 10; cents++) {
            MoneyAmount amount = new MoneyAmount(0, EUROS, cents);
            String expected = "EUR0.0" + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringEurosTenToNinetyNineCents() {
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(0, EUROS, cents);
            String expected = "EUR0." + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringEurosPlusZeroToNineCents() {
        int euroQty = RANDOM.nextInt(1000) + 1;
        String part = "EUR" + euroQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            MoneyAmount amount = new MoneyAmount(euroQty, EUROS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringEurosPlusTenToNinetyCentsCents() {
        int euroQty = RANDOM.nextInt(1000) + 1;
        String part = "EUR" + euroQty + '.';
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(euroQty, EUROS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringEurosNegativeEuroAmountPlusZeroToNineCents() {
        int euroQty = RANDOM.nextInt(1000) + 1;
        String part = "-EUR" + euroQty + ".0";
        for (short cents = 0; cents < 10; cents++) {
            MoneyAmount amount = new MoneyAmount(-euroQty, EUROS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringEurosNegativeEuroAmountPlusTenToNinetyNineCents() {
        int euroQty = RANDOM.nextInt(1000) + 1;
        String part = "-EUR" + euroQty + '.';
        for (short cents = 10; cents < 100; cents++) {
            MoneyAmount amount = new MoneyAmount(-euroQty, EUROS, cents);
            String expected = part + cents;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsZeroToNineDarahim() {
        for (short darahim = 0; darahim < 10; darahim++) {
            MoneyAmount amount = new MoneyAmount(0, DINARS, darahim);
            String expected = "LYD0.00" + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void testToStringDinarsTenToNinetyNineDarahim() {
        for (short darahim = 10; darahim < 100; darahim++) {
            MoneyAmount amount = new MoneyAmount(0, DINARS, darahim);
            String expected = "LYD0.0" + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinars100To999Darahim() {
        for (short darahim = 100; darahim < 1000; darahim++) {
            MoneyAmount amount = new MoneyAmount(0, DINARS, darahim);
            String expected = "LYD0." + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsPlusZeroToNineDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "LYD" + dinarQty + ".00";
        for (short darahim = 0; darahim < 10; darahim++) {
            MoneyAmount amount = new MoneyAmount(dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsPlusTenToNinetyDarahimDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "LYD" + dinarQty + ".0";
        for (short darahim = 10; darahim < 100; darahim++) {
            MoneyAmount amount = new MoneyAmount(dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsPlus100To999DarahimDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "LYD" + dinarQty + '.';
        for (short darahim = 100; darahim < 1000; darahim++) {
            MoneyAmount amount = new MoneyAmount(dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsNegativeDinarAmountPlusZeroToNineDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "-LYD" + dinarQty + ".00";
        for (short darahim = 0; darahim < 10; darahim++) {
            MoneyAmount amount = new MoneyAmount(-dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToStringDinarsNegativeDinarAmountPlusTenToNinetyNineDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "-LYD" + dinarQty + ".0";
        for (short darahim = 10; darahim < 100; darahim++) {
            MoneyAmount amount = new MoneyAmount(-dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testToString() {
        System.out.println("toString");
        int yenQty = RANDOM.nextInt(1048576);
        MoneyAmount amount = new MoneyAmount(yenQty, YEN);
        String expected = "JPY" + yenQty;
        String actual = amount.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    void testToStringDinarsNegativeDinarAmountPlus100To999NineDarahim() {
        int dinarQty = RANDOM.nextInt(1000) + 1;
        String part = "-LYD" + dinarQty + '.';
        for (short darahim = 100; darahim < 1000; darahim++) {
            MoneyAmount amount = new MoneyAmount(-dinarQty, DINARS, darahim);
            String expected = part + darahim;
            String actual = amount.toString();
            assertEquals(expected, actual);
        }
    }
    
    @Test
    void testReferentialEquality() {
        int units = RANDOM.nextInt(1048576);
        Currency currency = chooseCurrency();
        MoneyAmount amount = new MoneyAmount(units, currency);
        assertEquals(amount, amount);
    }
    
    @Test
    void testNotEqualsNull() {
        int units = RANDOM.nextInt(1048576);
        Currency currency = chooseCurrency();
        MoneyAmount amount = new MoneyAmount(units, currency);
        assertNotEquals(amount, null);
    }
    
    @Test
    void testNotEqualsDiffClass() {
        int units = RANDOM.nextInt(1048576);
        Currency currency = chooseCurrency();
        MoneyAmount amount = new MoneyAmount(units, currency);
        MoneyAmount diffClassAmount = new MoneyAmount(units, currency) {
            
            @Override
            public String toString() {
                return this.getClass().getName() + "<" + super.toString() + ">";
            }
            
        };
        assertNotEquals(amount, diffClassAmount);
    }
    
    @Test
    void testNotEqualsDiffCurrency() {
        int units = RANDOM.nextInt(1048576);
        MoneyAmount amountA = new MoneyAmount(units, DOLLARS);
        MoneyAmount amountB = new MoneyAmount(units, EUROS);
        assertNotEquals(amountA, amountB);
    }
    
    @Test
    void testEquals() {
        System.out.println("equals");
        int units = RANDOM.nextInt(1048576);
        Currency currency = chooseCurrency();
        MoneyAmount someAmount = new MoneyAmount(units, currency);
        MoneyAmount sameAmount = new MoneyAmount(units, currency);
        assertEquals(someAmount, sameAmount);
    }
    
    @Test
    void testNotEqualsDifferentEuroAmount() {
        int units = RANDOM.nextInt(1048576);
        MoneyAmount amountA = new MoneyAmount(units, EUROS);
        MoneyAmount amountB = new MoneyAmount(units + 1, EUROS);
        assertNotEquals(amountA, amountB);
    }
    
    @Test
    void testNotEqualsDifferentCentsAmount() {
        int dollarQty = RANDOM.nextInt(1000) + 1;
        for (short cents = 0; cents < 99; cents++) {
            MoneyAmount amountA = new MoneyAmount(dollarQty, DOLLARS, cents);
            MoneyAmount amountB = new MoneyAmount(dollarQty, DOLLARS, 
                    (short) (cents +  1));
            assertNotEquals(amountA, amountB);
        }
    }
    
    @Test
    void testHashCode() {
        System.out.println("hashCode");
        int capacity = RANDOM.nextInt(256) + 64;
        Set<MoneyAmount> amounts = new HashSet<>(capacity);
        Set<Integer> hashes = new HashSet<>(capacity);
        while (amounts.size() < capacity) {
            int units = RANDOM.nextInt();
            MoneyAmount amount = new MoneyAmount(units, chooseCurrency());
            MoneyAmount dinarAmount = new MoneyAmount(units, DINARS);
            MoneyAmount dollarAmount = new MoneyAmount(units, DOLLARS);
            MoneyAmount euroAmount = new MoneyAmount(units, EUROS);
            MoneyAmount yenAmount = new MoneyAmount(units, YEN);
            amounts.add(amount);
            amounts.add(dinarAmount);
            amounts.add(dollarAmount);
            amounts.add(euroAmount);
            amounts.add(yenAmount);
            hashes.add(amount.hashCode());
            hashes.add(dinarAmount.hashCode());
            hashes.add(dollarAmount.hashCode());
            hashes.add(euroAmount.hashCode());
            hashes.add(yenAmount.hashCode());
        }
        int expected = amounts.size();
        int actual = hashes.size();
        assertEquals(expected, actual);
    }
    
    @Test
    void testPlusDiffCurrenciesCausesException() {
        MoneyAmount dollarAmount = new MoneyAmount(RANDOM.nextInt(), DOLLARS, 
                (short) RANDOM.nextInt(100));
        String dollarAmountStr = dollarAmount.toString();
        MoneyAmount euroAmount = new MoneyAmount(RANDOM.nextInt(), EUROS, 
                (short) RANDOM.nextInt(100));
        String euroAmountStr = euroAmount.toString();
        String msgPart = "Trying to add " + dollarAmountStr + " to " 
                + euroAmountStr + ' ';
        String msg = msgPart + "should've caused CurrencyMismatchException";
        Throwable t = assertThrows(CurrencyMismatchException.class, () -> {
            MoneyAmount badAmount = dollarAmount.plus(euroAmount);
            System.out.println(msgPart + " is said to be " 
                    + badAmount.toString());
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        String dollarMsg = "Exception message should contain \"" 
                + dollarAmountStr + '"';
        assert excMsg.contains(dollarAmountStr) : dollarMsg;
        String euroMsg = "Exception message should contain \"" 
                + euroAmountStr + '"';
        assert excMsg.contains(euroAmountStr) : euroMsg;
    }
    
    @Test
    void testConstructorRejectsPseudoCurrencies() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (Currency currency : currencies) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                Throwable t = assertThrows(IllegalArgumentException.class, 
                        () -> {
                    MoneyAmount badAmount = new MoneyAmount(0, currency);
                    System.out.println("Instantiated amount " 
                            + badAmount.toString() + " with currency "  
                            + currency.getDisplayName() + " which has " 
                            + fractDigits + " fractional digits");
                });
                String excMsg = t.getMessage();
                assert excMsg != null : "Message should not be null";
                String symbol = currency.getSymbol();
                String msg = "Message should include symbol " + symbol 
                        + " for pseudo-currency " + currency.getDisplayName();
                assert excMsg.contains(symbol) : msg;
            }
        }
    }
    
    @Test
    void testConstructorRejectsNullCurrency() {
        int units = RANDOM.nextInt(1048576);
        Throwable t = assertThrows(NullPointerException.class, () -> {
            MoneyAmount amount = new MoneyAmount(units, null);
            System.out.println("Should not have created " 
                    + amount.getClass().getName() + '@' 
                    + Integer.toString(amount.hashCode(), 16) + " for " + units 
                    + " of null currency");
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
    }

}
