package casalicitatii;


public final class PersoanaFizica extends Client {
    String dataNastere;

    public PersoanaFizica(int id, String nume, String adresa, double bani, String dataNastere) {
        super(id, nume, adresa, bani);
        this.dataNastere = dataNastere;
    }
}
