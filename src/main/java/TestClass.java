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
    public static void main(String[] args){
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
            client = new CouchbaseClient(cfb.buildCouchbaseConnection(serverList, "default", ""));
        } catch (IOException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object obj = client.get("test");
    }
}
