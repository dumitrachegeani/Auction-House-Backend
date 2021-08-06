package casalicitatii.test;

import casalicitatii.*;
import casalicitatii.csv.CsvBrokers;
import casalicitatii.csv.CsvClients;
import casalicitatii.csv.CsvProducts;
import casalicitatii.exceptii.AlreadyParticipatingException;
import casalicitatii.exceptii.BrokerListEmpty;
import casalicitatii.lucratori.Broker;
import casalicitatii.produse.Bijuterie;
import casalicitatii.produse.Mobila;
import casalicitatii.produse.Produs;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CasaLicitatieTest {
    CasaLicitatie casaLicitatie;

    /**
     * verific daca getInstance poate returna null
     */
    //1
    @Test
    void getInstance() {
        casaLicitatie = CasaLicitatie.getInstance();
        assertNotNull(casaLicitatie);
    }

    /**
     * verific daca se adauga cate produse ar trebui
     */
    //2
    @Test
    void adaugaProduse() {
        List<Produs> produse = new ArrayList<>();
        produse.add(new Mobila(0, "numetest", 10, 10, 2000, "masa", "lemn" ));
        produse.add(new Bijuterie(0, "numetest1", 10, 10, 2000, "DIAMANT", "true" ));
        casaLicitatie.adaugaProduse(produse);
        assertEquals(casaLicitatie.getProduseDeVandut().size(), produse.size(), "Nicio eroare in adaugarea listei de produse");
    }

    /**
     * verific daca sunt aceleasi produse
     */
    //3
    @Test
    void getProduseDeVandut() {
        List<Produs> produse = new ArrayList<>();
        produse.add(new Mobila(0, "numetest", 10, 10, 2000, "masa", "lemn" ));
        produse.add(new Bijuterie(0, "numetest1", 10, 10, 2000, "DIAMANT", "true" ));
        casaLicitatie.adaugaProduse(produse);
        assertEquals(casaLicitatie.getProduseDeVandut(), produse, "Produsele sunt aceleasi");
    }

    /**
     * verifica daca adauga cati brokeri zice
     */
    //4
    @Test
    void adaugaBrokeri() {
        List<Broker> brokeri = new ArrayList<>();
        brokeri.add(new Broker("nume1",  (List<Produs>)casaLicitatie.getProduseDeVandut(), 30));
        brokeri.add(new Broker("nume2", (List<Produs>)casaLicitatie.getProduseDeVandut(), 33));
        casaLicitatie.adaugaBrokeri(brokeri);
        assertEquals(casaLicitatie.getBrokeri().size(), brokeri.size(), "Nicio eroare in adaugarea listei de brokeri");
    }

    /**
     * verifica daca dejaParticipa arunca exceptia cand trebuie
     * @throws FileNotFoundException
     */
    //5
    @Test
    void dejaParticipa() throws FileNotFoundException {
        List<Produs> produsList = (new CsvProducts()).readProducts("file.txt");
        casaLicitatie.adaugaProduse(produsList);
        List<Broker> brokers = (new CsvBrokers()).readBrokers("brokers.txt", casaLicitatie.getProduseDeVandut());
        casaLicitatie.adaugaBrokeri(brokers);

        assertThrows(AlreadyParticipatingException.class, () -> {
            Client c1 = new PersoanaFizica(1, "nume", "adresa", 300, "18.20.2");
            c1.cumpara(0, casaLicitatie, 100);
            c1.cumpara(0,casaLicitatie,100);
        });
    }

    //6

    /**
     * verifica daca cumpara in cazul in care nu sunt brokeri chiar returneeaza exceptia
     * BrokerListEmpty
     * @throws FileNotFoundException
     */
    @Test
    void CumparaFaraBrokeri() throws FileNotFoundException {
        List<Produs> produsList = (new CsvProducts()).readProducts("file.txt");
        casaLicitatie.adaugaProduse(produsList);
        assertThrows(BrokerListEmpty.class, () -> {
            Client c = new PersoanaJuridica(1, "nume", "adresa", 1000, Companie.SA, 1000);
            c.cumpara(0, casaLicitatie, 1000);
        });
    }

    /**
     * verifica daca merge exceptia cand nu exista produsul gasit
     */
    //7
    @Test
    void CautaProdusExceptie() {
        assertThrows(BrokerListEmpty.class, () -> {
            List<Produs> produsList = (new CsvProducts()).readProducts("file.txt");
            casaLicitatie.adaugaProduse(produsList);
            casaLicitatie.cautaProdus(1000);
        });
    }

    /**
     * verific dcaa am exceptie cand citesc din fisier care nu exista
     */
    //8
    @Test
    void CitesteProduse() {
        assertThrows(FileNotFoundException.class, () -> {
            (new CsvProducts()).readProducts("fisierInexistent.txt");
        });
    }

    /**
     * verific daca am exceptie cand citesc brokerii
     */
    //9
    @Test
    void CitesteBrokeri() {
        assertThrows(FileNotFoundException.class, () -> {
            List<Produs> produsList = (new CsvProducts()).readProducts("file.txt");
            (new CsvBrokers()).readBrokers("fisierInexistent.txt", produsList);
        });
    }

    /**
     * verific daca am exceptie cand citesc clientii
     */
    //10
    @Test
    void CitesteClienti() {
        assertThrows(FileNotFoundException.class, () -> {
            (new CsvClients()).readClients("fisierInexistent.txt");
        });
    }

}