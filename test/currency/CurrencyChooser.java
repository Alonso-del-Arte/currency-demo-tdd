package currency;

import static currency.MoneyAmountTest.RANDOM;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CurrencyChooser {
    
    private static final List<Currency> CURRENCIES 
            = new ArrayList<>(Currency.getAvailableCurrencies());

    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();

    static {
        for (Currency currency : CURRENCIES) {
            if (currency.getDefaultFractionDigits() < 0) {
                PSEUDO_CURRENCIES.add(currency);
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
    }

    /**
     * Chooses a currency suitable for the {@link MoneyAmount} constructor.
     * @return A currency with default fraction digits of at least 0. For 
     * example, the Kyrgystani som (KGS), which like most world currencies by 
     * default has two fractional digits. A som is divided into 100 tyin.
     */
    public static Currency chooseCurrency() {
        int index = RANDOM.nextInt(CURRENCIES.size());
        return CURRENCIES.get(index);
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(int fractionDigits) {
        return chooseCurrency();
    }

    // TODO: Write tests for this
    public static Currency chooseCurrencyOtherThan(Currency currency) {
        Currency otherCurrency = currency;
        while (otherCurrency == currency) {
            otherCurrency = chooseCurrency();
        }
        return otherCurrency;
    }

}
