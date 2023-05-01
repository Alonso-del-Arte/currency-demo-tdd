package currency;

import java.util.Currency;

public class MoneyAmount implements Comparable<MoneyAmount> {
    
    public int compareTo(MoneyAmount other) {
        return 0;
    }
    
    public MoneyAmount(long units, Currency currency) {
        this(units, currency, (short) 0);
    }

    public MoneyAmount(long units, Currency currency, short divisions) {
        //
    }

}
