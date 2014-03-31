/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauriciochirino
 */
public class TestClass {

    public static final int EXP_TIME = 10;
    public static final String KEY = "user3";
    public static final String VALUE = "18847834";

    public static void main(String[] args) {
        CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
        cfb.setOpTimeout(10000);//10 seconds for operation timeout
        cfb.setTimeoutExceptionThreshold(1500);

        URI server = null;
        try {
            server = new URI("http://172.16.16.129:8091/pools");
        } catch (URISyntaxException ex) {

        }
        List<URI> serverList = new ArrayList<URI>();
        serverList.add(server);
        CouchbaseClient client = null;
        try {
            client = new CouchbaseClient(serverList, "billetera", "");
            
        } catch (IOException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, "No se pudo operar sobre el objeto a bd", ex);
        }
        
        System.out.println("\n" + client.get(KEY).toString() + "\n");
        
        //client.set(KEY, EXP_TIME, new user("18839634"));
        // Store a Document

        //client.set("my-first-document", "Hello Couchbase!").get();
        // Retreive the Document and print it
    }
}
