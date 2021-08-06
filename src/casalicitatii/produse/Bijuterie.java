package casalicitatii.produse;

import java.io.Serializable;

public final class Bijuterie extends Produs implements Serializable {
    private String material;
    private boolean piatraPretioasa;

    public Bijuterie(int id, String nume, double pretVanzare, double pretMinim, int an, String material, String piatraPretioasa) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.material = material;
        this.piatraPretioasa = Boolean.getBoolean(piatraPretioasa);
    }

    public String getMaterial() {
        return material;
    }

    public boolean isPiatraPretioasa() {
        return piatraPretioasa;
    }
}
