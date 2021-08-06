package casalicitatii.csv;

import casalicitatii.Client;
import casalicitatii.Companie;
import casalicitatii.PersoanaFizica;
import casalicitatii.PersoanaJuridica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvClients {
    private List<Client> clienti = new ArrayList<>();

    public List<Client> readClients(String numeFisier) throws FileNotFoundException {

        //parsing a CSV file into Scanner class constructor
        Scanner scanner = new Scanner(new File(numeFisier));
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            Client client;
            client = read(scanner);
            clienti.add(client);
        }
        scanner.close();
        return clienti;
    }

    //citeste un singur rand (un singur spectacol)
    private Client read(Scanner scanner) {
        String nume = scanner.next().trim();
        int tip = scanner.nextInt();
        int id = scanner.nextInt();
        String adresa = scanner.next().trim();
        double bani = scanner.nextDouble();
        if (tip == 1) {
            String dataNastere = scanner.next();
            return new PersoanaFizica(id, nume, adresa, bani, dataNastere);
        }
        else {
            String companie = scanner.next();
            double capitalSocial  = scanner.nextDouble();

            return new PersoanaJuridica(id, nume, adresa,bani, Companie.valueOf(companie), capitalSocial );
        }
    }
}
