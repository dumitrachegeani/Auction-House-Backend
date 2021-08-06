package casalicitatii;

import casalicitatii.exceptii.AlreadyParticipatingException;
import casalicitatii.exceptii.BrokerListEmpty;
import casalicitatii.exceptii.ProductNotFoundExcpetion;
import casalicitatii.lucratori.Administrator;
import casalicitatii.lucratori.Broker;
import casalicitatii.produse.Produs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Casa licitatie design pattern single tone
 */

public class CasaLicitatie implements Serializable {
    private static CasaLicitatie casaLicitatie = null;
    private List<? extends Produs> produseDeVandut;
    private final List<Licitatie> licitatiiActive;
    private List<Broker> brokerList;
    private Administrator administrator;

    //constructor
    private CasaLicitatie () {
        this.licitatiiActive = new ArrayList<>();
        this.brokerList = new ArrayList<>();
    }

    //Single tone
    public static CasaLicitatie getInstance() {
        if (casaLicitatie == null)
            casaLicitatie = new CasaLicitatie();
        return casaLicitatie;
    }

    // Una dintre cele mai importante metode - aici incepe licitatia
    public void alocaBroker(int idProdus, Client client, double pretMaxim) throws BrokerListEmpty, AlreadyParticipatingException {
        //amestec lista sa iau un broker random
        Collections.shuffle(brokerList);
        Broker broker;
        try {
           broker = brokerList.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            throw new BrokerListEmpty("NU MAI AM BROKERI!!!!");
        }

        //de acum nu se mai ocupa casa de ei
        broker.alocaClient(client, pretMaxim);
        client.setBroker(broker);
        
        //adauga brokerul la licitatia pentru produsul id
        Licitatie licitatie;
        try {
           licitatie = cauatLicitatie(idProdus);
        }
        catch (ProductNotFoundExcpetion e) {
            System.out.println("Nu am gasit produsul cu id: " + idProdus);
            return;
        }
        //daca nu participa deja la aceasta licitatie
        if (!participaDeja(broker, licitatie)) {
            licitatie.adaugaParticipant(broker);
            broker.clientParticipariPlusPlus();
        }
        else throw new AlreadyParticipatingException();
        //daca licitatia nu era activa o adaugam acum
        if (!licitatiiActive.contains(licitatie))
            licitatiiActive.add(licitatie);
        //incepe threadul de licitatie
        if (licitatie.getParticipanti().size() == licitatie.getMaximParticipanti()) {
            System.out.println("A INCEPUT LICITATIA");
            licitatiiActive.remove(licitatie);
             Thread t1 = new Thread(licitatie);
             t1.start();
        }
    }

    /**
     * @param broker broker al carui client il verific
     * @param licitatie licitatia in care verific daca exista deja
     * @return returneaza adevarat daca clientul brokerului participa deja la licitatia respectiva
     */
    private boolean participaDeja(Broker broker, Licitatie licitatie) {
        return licitatie.getParticipanti().stream().anyMatch(p-> p.getNumeClient().equals(broker.getNumeClient()));
    }

    /**
     *Cauta licitatia dupa id-ul produsului
     * @param idProdus
     * @return
     * @throws ProductNotFoundExcpetion
     */
    private Licitatie cauatLicitatie(int idProdus) throws ProductNotFoundExcpetion {
        return licitatiiActive.stream()
                .filter(licitatie -> licitatie.getProdus().getId() == idProdus)
                .findFirst()
                .orElse(new Licitatie(this, cautaProdus(idProdus), 10, 10));
    }

    /**
     * Cauta produsul dupa id in lista de produse
     * @param id
     * @return
     * @throws ProductNotFoundExcpetion
     */
    public Produs cautaProdus(int id) throws ProductNotFoundExcpetion {
        List<Produs>  aux = new ArrayList<>();
        aux.addAll(produseDeVandut);
        return aux.stream()
                .filter(produs -> produs.getId() == id)
                .findFirst().
                orElseThrow(() -> new ProductNotFoundExcpetion("Produsul nu a fost gasit : " + id));
    }

    // initializeaza lista de produse cu lsta sincronizata obtinuta din citire
    public void adaugaProduse(List<? extends Produs> produseDeVandut) {
        this.produseDeVandut = produseDeVandut;
    }

    // returneaza produsul
    public List<? extends Produs> getProduseDeVandut() {
        return produseDeVandut;
    }
// initializeaza lista de brokeri cu cea citita
    public void adaugaBrokeri(List<Broker> brokerList) {
        this.brokerList = brokerList;
    }

    /**
     * trimite semnal catre toti brokerii sa-si anunte clientii despre reusite/esec
     * @param participanti
     * @param produs
     */
    public void notifyBrokers(List<Broker> participanti, Produs produs) {
        //brokerii inapoi pe pozitii
        for (Broker broker : participanti) {
            broker.notificaClient(produs);
            broker.reset();
            brokerList.add(broker);
        }
    }

    public void setAdmin(Administrator administrator) {
        this.administrator = administrator;
    }

    public List<Broker> getBrokeri() {
        return brokerList;
    }
}
