package casalicitatii.produse;

/**
 * Design Pattern Factory
 * Creeaza obiecte de tip produs
 * Aici am folosit switch expressions
 */
public class ProdusFactory {
    
    public static Produs getProdus (String tipProdus, int id, String nume,
                                    double pretVanzare, double pretMinim, int an, String arg1, String arg2) {
        return switch (tipProdus) {
            case "bijuterie" -> new Bijuterie(id, nume, pretVanzare, pretMinim, an, arg1, arg2);
            case "mobila" ->  new Mobila(id, nume, pretVanzare, pretMinim, an, arg1, arg2);
            case "tablou" ->  new Tablou(id, nume, pretVanzare, pretMinim, an, arg1, arg2);
            default -> throw new IllegalStateException("Unexpected value: " + tipProdus);
        };

    }
}
