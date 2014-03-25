/**
 * 
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL 
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO.
 * SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE LICENCIAMIENTO DEL CÓDIGO 
 * FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO
 * UTILICE EL CÓDIGO FUENTE.
 * 
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”) 
 * se realiza entre Synergy Global Business, C.A. (en adelante, “Synergy-GB”) 
 * y el Licenciatario (en adelante, “el Cliente”).
 * 
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en 
 * EL ACUERDO en cualquier momento y sin previo aviso. 
 * EL ACUERDO está descrito y accesible a través de la dirección siguiente: 
 * http://www.synergy-gb.com/licenciamiento.pdf
 * 
 */
package com.synergygb.billeteravirtual.core.connector.cache;

import com.couchbase.client.CouchbaseClient;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import java.util.List;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Billetera Virtual+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class GenericMemcachedConnector<T extends CouchbaseClient> implements CacheConnector {
    
    private int secsInCache;
    private GenericObjectPool<T> cacheClientObjectPool;
    
    public GenericMemcachedConnector(int secsInCache, GenericObjectPool<T> cacheClientObjectPool) {
        this.secsInCache = secsInCache;
        this.cacheClientObjectPool = cacheClientObjectPool;
    }

    @Override
    public void save(String prefix, Object obj, String cookie) throws CouchbaseOperationException {
        String key = prefix + cookie;
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            int secs = secsInCache;
            cacheClient.set(key, secs, obj);
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
        }
            }
    }

    @Override
    public void save(String prefix, List<Object> entities, String cookie) throws CouchbaseOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object get(String prefix, String cookie) throws CouchbaseOperationException {
        String key = prefix + cookie;
        
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            return (Object) cacheClient.get(key);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
            }
        } 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAndTouch(String prefix, String cookie) throws CouchbaseOperationException {
        String key = prefix + cookie;
        
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            Object obj = cacheClient.get(key);
            if (obj == null) {
                return null;
            }
            int secs = secsInCache;
            cacheClient.touch(key, secs);
            return (Object) obj;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
            }
        } 
    }

    @Override
    public void remove(String prefix, String cookie) throws CouchbaseOperationException {
        String key = prefix + cookie;
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            cacheClient.delete(key);
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
            }
        }
    }

    @Override
    public void remove(String prefix, List<String> keys, String cookie) throws CouchbaseOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object incr(String key) throws CouchbaseOperationException {
        
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            Object obj = cacheClient.get(key);
            if (obj == null) {
                return null;
            }
            int secs = secsInCache;
            cacheClient.incr(key, 1,1,0);
            return (Object) obj;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
            }
        } 
    }
    
    public void saveExpiration(String prefix, Object obj, String cookie, int secs) throws CouchbaseOperationException {
        String key = prefix + cookie;
        T cacheClient = null;
        try {
            cacheClient = cacheClientObjectPool.borrowObject();
            cacheClient.set(key, secs, obj);
        } catch (Exception ex) {
            throw new CouchbaseOperationException(ex.getMessage());
        } finally {
            try {
                cacheClientObjectPool.returnObject(cacheClient);
            } catch (Exception ex) {
                throw new CouchbaseOperationException(ex.getMessage());
            }
        }
    }
}
