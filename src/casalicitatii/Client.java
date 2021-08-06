package casalicitatii;

import casalicitatii.exceptii.AlreadyParticipatingException;
import casalicitatii.exceptii.BrokerListEmpty;
import casalicitatii.lucratori.Broker;
import casalicitatii.produse.Produs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa sigilata perimte doar PersoaaFizica si PersoanaJuridica sa o extinda
 *
 */
public sealed class Client implements Serializable permits PersoanaFizica, PersoanaJuridica {
    private int id;
    private String nume;
    private String adresa;
    private int nrParticipari;
    private int nrLicitatiiCastigate;
    private Broker broker;
    private double bani;
    private List<Produs> produseCastigate;

    public Client(int id, String nume, String adresa, double bani) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.produseCastigate = new ArrayList<>();
        this.bani = bani;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNrParticipari() {
        return nrParticipari;
    }

    public void incrementParticipare(){
        nrParticipari++;
    }

    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    public void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    /**
     * Metoda cu care clientul plaseaza o comanda catre casa de licitatii
     * @param id produsul dorit
     * @param casaLicitatie casa de licitatie de la care cumpar produsul
     * @param pretMaxim pretul maxim pe care il da pe produs
     * @throws BrokerListEmpty returneaza in caz ca nu mai am brokeri
     * @throws AlreadyParticipatingException returneaza in caz ca clientul deja participa la aceeasi licitatie
     * @throws InterruptedException exceptie specifica threadului
     */
    public void cumpara(int id, CasaLicitatie casaLicitatie, double pretMaxim) throws BrokerListEmpty, AlreadyParticipatingException, InterruptedException {
        casaLicitatie.alocaBroker(id, this, pretMaxim);
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public double oferaBani(double diferenta) {
        bani -= diferenta;
        return diferenta;
    }

    public void castiga(Produs produs) {
        System.out.println(nume + ":  YEEEY AM CASTIGAT UN " + produs + "!!!");
        produseCastigate.add(produs);
    }

    public void pierde(double baniInapoi) {
        bani += baniInapoi;
        System.out.println(nume + ":  off....am pierdut...");
    }

    public double propunePret(double pretCurent, int profi) {
        double pretPropus  = profi /100 * pretCurent + pretCurent;
        return Math.min(bani, pretPropus);
    }
}
