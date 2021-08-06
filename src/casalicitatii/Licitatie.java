package casalicitatii;

import casalicitatii.exceptii.BelowMinimumPriceException;
import casalicitatii.exceptii.NoWinnerException;
import casalicitatii.lucratori.Broker;
import casalicitatii.produse.Produs;

import java.io.Serializable;
import java.util.*;

/**
 * Clasa licitatie
 * Aceasta implementeaza Runnable pentru a fi rulata ca thread metoda
 * ce simuleaza o licitatie maximPasi pasi
 */
public class Licitatie implements Runnable, Serializable {
    private Produs produs;
    private int maximParticipanti;
    private int maximPasi;
    private List<Broker> participanti;
    private double pretCurent;
    private List<Broker> saracii;
    private CasaLicitatie casaLicitatie;

    // constructor
    public Licitatie(CasaLicitatie casaLicitatie, Produs produs, int maximParticipanti, int maximPasi) {
        this.produs = produs;
        this.maximParticipanti = maximParticipanti;
        this.maximPasi = maximPasi;
        this.participanti =  Collections.synchronizedList(new ArrayList<>());
        this.pretCurent = produs.getPretMinim();
        this.saracii = new ArrayList<>();
        this.casaLicitatie = casaLicitatie;
    }

    // adauga participantii
    public void adaugaParticipant(Broker broker) {
        participanti.add(broker);
    }

    //ia produsul pentru care se bat participantii
    public Produs getProdus() {
        return produs;
    }

    //ia numarul de participanti
    public int getMaximParticipanti() {
        return maximParticipanti;
    }

    //  ia numarul de pasi
    public int getMaximPasi() {
        return maximPasi;
    }

    // ia numarul de participanti
    public List<Broker> getParticipanti() {
        return participanti;
    }

    /**
     * Simularea unei licitatii ce se formeaa in k pasi
     * In caz ca ramane fara niciun client (toti sunt "saraci) SAU pretulCurent final e mai mic decat
     * pretul minim al produsului nu scoate produsul din lista
     * In caz ca ramane doar un participant se opreste si il declara castigator
     * In caz ca raman mai multi clienti apelez fucnite de selectWiner sa aleaga.
     */
    @Override
    public void run() {
        boolean scotProdus = true;
        // un client random alege pretul de inceput pentru produs
        pretCurent = propunePretNouRandom(participanti, (new Random().nextInt(100) + 10));
        for (int pas = 0 ; pas < maximPasi; pas++) {
            for (Broker participant : participanti) {
                //daca nu au destui bani cat le trebuie sa continue o sa iasa
                if (!participant.cereBaniClientului(pretCurent))
                    saracii.add(participant);
            }
            if (pretCurent < produs.getPretMinim())
                try {
                    throw new BelowMinimumPriceException();
                } catch (BelowMinimumPriceException e) {
                    scotProdus = false;
                    break;
                }
            //eliminam participantii care n-au platit
            participanti.removeAll(saracii);
            // daca a ramas doar un participant care si-a permis pretul propus castiga
            if (participanti.size() == 1) {
                participanti.get(0).win();
                break;
            }
            //daca niciun participant nu si-a permis pretul (un "fraier" a propus prea mult)
            if (participanti.size() == 0) {
                System.out.println("Cine a propus pretul a fost fraier, nu a castigat nimeni");
                scotProdus = false;
                break;
            }
            // cu fiecare pas creste pretul produsului
            pretCurent = propunePretNouRandom(participanti, this.pretCurent);
        }
        //daca s-a terminat licitatia si au ramas mai multi clienti care si-au permis pretul
        //il alegem pe cel ce a castigat cele mai multe licitatii
        try{
            selectWinner(participanti).win();
        }
        catch (NoSuchElementException ignored) {

        }
        casaLicitatie.notifyBrokers(participanti, produs);
        System.out.println("s-a terminat licitatia");
        if (scotProdus)
            casaLicitatie.getProduseDeVandut().remove(produs);
        //Afisez sa vad ce produse au ramas
        System.out.println(casaLicitatie.getProduseDeVandut());
    }

    private Broker selectWinner(List<Broker> participanti) {
        return participanti.stream().max(Comparator.comparing(Broker::getCastiguriClient))
                .orElseThrow(()-> new NoWinnerException(produs.getId()));
    }

    /**
     * alege din lista de participanti unul random si brokerul respectiv
     * cere un nou pret random pentru produsul la licitat clientului pe care il reprezinta
     * (!!! CLIENTUL trimite pretul pe care il CERE brokerul)
     * @param participanti
     * @param pretCurent
     * @return
     */
    private double propunePretNouRandom(List<Broker> participanti, double pretCurent) {
        Collections.shuffle(participanti);
        return participanti.get(0).cerePretPropus(pretCurent);
    }
}
