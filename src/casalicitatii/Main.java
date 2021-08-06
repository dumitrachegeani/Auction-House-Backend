package casalicitatii;

import casalicitatii.csv.CsvBrokers;
import casalicitatii.csv.CsvClients;
import casalicitatii.csv.CsvProducts;
import casalicitatii.exceptii.AlreadyParticipatingException;
import casalicitatii.exceptii.BrokerListEmpty;
import casalicitatii.lucratori.Administrator;
import casalicitatii.produse.Produs;
import java.io.*;
import java.util.List;

/**
 * Citesc lista de produse
 * Citesc lista de brokeri
 * Citesc lista de clienti
 * initializez toate elementele (casaLicitatie)
 * Pornesc threadul adminului care pune produse in lista sincronizata
 */
public class Main {
    private static CasaLicitatie casaLicitatie;
    private static List<Client> clientiTest;

    public static void main(String args[]) throws Exception {

        //initializez casa de licitatii
        casaLicitatie = CasaLicitatie.getInstance();

        //citesc clientii de test
        CsvClients csvClients = new CsvClients();

        clientiTest = csvClients.readClients("clients.txt");

            //citesc produsele ---- aici avem lista sincronizata
            CsvProducts csvProducts = new CsvProducts();
            List<Produs> produsList = csvProducts.readProducts("file.txt");
            casaLicitatie.adaugaProduse(produsList);

            //citesc brokerii
            CsvBrokers csvBrokers = new CsvBrokers();
            casaLicitatie.adaugaBrokeri(csvBrokers.readBrokers("brokers.txt", casaLicitatie.getProduseDeVandut()));

            //pornesc threadul adminuli de a adauga produse continuu
            Administrator administrator = new Administrator(produsList);
            casaLicitatie.setAdmin(administrator);
            //DACA MODIFIC SA ADAUGE PRODUSUL 0 IN ADAUGA PRODUS, OBSERVAM CA INTR-ADEVAR ADAUGA SI SCOAATE PRODUSELE
            administrator.adaugaProduse();

             //TESTELE POT FI TESTATE PE RAND SAU COMBINATE/ TOATE ODATA !!!
        try {
            test1();
            test2();
            test3();
            test4();
            test5();
            test6();
            test7();
            test8();
            test9();
        }
        catch (BrokerListEmpty e) {
            System.out.println("Nu mai am brokeri");
        }
    }

    private static void test1() throws BrokerListEmpty {
        System.out.println("-------------------TEST 1 ---------------------------------");
        try{
            clientiTest.get(0).cumpara(1, casaLicitatie, 120);
            clientiTest.get(1).cumpara(1, casaLicitatie, 200);
            clientiTest.get(2).cumpara(1, casaLicitatie, 333);
            clientiTest.get(3).cumpara(1, casaLicitatie, 300);
            clientiTest.get(4).cumpara(1, casaLicitatie, 400);
            clientiTest.get(5).cumpara(1, casaLicitatie, 500);
            clientiTest.get(6).cumpara(1, casaLicitatie, 100);
            clientiTest.get(7).cumpara(1, casaLicitatie, 200);
            clientiTest.get(8).cumpara(1, casaLicitatie, 444);
            clientiTest.get(9).cumpara(1, casaLicitatie, 444);
        }
        catch (AlreadyParticipatingException | InterruptedException e) {
            System.out.println("Deja parrticipa");
        }
    }

    private static void test2() throws Exception, BrokerListEmpty {
        System.out.println("-------------------TEST 2 ---------------------------------");
        try{
            clientiTest.get(0).cumpara(2, casaLicitatie, 200);
            clientiTest.get(1).cumpara(2, casaLicitatie, 120);
            clientiTest.get(2).cumpara(2, casaLicitatie, 200);
            clientiTest.get(3).cumpara(2, casaLicitatie, 120);
            clientiTest.get(4).cumpara(2, casaLicitatie, 200);
            clientiTest.get(5).cumpara(2, casaLicitatie, 120);
            clientiTest.get(6).cumpara(2, casaLicitatie, 200);
            clientiTest.get(8).cumpara(2, casaLicitatie, 120);
            clientiTest.get(9).cumpara(2, casaLicitatie, 200);
            clientiTest.get(10).cumpara(2, casaLicitatie, 120);
        }catch (AlreadyParticipatingException e) {
            System.out.println("Deja participa la licitatie");
        }
    }

    //LA ACEST TEST NU VA PORNI LICITATIA ( NU SUNT DESTUI PARTICIPANTI )
    private static void test3() throws BrokerListEmpty {
        System.out.println("-------------------TEST 3 ---------------------------------");
        try {
            clientiTest.get(10).cumpara(12, casaLicitatie, 120);
            clientiTest.get(11).cumpara(12, casaLicitatie, 120);
            clientiTest.get(12).cumpara(12, casaLicitatie, 120);
            clientiTest.get(13).cumpara(12, casaLicitatie, 120);
            clientiTest.get(14).cumpara(12, casaLicitatie, 120);
            clientiTest.get(15).cumpara(11, casaLicitatie, 120);
            clientiTest.get(16).cumpara(12, casaLicitatie, 120);
            clientiTest.get(17).cumpara(12, casaLicitatie, 120);
        } catch (AlreadyParticipatingException | InterruptedException e) {
            System.out.println("Deja participa");
        }

        //aici nu vor fi destui pentru a incepe licitatie
    }

    //AICI INCERCAM SA CUMPAR DE 2 ORI ACELASI LUCRU
    private static void test4() throws BrokerListEmpty {
        System.out.println("-------------------TEST 4 ---------------------------------");
        try{
            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

            clientiTest.get(0).cumpara(5, casaLicitatie, 120);
            clientiTest.get(1).cumpara(3, casaLicitatie, 120);
            clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            clientiTest.get(3).cumpara(3, casaLicitatie, 120);
            clientiTest.get(4).cumpara(0, casaLicitatie, 120);

        }
        catch (AlreadyParticipatingException | InterruptedException e) {
        System.out.println("Deja participa");
    }
    }


    private static void test5() throws BrokerListEmpty {
        System.out.println("-------------------TEST 5 ---------------------------------");
        try {
            clientiTest.get(0).cumpara(21, casaLicitatie, 120);
            clientiTest.get(1).cumpara(21, casaLicitatie, 120);
            clientiTest.get(2).cumpara(21, casaLicitatie, 120);
            clientiTest.get(3).cumpara(21, casaLicitatie, 120);
            clientiTest.get(4).cumpara(21, casaLicitatie, 120);
            clientiTest.get(5).cumpara(21, casaLicitatie, 120);
            clientiTest.get(6).cumpara(21, casaLicitatie, 120);
            clientiTest.get(7).cumpara(21, casaLicitatie, 120);
            clientiTest.get(8).cumpara(21, casaLicitatie, 120);
            clientiTest.get(9).cumpara(21, casaLicitatie, 120);
            clientiTest.get(10).cumpara(21, casaLicitatie, 120);

            clientiTest.get(0).cumpara(22, casaLicitatie, 120);
            clientiTest.get(1).cumpara(22, casaLicitatie, 120);
            clientiTest.get(2).cumpara(22, casaLicitatie, 120);
            clientiTest.get(3).cumpara(22, casaLicitatie, 120);
            clientiTest.get(4).cumpara(22, casaLicitatie, 120);
            clientiTest.get(5).cumpara(22, casaLicitatie, 120);
            clientiTest.get(6).cumpara(22, casaLicitatie, 120);
            clientiTest.get(7).cumpara(22, casaLicitatie, 120);
            clientiTest.get(8).cumpara(22, casaLicitatie, 120);
            clientiTest.get(9).cumpara(22, casaLicitatie, 120);
            clientiTest.get(10).cumpara(22, casaLicitatie, 120);
        }catch (AlreadyParticipatingException | InterruptedException e) {
            System.out.println("Deja participa");
        }
    }


    //incep 4 licitatii pe produsele 6 7 8 si 9
    private static void test6() throws BrokerListEmpty {
        System.out.println("-------------------TEST 6 ---------------------------------");
        try {
            for (int i = 0 ;i < 10; i++) {
                clientiTest.get(i).cumpara(6, casaLicitatie, 120);
                clientiTest.get(i).cumpara(7, casaLicitatie, 200);
                clientiTest.get(i).cumpara(8, casaLicitatie, 300);
                clientiTest.get(i).cumpara(9, casaLicitatie, 400);

            }
        }
        catch (AlreadyParticipatingException | InterruptedException e) {
            System.out.println("Deja participa");
        }
    }


    //incep 7 lictiatii pe produsele 23...30
    private static void test7() throws BrokerListEmpty {
        System.out.println("-------------------TEST 7 ---------------------------------");
        try {
            for (int i = 0 ;i < 10; i++) {
                clientiTest.get(i).cumpara(23, casaLicitatie, 120);
                clientiTest.get(i).cumpara(24, casaLicitatie, 200);
                clientiTest.get(i).cumpara(25, casaLicitatie, 300);
                clientiTest.get(i).cumpara(26, casaLicitatie, 400);
                clientiTest.get(i).cumpara(27, casaLicitatie, 120);
                clientiTest.get(i).cumpara(28, casaLicitatie, 200);
                clientiTest.get(i).cumpara(29, casaLicitatie, 300);
                clientiTest.get(i).cumpara(30, casaLicitatie, 400);

            }
        }
        catch (AlreadyParticipatingException | InterruptedException e) {
            System.out.println("Deja participa");
        }
    }


    //AICI NU MAI POT FI GASITE ACELE PRODUSE CA LE-AM CUMPARAT MAI SUS (TEST 7)
    private static void test8() throws BrokerListEmpty {
        System.out.println("-------------------TEST 8 ---------------------------------");
        try {
            for (int i = 10; i < 20; i++) {
                clientiTest.get(i).cumpara(23, casaLicitatie, 120);
                clientiTest.get(i).cumpara(24, casaLicitatie, 200);
                clientiTest.get(i).cumpara(25, casaLicitatie, 300);
                clientiTest.get(i).cumpara(26, casaLicitatie, 400);
                clientiTest.get(i).cumpara(27, casaLicitatie, 120);
                clientiTest.get(i).cumpara(28, casaLicitatie, 200);
                clientiTest.get(i).cumpara(29, casaLicitatie, 300);
                clientiTest.get(i).cumpara(30, casaLicitatie, 400);
            }
            } catch(AlreadyParticipatingException | InterruptedException e){
                System.out.println("Deja participa");
            }
    }


        private static void test9 () throws BrokerListEmpty {
            System.out.println("-------------------TEST 9 ---------------------------------");
            try {
                clientiTest.get(0).cumpara(0, casaLicitatie, 120);
                clientiTest.get(1).cumpara(0, casaLicitatie, 120);
                clientiTest.get(2).cumpara(0, casaLicitatie, 120);
                clientiTest.get(2).cumpara(0, casaLicitatie, 120);
                clientiTest.get(2).cumpara(0, casaLicitatie, 120);
                clientiTest.get(2).cumpara(0, casaLicitatie, 120);
            } catch (AlreadyParticipatingException | InterruptedException e) {
                System.out.println("Deja participa");
            }
        }

        //LA TESTUL ASTA RAMAN FARA BROKERI / rulat singur este foarte frumos
        public static void test10 () throws BrokerListEmpty {
            System.out.println("-------------------TEST 10 ---------------------------------");
            try {
                for (int i = 10; i < 20; i++) {
                    clientiTest.get(i).cumpara(30, casaLicitatie, 120);
                    clientiTest.get(i).cumpara(31, casaLicitatie, 200);
                    clientiTest.get(i).cumpara(32, casaLicitatie, 300);
                    clientiTest.get(i).cumpara(33, casaLicitatie, 400);
                    clientiTest.get(i).cumpara(34, casaLicitatie, 120);
                    clientiTest.get(i).cumpara(35, casaLicitatie, 200);
                    clientiTest.get(i).cumpara(36, casaLicitatie, 300);
                    clientiTest.get(i).cumpara(37, casaLicitatie, 400);
                }
            } catch (AlreadyParticipatingException | InterruptedException e) {
                System.out.println("Deja participa");
            }
        }

        //in caz ca vreau sa serializez ( SAU CINEVA CARE VA CONTINUA PROIECTUL )
    public static void loadCasa(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename));
        casaLicitatie = (CasaLicitatie) objectInputStream.readObject();
        objectInputStream.close();
    }

    public static void saveCasa(String filename) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
        objectOutputStream.writeObject(casaLicitatie);
        objectOutputStream.close();
    }
}
