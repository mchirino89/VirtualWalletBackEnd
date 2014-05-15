/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.synergygb.billeteravirtual.notificacion.models.*;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauriciochirino
 */
public class TestClass {

    private static final int EXP_TIME = 0;
    private static final String KEY = "transactions-";
    private String cedulas[] = {"18839634", "19000000", "15000000"};
    private String prov[] = {"Master Card", "American Express", "Visa", "Maestro", "Dinner Club"};
    private String id[] = {"n7lhs4qy", "aiskc3zl",      "7p4o7d10"      , "6g2k9xlm", "9h7zshs5",       "shn58544"      };
    private String ref[] = {"m0ai9s5c3kdzsxp0ns9mz4ea4ruelh", "6prwg5gm41yex13mg4tl5voy5w71bc", "3aypulbu4k3kasekfqnaow8hsga34m", "m7ul28k3uhrig2lkkvnnj4voxmbgst", "4yxgpbolje8jpv3dtbp6rert9xkgm5", "ok55nk0w5n0oa366vg33pzdfb95kuf"};
    private float amounts[] = {62800,765,2260,11280,480,3500,260000};
    private String descriptions[] = {"PS4 todo video. CC san ignacio","medicamentos farmatodo. la urbuna","consumo restaurante cinex el hatillo","respuestos. agencia chevrolet","panaderia las delicias. cc las americas","entradas VIP teatrex","boletos aeros ccs-madrid"};
    
    private Random aleatorio;

    public TestClass() {
        
    }

    public TestClass(boolean sobrecargado) {
        CouchbaseClient client = null;
        aleatorio = new Random();
        System.setProperty("viewmode", "development");
        CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
        cfb.setOpTimeout(10000);//10 seconds for operation timeout
        cfb.setTimeoutExceptionThreshold(1500);
        try {
            URI server = new URI("http://172.16.16.129:8091/pools");
            List<URI> serverList = new ArrayList<URI>();
            serverList.add(server);
            client = new CouchbaseClient(serverList, "billetera", "");
            //------- Llenando la bd -----
            //borradoInstrumentos(client);
            llenado(client, 4);
            //------- listar bd -----------
            //listado(client, 3);
            //------- Creacion de vistas -------
            //creacionDeVista(client);
            client.shutdown();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, "Problemas con el pool de direcciones", ex);
        } catch (IOException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, "Problemas con el cableado de la bd", ex);
        }
    }

    private void borradoInstrumentos(CouchbaseClient client) {

        for (String apuntador : this.id) {
            try {
                System.out.println("exito: " + client.delete(KEY + apuntador).get());
            } catch (InterruptedException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "interrupcion del borrado");
            } catch (ExecutionException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "peos en la ejecucion");
            }
        }
    }

    private void llenado(CouchbaseClient client, int type) {
        switch (type) {
            case 1://usuarios
                for (String cedula : cedulas) {
                    client.set(KEY + cedula, EXP_TIME, new User(org.apache.commons.codec.digest.DigestUtils.sha256Hex("1234")));
                    //client.set(KEY + cedula, EXP_TIME, new Wallet());
                }
                break;
            case 2://instrumentos
                /*
                 int k = 0;
                 for (String cedula : cedulas) {
                 ArrayList<Instrument> tarj = new ArrayList<Instrument>();
                 for (int i = 0; i < 3; i++, k++) {
                 tarj.add(new Instrument(id[k], ref[k]));
                 }
                 client.set(KEY + cedula, EXP_TIME, new Instruments(tarj));
                 }*/
                ArrayList<Instrument> tarj = new ArrayList<Instrument>();
                for (int i = 0, k = 6; i < 3; i++, k++) {
                    tarj.add(new Instrument(id[k], ref[k]));
                }
                client.set("instruments-15000000", EXP_TIME, new Instruments(tarj));
                break;
            case 3://ref
                int i = 0;
                for (String apuntador : this.ref) {
                    client.set(KEY + apuntador, EXP_TIME, new Card(String.valueOf(5000 + aleatorio.nextInt(5000)), prov[aleatorio.nextInt(prov.length)], id[i], "1"));
                    i++;
                    //client.set(KEY + cedula, EXP_TIME, new Wallet());
                }
                break;
            case 4://historial
                ArrayList<Transaction> movimientos = new ArrayList<Transaction>();
                for (int index = 4; index < 7; index++) {
                    movimientos.add(new Transaction(generateDate(), descriptions[index], String.format("%.2f", amounts[index])));
                }
                //client.set(KEY + id[5], EXP_TIME, new Transactions(movimientos));
                System.out.println(KEY + id[0]+": "+client.get(KEY + id[0]));
                break;
        }
    }

    private void listado(CouchbaseClient client, int tipo) {
        switch (tipo) {
            case 1:
                for (String cedula : cedulas) {
                    System.out.println(KEY + cedula + " - " + client.get(KEY + cedula));
                }
                break;
            case 2:
                for (String referencia : this.ref) {
                    System.out.println(KEY + referencia + " - " + client.get(KEY + referencia));
                }
                break;
            case 3:
                for (String referencia : this.id) {
                    System.out.println(KEY + referencia + " - " + client.get(KEY + referencia));
                }
                break;
        }
    }

    public String generateDate() {
        Random aleatorio = new Random();
        int year = aleatorio.nextInt(5) + 2010;// Here you can set Range of years you need
        int month = year != 2014 ? 1 + new Random().nextInt(11) : 1 + new Random().nextInt(4);
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = aleatorio.nextInt(gc.getActualMaximum(gc.DAY_OF_MONTH)) + 1;
        return (day < 10 ? "0" + day : day) + "-" + (month < 10 ? "0" + month : month) + "-" + year;
    }

    private String generaCadena(int longitud) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new TestClass();

    }
}
