package currency;

import java.util.Currency;

public class MoneyAmount implements Comparable<MoneyAmount> {
    
    private final long singles;
    
    private final short cents;
    
    private final int multiplier;
    
    private final long allCents;
    
    private final Currency currencyID;
    
    private static int calculateMultiplier(Currency currency) {
        int mult = 1;
        int multCount = 0;
        int stop = currency.getDefaultFractionDigits();
        while (multCount < stop) {
            mult *= 10;
            multCount++;
        }
        return mult;
    }
    
    /**
     * Tells how many full units of currency there are in the amount. Any cents, 
     * mills, darahim, etc., are ignored.
     * @return The number of units. For example, if the amount is &euro;197.54, 
     * this function returns 197.
     */
    public long getUnits() {
        return this.singles;
    }
    
    /**
     * Tells how many divisions of the unit of currency there are in the amount. 
     * The units are multiplied as needed and the divisions are added.
     * @return The number of divisions of the unit of currency. For example, if 
     * the amount is &euro;197.54, this function returns 19754.
     */
    public long getFullAmountInCents() {
        return this.allCents;
    }
    
    /**
     * Tells how many divisions of the unit of currency there are in the amount 
     * minus the full units.
     * @return The number of divisions of the unit of currency minus the full 
     * units. For example, if the amount is &euro;197.54, this function returns 
     * 54.
     */
    public short getDivisions() {
        return this.cents;
    }
    
    /**
     * Tells what currency this money amount is drawn in.
     * @return The currency. For example, if the amount is &euro;197.54, this 
     * function returns EUR.
     */
    public Currency getCurrency() {
        return this.currencyID;
    }
    
    // TODO: Refactor to reduce cyclomatic complexity
    @Override
    public String toString() {
        if (this.multiplier == 1) {
            return this.currencyID.getSymbol() + this.singles;
        }
        String intermediate = this.currencyID.getSymbol() 
                + Math.abs(this.singles) + '.';
        if (this.singles < 0) {
            intermediate = '-' + intermediate;
        }
        if (this.cents < 10) {
            intermediate += "0";
        }
        if (this.cents < 100 && this.multiplier == 1000) {
            intermediate += "0";
        }
        return intermediate + this.cents;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        MoneyAmount other = (MoneyAmount) obj;
        if (this.currencyID != other.currencyID) {
            return false;
        }
        return this.allCents == other.allCents;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    // TODO: Write tests for this
    public int compareTo(MoneyAmount other) {
        return 0;
    }
    
    public MoneyAmount(long units, Currency currency) {
        this(units, currency, (short) 0);
    }

    public MoneyAmount(long units, Currency currency, short divisions) {
        if (currency == null) {
            String excMsg = "Currency must not be null";
            throw new NullPointerException(excMsg);
        }
        int fractDigits = currency.getDefaultFractionDigits();
        if (fractDigits < 0) {
            String excMsg = "Currency " + currency.getSymbol() 
                    + " is not valid";
            throw new IllegalArgumentException(excMsg);
        }
        this.singles = units;
        this.cents = divisions;
        this.multiplier = calculateMultiplier(currency);
        this.currencyID = currency;
        this.allCents = this.singles * this.multiplier + this.cents;
    }
    
    private MoneyAmount(Currency currency, long fullAmountInCents, 
            int verifiedMultiplier) {
        this.allCents = fullAmountInCents;
        this.singles = this.allCents / verifiedMultiplier;
        long adjust = this.singles * verifiedMultiplier;
        this.cents = (short) (this.allCents - adjust);
        this.multiplier = verifiedMultiplier;
        this.currencyID = currency;
    }

}
