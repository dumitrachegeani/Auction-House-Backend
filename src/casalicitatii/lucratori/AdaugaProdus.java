package casalicitatii.lucratori;

import casalicitatii.exceptii.BrokerListEmpty;
import casalicitatii.Main;
import casalicitatii.produse.Mobila;
import casalicitatii.produse.Produs;

import java.time.LocalDateTime;
import java.util.List;

//PRODUCER DESING PATTERN / COMMAND / BROKER
public class AdaugaProdus extends Thread {
    List<Produs> produsList;
    int id = 30;
    AdaugaProdus(List<Produs> produsList ){
        this.produsList = produsList;
    }
    public void run() {
        while (id < 40) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                Produs produs = new Mobila(id++, "NUME: " + id, 100, 100, LocalDateTime.now().getYear(), "masa", "lemn");
                produsList.add(produs);
                System.out.println("Am adaugat " + produs);
            }
        try {
            Main.test10();
        } catch (BrokerListEmpty brokerListEmpty) {
            brokerListEmpty.printStackTrace();
        }
    }

}