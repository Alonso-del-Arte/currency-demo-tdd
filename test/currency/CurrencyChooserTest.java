package currency;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
        int expected = 9 * totalNumberOfCurrencies / 10;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " from set of " 
                + totalNumberOfCurrencies + " gave " + actual 
                + " distinct, should've given at least " + expected 
                + " distinct";
        assert expected < actual : msg;
    }

}
