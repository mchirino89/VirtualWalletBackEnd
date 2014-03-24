
package com.synergygb.billeteravirtual.core.connector.cache.models;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.logformatter.LogUtils;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.log4j.Logger;

/**
 * Factory class that allows to create CouchbaseClient object to be managed 
 * within an object pool
 */
public class PoolableCouchbaseClientObjectFactory implements PoolableObjectFactory<CouchbaseClient> {

    private List<URI> serverList;
    private String bucketName;
    private String bucketPassword;
   
    static Logger logger = Logger.getLogger(PoolableCouchbaseClientObjectFactory.class);
    public final static String HEADER_NAME = "PoolableCouchbaseClientObjectFactory";

    /**
     * Class Constructor
     *
     * @param serverList List of URI of the couchbase servers
     * @param bucketName Couchbase bucket name
     * @param bucketPassword Couchbase bucket password
     */
    public PoolableCouchbaseClientObjectFactory(List<URI> serverList, String bucketName, String bucketPassword) {
        this.serverList = serverList;
        this.bucketName = bucketName;
        this.bucketPassword = bucketPassword;
    }

    @Override
    public CouchbaseClient makeObject() throws CouchbaseOperationException {
        // creates a new instance of the object to be inserted on the pool
        try {
            //server = new URI(IntegrationProperties.COUCHBASE_SERVER);
            
            CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
            cfb.setOpTimeout(10000);//10 seconds for operation timeout
            cfb.setTimeoutExceptionThreshold(1500);
            
            CouchbaseClient client = new CouchbaseClient(cfb.buildCouchbaseConnection(this.serverList, this.bucketName, this.bucketPassword));
            
            //CouchbaseClient client = new CouchbaseClient(this.serverList, this.bucketName, this.bucketPassword);
            if(client == null){
                throw new CouchbaseOperationException("El cliente no pudo ser creado");
            }
            return client;            
        }   
        catch (IOException ex) {
            logger.error(LogUtils.getLogMessage(WSLog.buildShortLog(HEADER_NAME, WSLogOrigin.REMOTE_ARCHITECTURE, String.valueOf(Thread.currentThread().getId()), ErrorID.DATABASE_OPERATION.getId(), "Excepcion de IO creando conexion con el cache: conexion hacia"+this.serverList.get(0)+" "+this.bucketName+" "+this.bucketPassword)),ex);
            throw new CouchbaseOperationException("Excepción de IO "+ex.getMessage());
        }   
        catch(Exception ex){
            logger.error(LogUtils.getLogMessage(WSLog.buildShortLog(HEADER_NAME, WSLogOrigin.REMOTE_ARCHITECTURE, String.valueOf(Thread.currentThread().getId()), ErrorID.DATABASE_OPERATION.getId() , "Excepcion inesperada creando conexion con el cache"+this.serverList.get(0)+" "+this.bucketName+" "+this.bucketPassword)),ex);
            throw new CouchbaseOperationException("Excepción creando objeto conexion para el pool "+ex.getMessage());   
        }
    }
    
    @Override
    public void destroyObject(CouchbaseClient obj) throws Exception {
        // called when the instance is dropped from the pool
        try {
            obj.shutdown();
        } catch (Exception e) {
            logger.error(LogUtils.getLogMessage(WSLog.buildShortLog(HEADER_NAME, WSLogOrigin.REMOTE_ARCHITECTURE, String.valueOf(Thread.currentThread().getId()), ErrorID.DATABASE_OPERATION.getId(), "No se pudo destruir la conexion a couchbase correctamente")),e);
        }
    }

    /*
    @Override
    public boolean validateObject(CouchbaseClient obj) {
        //validates activated instances before being return to pool 
        if(obj==null){
            return false;
        }
        return true;
    }

    @Override
    public void activateObject(CouchbaseClient obj) throws Exception {
        //called on every passivated instance before borrowing
    }

    @Override
    public void passivateObject(CouchbaseClient obj) throws Exception {
       //called on every object after returning to object pool
    }
*/

    @Override
    public boolean validateObject(CouchbaseClient obj) {
       // return true;
        if(obj==null){
            return false;
        }
        return true;
    }

    @Override
    public void activateObject(CouchbaseClient obj) throws Exception {
        
    }

    @Override
    public void passivateObject(CouchbaseClient obj) throws Exception {
        
    }
}