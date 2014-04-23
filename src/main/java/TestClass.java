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
    private static final String KEY = "card-";
    private final String cedulas[] = {"18839634", "19000000", "15000000"};
    private final String prov[] = {"Master Card", "American Express", "Visa", "Maestro", "Dinner Club"};
    private final String ref[] = {"egw28hol0uzoa3dqsfd1nxc9gylvyr","4l3jnnb4m4tso0ugg7y49ecd09956f","5e8f05cxujszzvlxa0egvueahtevp0","vabcdd2egf0jn36cjj1ima5w9gkpt2","dh9olmopd2fpfzo2uuiwbo1vsnt2bo","098sz29z75bh2gg46ddi7v8eewhhd9","1ppuowzk97jv2fm7rh20g9zptqwzkx","ii827ohur9lwu2u5q9n0jyrvzq9rqs","vz5lr484x79kuwx8gbvbv0gwxz20w3"};
    private StringBuilder sb;
    private Random aleatorio;

    public TestClass() {
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
            llenadoRef(client);
            //------- listar bd -----------
            listado(client,false);
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
        for (String apuntador : this.ref) {
            try {
                System.out.println("exito: " + client.delete(KEY + apuntador).get());
            } catch (InterruptedException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "interrupcion del borrado");
            } catch (ExecutionException ex) {
                Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, "peos en la ejecucion");
            }
        }
    }

    private void insertaRegistro(CouchbaseClient client, String cedula) {
        client.set(KEY + cedula, EXP_TIME, new User("1234"));
    }

    private void llenadoInstrumentos(CouchbaseClient client) {
        for (String cedula : cedulas) {
            Instrument[] tarj = new Instrument[1 + aleatorio.nextInt(3)];
            for (int i = 0; i < tarj.length; i++) {
                tarj[i] = new Instrument(generaCadena(8), generaCadena(30));
            }
            client.set(KEY + cedula, EXP_TIME, new Instruments(tarj));
        }
    }

    private String generaCadena(int longitud) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        sb = new StringBuilder();
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
        for (String apuntador : this.ref) {
            client.set(KEY + apuntador, EXP_TIME, new Card(String.valueOf(5000 + aleatorio.nextInt(5000)), prov[aleatorio.nextInt(prov.length)]));
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
        new TestClass();
    }
}
