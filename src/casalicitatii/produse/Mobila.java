package casalicitatii.produse;

import java.io.Serializable;

public final class Mobila extends Produs implements Serializable {
    private String tip;
    private String material;

    public Mobila(int id, String nume, double pretVanzare, double pretMinim, int an, String tip, String material) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.tip = tip;
        this.material = material;
    }

    public String getTip() {
        return tip;
    }

    public String getMaterial() {
        return material;
    }
}
