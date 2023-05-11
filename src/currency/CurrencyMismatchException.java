package currency;

public class CurrencyMismatchException extends RuntimeException {
    
    private static final long serialVersionUID = 4556099108573545874L;
    
    private final MoneyAmount amountA, amountB;
    
    // TODO: Write tests for this
    public MoneyAmount getAmountA() {
        return this.amountB;
    }

    // TODO: Write tests for this
    public MoneyAmount getAmountB() {
        return this.amountA;
    }

    public CurrencyMismatchException(MoneyAmount amtA, MoneyAmount amtB) {
        this("SORRY, DEFAULT MESSAGE NOT IMPLEMENTED YET", amtA, amtB);
    }
    
    public CurrencyMismatchException(String msg, MoneyAmount amtA, 
            MoneyAmount amtB) {
        this.amountA = amtA;
        this.amountB = amtB;
    }
    
}
