package currency;

import java.util.Currency;

public class MoneyAmount implements Comparable<MoneyAmount> {
    
    private final short cents;
    
    @Override
    public String toString() {
        String intermediate = "$0.";
        if (this.cents < 10) {
            intermediate += "0";
        }
        return intermediate + this.cents;
    }
    
    public int compareTo(MoneyAmount other) {
        return 0;
    }
    
    public MoneyAmount(long units, Currency currency) {
        this(units, currency, (short) 0);
    }

    public MoneyAmount(long units, Currency currency, short divisions) {
        this.cents = divisions;
    }

}
