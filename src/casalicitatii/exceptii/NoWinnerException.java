package casalicitatii.exceptii;

import javax.swing.plaf.SeparatorUI;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class NoWinnerException extends NoSuchElementException{
    public NoWinnerException(int id) {
        super("Nu am gasit castigator la licitatia pentru produsul " + id);
    }
}
