package currency;

public class CurrencyMismatchException extends RuntimeException {
    
    private static final long serialVersionUID = 4556099108573545874L;

    public CurrencyMismatchException(MoneyAmount amountA, MoneyAmount amountB) {
        this("SORRY, DEFAULT MESSAGE NOT IMPLEMENTED YET", amountA, amountB);
    }
    
    public CurrencyMismatchException(String msg, MoneyAmount amountA, 
            MoneyAmount amountB) {
//        this
    }
    
}
