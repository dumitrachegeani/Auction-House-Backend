package casalicitatii.csv;

import casalicitatii.produse.Produs;
import casalicitatii.produse.ProdusFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Citeste fisiere dintr-un fisier CSV produsele
 */
public class CsvProducts {
    private final List<Produs> produse = Collections.synchronizedList(new ArrayList<>());

    public List<Produs> readProducts(String numeFisier) throws FileNotFoundException {

        //parsing a CSV file into Scanner class constructor
        Scanner scanner = new Scanner(new File(numeFisier));
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            Produs produs;
            produs = read(scanner);
            produse.add(produs);
        }
        scanner.close();
        return produse;
    }

    /**
     * Citeste un singur produs
     * @param scanner
     * @return
     */
    private Produs read(Scanner scanner) {
        String tip = scanner.next().trim();
        int id = scanner.nextInt();
        String nume = scanner.next();
        double pretMinim = scanner.nextDouble();
        int an = scanner.nextInt();
        String arg1 =scanner.next();
        String arg2 = scanner.next();
        return ProdusFactory.getProdus(tip, id, nume, pretMinim, pretMinim, an, arg1, arg2);
    }
}
