package currency;

import static currency.MoneyAmountTest.*;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyChooserTest {
    
    private static final Set<Currency> CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    static {
        for (Currency currency : CURRENCIES) {
            if (currency.getDefaultFractionDigits() < 0) {
                PSEUDO_CURRENCIES.add(currency);
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

}
