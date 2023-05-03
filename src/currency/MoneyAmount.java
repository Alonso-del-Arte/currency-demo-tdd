package currency;

import java.util.Currency;

public class MoneyAmount implements Comparable<MoneyAmount> {
    
    private final long dollars;
    
    private final short cents;
    
    // TODO: Write tests for this
    public long getUnits() {
        return -1;
    }
    
    // TODO: Write tests for this
    public long getFullAmountInCents() {
        return -1;
    }
    
    // TODO: Write tests for this
    public short getCents() {
        return -1;
    }
    
    // TODO: Write tests for this
    public Currency getCurrency() {
        return Currency.getInstance("AED");
    }
    
    @Override
    public String toString() {
        String intermediate = "$" + this.dollars + '.';
        if (this.cents < 10) {
            intermediate += "0";
        }
        return intermediate + this.cents;
    }
    
    // TODO: Write tests for this
    public int compareTo(MoneyAmount other) {
        return 0;
    }
    
    public MoneyAmount(long units, Currency currency) {
        this(units, currency, (short) 0);
    }

    public MoneyAmount(long units, Currency currency, short divisions) {
        int fractDigits = currency.getDefaultFractionDigits();
        if (fractDigits < 0) {
            String excMsg = "Currency " + currency.getSymbol() 
                    + " is not valid";
            throw new IllegalArgumentException(excMsg);
        }
        this.dollars = units;
        this.cents = divisions;
    }

}
