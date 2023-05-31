package currency;

import static currency.MoneyAmountTest.RANDOM;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class CurrencyChooser {
    
    private static final List<Currency> CURRENCIES 
            = new ArrayList<>(Currency.getAvailableCurrencies());

    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();

    private static final Map<Integer, Currency> CURRENCIES_DIGITS_MAP 
            = new HashMap<>();

    static {
        for (Currency currency : CURRENCIES) {
            int fractionDigits = currency.getDefaultFractionDigits(); 
            if (fractionDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                CURRENCIES_DIGITS_MAP.put(fractionDigits, currency);
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
        if (fractionDigits == 2) {
            return Currency.getInstance(java.util.Locale.CANADA);
        }
        return Currency.getInstance(java.util.Locale.JAPAN);
    }

    /**
     * Chooses a currency suitable for the {@link MoneyAmount} constructor 
     * other than the specified currency.
     * @param currency The currency not to choose. For example, the U.&nbsp;S. 
     * dollar (USD). Pseudo-currencies like gold (XAU) may be used, but then the 
     * effect is the same as calling {@link #chooseCurrency()} without any 
     * parameters.
     * @return A currency other than <code>currency</code>. For example, given 
     * USD, this function might return the Panamanian balboa (PAB). For what 
     * it's worth, the balboa is one of two official currencies of Panama, the 
     * other is the U.&nbsp;S. dollar.
     */
    public static Currency chooseCurrencyOtherThan(Currency currency) {
        Currency otherCurrency = currency;
        while (otherCurrency == currency) {
            otherCurrency = chooseCurrency();
        }
        return otherCurrency;
    }

}
