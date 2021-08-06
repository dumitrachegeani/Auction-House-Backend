package casalicitatii;

import java.util.Collections;

public final class PersoanaJuridica extends Client {
    Companie companie;
    double capitalSocial;

    public PersoanaJuridica(int id, String nume, String adresa, double bani, Companie companie, double capitalSocial) {
        super(id, nume, adresa, bani);
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }
}
