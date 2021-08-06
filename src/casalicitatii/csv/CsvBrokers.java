package casalicitatii.csv;

import casalicitatii.lucratori.Broker;
import casalicitatii.produse.Produs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvBrokers {
    private List<Broker> brokers = new ArrayList<>();

    public List<Broker> readBrokers(String numeFisier, List<? extends Produs> produsList) throws FileNotFoundException {

        //parsing a CSV file into Scanner class constructor
        Scanner scanner = new Scanner(new File(numeFisier));
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            Broker broker;
            broker = read(scanner, (List<Produs>) produsList);
            brokers.add(broker);
        }
        scanner.close();
        return brokers;
    }

    //citeste un singur rand (un singur spectacol)
    private Broker read(Scanner scanner, List<Produs> produsList) {
        String nume = scanner.next().trim();
        int nebunie = scanner.nextInt();
        return new Broker(nume, produsList, nebunie);
    }
}
