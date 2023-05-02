package currency;

import java.util.Currency;

public abstract class CurrencyConverter {
    
    public abstract double rate(Currency source, Currency target);
    
    public MoneyAmount convert(MoneyAmount source, Currency target) {
        return source;
    }

}
