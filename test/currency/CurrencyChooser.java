package currency;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class CurrencyChooser {
    
    // TODO: Write tests for this
    public static Currency chooseCurrency() {
        return Currency.getInstance(Locale.CANADA);
    }

}
