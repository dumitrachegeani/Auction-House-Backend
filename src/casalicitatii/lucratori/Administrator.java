package casalicitatii.lucratori;

import casalicitatii.produse.Produs;

import java.io.Serializable;
import java.util.List;

public final class Administrator extends Angajat implements Serializable {
    List<Produs> produsList;
    public Administrator(List<Produs> produsList) {
        this.produsList = produsList;
    }

    public void adaugaProduse() {
        new AdaugaProdus(produsList).start();
    }
}
