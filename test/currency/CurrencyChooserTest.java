package currency;

import static currency.MoneyAmountTest.*;

import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyChooserTest {
    
    private static final Set<Currency> CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    private static final Map<Integer, Set<Currency>> FRACT_DIGITS_MAP 
            = new HashMap<>();
    
    static {
        for (Currency currency : CURRENCIES) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                Set<Currency> digitGroupedSet;
                if (FRACT_DIGITS_MAP.containsKey(fractDigits)) {
                    digitGroupedSet = FRACT_DIGITS_MAP.get(fractDigits);
                } else {
                    digitGroupedSet = new HashSet<>();
                    FRACT_DIGITS_MAP.put(fractDigits, digitGroupedSet);
                }
                digitGroupedSet.add(currency);
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
    }

    @Test
    void testChooseCurrency() {
        System.out.println("chooseCurrency");
        int totalNumberOfCurrencies = CURRENCIES.size();
        int numberOfTries = 3 * totalNumberOfCurrencies / 2;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrency();
            String msg = "Chosen currency " + sample.getDisplayName() 
                    + " expected to not have negative fraction digits";
            assert sample.getDefaultFractionDigits() > -1 : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 7 * totalNumberOfCurrencies / 10;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " from set of " 
                + totalNumberOfCurrencies + " gave " + actual 
                + " distinct, should've given at least " + expected 
                + " distinct";
        assert expected < actual : msg;
    }

    @Test
    void testChooseCurrencyOtherThanDollars() {
        int numberOfTries = 20;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        String dollarsDisplayName = DOLLARS.getDisplayName();
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrencyOtherThan(DOLLARS);
            String msg = "Chosen currency " + sample.getDisplayName() 
                    + " expected to not be " + dollarsDisplayName;
            assert sample != DOLLARS : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 4 * numberOfTries / 5;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " other than " 
                + dollarsDisplayName + " gave " + actual 
                + " distinct, should've given at least " + expected 
                + " distinct";
        assert expected < actual : msg;
    }
    
    @Test
    void testChooseCurrencyOtherThan() {
        System.out.println("chooseCurrencyOtherThan");
        Currency someCurrency 
                = CurrencyChooser.chooseCurrencyOtherThan(DOLLARS);
        int numberOfTries = 20;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        String currencyDisplayName = someCurrency.getDisplayName();
        while (sampleNumber < numberOfTries) {
            Currency sample 
                    = CurrencyChooser.chooseCurrencyOtherThan(someCurrency);
            String msg = "Chosen currency " + sample.getDisplayName() 
                    + " expected to not be " + currencyDisplayName;
            assert sample != someCurrency : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int expected = 4 * numberOfTries / 5;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " other than " 
                + currencyDisplayName + " gave " + actual 
                + " distinct, should've given at least " + expected 
                + " distinct";
        assert expected < actual : msg;
    }
    
    @Test
    void testChooseCurrencyWithNoCentsOrDarahim() {
        int expected = 0;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String msg = "Chosen currency " + currency.getDisplayName() 
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, msg);
    }

    @Test
    void testChooseCurrencyWith100Cents() {
        int expected = 2;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String msg = "Chosen currency " + currency.getDisplayName() 
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, msg);
    }

    @Test
    void testChooseCurrencyWith1000Darahim() {
        int expected = 3;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String msg = "Chosen currency " + currency.getDisplayName() 
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, msg);
    }

    /**
     * Test of the chooseCurrency function of the CurrencyChooser class. If the 
     * requested number of fraction digits is 4, there might be only one answer 
     * the function can give: the Chilean unit of account (UF), ISO-4217 code 
     * CLF. One UF subdivides into what I'm going to call cents of cents until I 
     * can find a better term. It's actually kind of odd that the Chilean peso 
     * (CLP) no longer divides into cents like it used to. But that's a whole 
     * other topic.
     * <p>If your Java runtime does not recognize the Chilean UF nor any other 
     * currency with ten thousand divisions, maybe either ignore or comment this 
     * test out.</p>
     */
    @Test
    void testChooseCurrencyWith10000Divisions() {
        int expected = 4;
        Currency currency = CurrencyChooser.chooseCurrency(expected);
        int actual = currency.getDefaultFractionDigits();
        String msg = "Chosen currency " + currency.getDisplayName() 
                + " should have " + expected + " default fraction digits";
        assertEquals(expected, actual, msg);
    }
    
    @Test
    void testUnavailableFractionDigitsCauseException() {
        int unlikelyFractionDigits = Integer.MAX_VALUE;
        Throwable t = assertThrows(NoSuchElementException.class, () -> {
            Currency badCurrency 
                    = CurrencyChooser.chooseCurrency(unlikelyFractionDigits);
            System.out.println("Somehow asking for currency with " 
                    + unlikelyFractionDigits + " fraction digits gave " 
                    + badCurrency.getDisplayName());
        });
        String excMsg = t.getMessage();
        assert excMsg != null : "Message should not be null";
        System.out.println("\"" + excMsg + "\"");
        String digitString = Integer.toString(unlikelyFractionDigits);
        String msg = "Exception message should include \"" + digitString + "\"";
        assert excMsg.contains(digitString) : msg;
    }
    
    @Test
    void testChooseNoCentsCurrencyRandomlyEnough() {
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(0);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        for (int i = 0; i < total; i++) {
            chosenCurrencies.add(CurrencyChooser.chooseCurrency(0));
        }
        int expected = total / 2;
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total 
                + " currencies with no divisions, at least " + expected 
                + " should've been chosen, " + actual + " were chosen";
        assert actual >= expected : msg;
    }
    
    @Test
    void testChooseCentCurrencyRandomlyEnough() {
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(2);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        int maxCallCount = total / 8;
        int expected = 9 * maxCallCount / 10;
        for (int i = 0; i < maxCallCount; i++) {
            chosenCurrencies.add(CurrencyChooser.chooseCurrency(3));
        }
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total 
                + " currencies dividing into 100 cents, at least " + expected 
                + " should've been chosen after " + maxCallCount + " calls, " 
                + actual + " were chosen";
        assert actual >= expected : msg;
    }
    
    @Test
    void testChooseDarahimCurrencyRandomlyEnough() {
        Set<Currency> noCentCurrencies = FRACT_DIGITS_MAP.get(3);
        int total = noCentCurrencies.size();
        Set<Currency> chosenCurrencies = new HashSet<>();
        for (int i = 0; i < total; i++) {
            chosenCurrencies.add(CurrencyChooser.chooseCurrency(3));
        }
        int expected = total / 2;
        int actual = chosenCurrencies.size();
        String msg = "Out of " + total 
                + " currencies dividing into 1,000 darahim, at least " 
                + expected + " should've been chosen, " + actual 
                + " were chosen";
        assert actual >= expected : msg;
    }
    
}
