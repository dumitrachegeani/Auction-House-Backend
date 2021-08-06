package casalicitatii.produse;

import casalicitatii.Culori;

import java.io.Serializable;

public final class Tablou extends Produs implements Serializable {
    private String numePictor;
    private Culori culori;

    public Tablou(int id, String nume, double pretVanzare, double pretMinim, int an, String numePictor, String culori) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.numePictor = numePictor;
        this.culori = Culori.valueOf(culori);
    }

    public String getNumePictor() {
        return numePictor;
    }

    public void setNumePictor(String numePictor) {
        this.numePictor = numePictor;
    }

    public Culori getCulori() {
        return culori;
    }

    public void setCulori(Culori culori) {
        this.culori = culori;
    }
}
