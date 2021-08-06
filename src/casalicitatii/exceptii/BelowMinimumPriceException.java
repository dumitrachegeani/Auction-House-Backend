package casalicitatii.exceptii;

public class BelowMinimumPriceException extends Exception {
    public BelowMinimumPriceException() {
        super("Nu s-a platit suficient");
    }
}
