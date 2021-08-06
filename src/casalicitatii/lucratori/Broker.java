package casalicitatii.lucratori;

import casalicitatii.Client;
import casalicitatii.PersoanaFizica;
import casalicitatii.PersoanaJuridica;
import casalicitatii.produse.Produs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Broker extends Angajat implements Serializable {
    private List<Client> clienti;
    private Client clientCurent;
    private int profit;
    private List<Produs> produsList;
    private String nume;
    private int suma;
    private double pretMaxim;
    private boolean castigator;
    private int profi;

    // constructor
    public Broker(String nume, List<Produs> produsList, int profi) {
        this.nume = nume;
        this.clienti = new ArrayList<>();
        this.produsList = produsList;
        this.profi = profi;
    }

    /**
     * ii adauga in lsta de clienti
     * @param client il adauga in lista de clienti
     * @param pretMaxim ii cere pretul maxim sa stie cand sa se opreasca
     */
    public void alocaClient(Client client, double pretMaxim) {
            clientCurent = client;
            clienti.add(client);
            this.pretMaxim = pretMaxim;
    }

    /**
     * cere bani clientului
     * @param pretCurent pretul curent al produsului la care trebuie sa ajunga suma
     *                   pe care o da clientul
     * @return
     */
    public boolean cereBaniClientului(double pretCurent) {
        if (pretCurent > pretMaxim)
            return false;
        suma += clientCurent.oferaBani(pretCurent - suma);
        return true;
    }

    // marcheaza brokerul ca castigator (pentru cand anunta clientul)
    public void win() {
        castigator = true;
    }

    //propune un nou pret bazat pe nebunia
    // brokerului, insa daca ar
    //depasi suma maxima o ofera pe aceea.
    // "profi" e cat de mult ii influenteaza pe clienti sa parieze
    public double cerePretPropus(double pretCurent) {
        return clientCurent.propunePret(pretCurent, profi);
    }

    public int getCastiguriClient() {
       return clientCurent.getNrLicitatiiCastigate();
    }

    /**
     * anuna clientii in vedere cu castigul licitatiei
     * @param produs
     */
    public void notificaClient(Produs produs) {
        try {
            profit += commission();
        }catch (Exception e ){
            e.printStackTrace();
        }
        if (castigator) {
            clientCurent.castiga(produs);
        }
        else
            clientCurent.pierde((suma-profit));
    }

    //reseteaza starile unui broker cand acesta termina o licitatie
    public void reset() {
        castigator = false;
        clientCurent = null;
        suma = 0;
    }

    /**
     * returneaza comisionul pe care il ia brokerul bazat pe suma tranzactionata
     * COMISIONUL ESTE AL BROKERULUI DE ACEEA ESTE AICI
     * @return
     * @throws Exception
     */
    public double commission() throws Exception {
        if (clientCurent instanceof PersoanaJuridica)
            if (clientCurent.getNrParticipari() < 25)
                return 0.25 * suma;
            else
                return 0.1 * suma;
        else if (clientCurent instanceof PersoanaFizica)
            if (clientCurent.getNrParticipari() < 5)
                return 0.2 * suma;
            else
                return 3.0/20 * suma;
        else throw new Exception();
    }

    // obtine nume client
    public String getNumeClient() {
        return clientCurent.getNume();
    }

    // incrementeaza numarul de participari la care a...participat clientul
    public void clientParticipariPlusPlus() {
        clientCurent.incrementParticipare();
    }
}
