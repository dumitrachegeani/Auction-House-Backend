package casalicitatii.produse;

import java.io.Serializable;

//FACTORY DESIGN PATTERN
public sealed class Produs implements Serializable permits Tablou, Mobila, Bijuterie{
    private int id;
    private String nume;
    private double pretVanzare;
    private double pretMinim;
    private int an;


    public Produs(int id, String nume, double pretVanzare, double pretMinim, int an) {
        this.id = id;
        this.nume = nume;
        this.pretVanzare = pretVanzare;
        this.pretMinim = pretMinim;
        this.an = an;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public double getPretVanzare() {
        return pretVanzare;
    }

    public double getPretMinim() {
        return pretMinim;
    }

    public int getAn() {
        return an;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                '}';
    }
}
