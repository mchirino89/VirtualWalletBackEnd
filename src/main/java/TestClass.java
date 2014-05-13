/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.synergygb.billeteravirtual.notificacion.models.*;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
    private static final String KEY = "instruments-";
    private String cedulas[] = {"18839634", "19000000", "15000000"};
    private String prov[] = {"Master Card", "American Express", "Visa", "Maestro", "Dinner Club"};
    private String id[] = {"djvmogsm","n7lhs4qy","sn7250lm","aiskc3zl","7p4o7d10","6g2k9xlm","9h7zshs5","shn58544","0y53r3c7"};
    private String ref[] = {"vdhbi073que380zzqvp36ikazxwb1t","m0ai9s5c3kdzsxp0ns9mz4ea4ruelh","10u8dx701a7ydl7ihsodg8onnmso55","6prwg5gm41yex13mg4tl5voy5w71bc","3aypulbu4k3kasekfqnaow8hsga34m","m7ul28k3uhrig2lkkvnnj4voxmbgst","4yxgpbolje8jpv3dtbp6rert9xkgm5","ok55nk0w5n0oa366vg33pzdfb95kuf","3fc53zstqmz1xeria6cef1zrvg2iww"};
    private Random aleatorio;

    public TestClass() {
        String temporal = "1234 5678 90123 4567";
        System.out.println("Ultimos 4 digitos: "+temporal.substring(temporal.length()-4, temporal.length()));
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
            llenadoInstrumentos(client);
            //------- listar bd -----------
            listado(client,true);
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
        
        for (String apuntador : this.cedulas) {
            try {
                System.out.println("exito: " + client.delete(KEY + apuntador).get());
            } catch (InterruptedException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "interrupcion del borrado");
            } catch (ExecutionException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "peos en la ejecucion");
            }
        }
    }

    private void llenadoInstrumentos(CouchbaseClient client) {
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

    private void llenado(CouchbaseClient client) {
        for (String cedula : cedulas) {
            client.set(KEY + cedula, EXP_TIME, new User(org.apache.commons.codec.digest.DigestUtils.sha256Hex("1234")));
            //client.set(KEY + cedula, EXP_TIME, new Wallet());
        }
    }

    private void llenadoRef(CouchbaseClient client) {
        int i = 0;
        for (String apuntador : this.ref) {
            client.set(KEY + apuntador, EXP_TIME, new Card(String.valueOf(5000 + aleatorio.nextInt(5000)), prov[aleatorio.nextInt(prov.length)], id[i], "1" ));
            i++;
            //client.set(KEY + cedula, EXP_TIME, new Wallet());
        }
    }

    private void listado(CouchbaseClient client, boolean tipo) {
        if (tipo) {
            for (String cedula : cedulas) {
                System.out.println(KEY + cedula + " - " + client.get(KEY + cedula));
            }
        }
        else
            for (String referencia : this.ref) {
                System.out.println(KEY + referencia + " - " + client.get(KEY + referencia));
            }
    }

    private void creacionDeVista(CouchbaseClient client) {
        //------------ Create view ----------------------
        DesignDocument designDoc = new DesignDocument("dev_user");
        String viewName = "userList";
        String mapFunction
                = "function (doc) {\n"
                + "  emit(doc.pass, doc);"
                + "}";

        ViewDesign viewDesign = new ViewDesign(viewName, mapFunction);
        designDoc.getViews().add(viewDesign);
        client.createDesignDoc(designDoc);
        //---------------- Query view ---------------------
        View view = client.getView("user", "userList");
        Query query = new Query();
        query.setIncludeDocs(true).setLimit(20);
        query.setStale(Stale.FALSE);
        ViewResponse result = client.query(view, query);
        for (ViewRow row : result) {
            row.getDocument(); // deal with the document/data
        }
    }

    public static void main(String[] args) {
        new TestClass(true);
    }
}
