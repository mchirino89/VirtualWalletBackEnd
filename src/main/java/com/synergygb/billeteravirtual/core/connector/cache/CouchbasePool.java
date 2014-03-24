package com.synergygb.billeteravirtual.core.connector.cache;

import com.couchbase.client.CouchbaseClient;
import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.models.CacheBucketType;
import com.synergygb.billeteravirtual.core.connector.cache.models.PoolableCouchbaseClientObjectFactory;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.logformatter.LogUtils;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Orinoquia REST Web Services
 *
 * @author Synergy-GB
 * @author Javier Fernandez
 * @version 1.0
 */
public class CouchbasePool {
    
    private static GenericObjectPool<CouchbaseClient> couchbaseClientObjectPool;
    private static GenericObjectPool<CouchbaseClient> memcachedClientObjectPool;
    private static long lastCreatedPoolTime = new Date().getTime();
    private static long MAX_POOL_ALIVE = 7200000;
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CouchbasePool.class);
    public final static String HEADER_NAME = "CouchbasePool";

       
    public static GenericObjectPool<CouchbaseClient> getPool(CacheBucketType bucket) throws CouchbaseOperationException {
        
        if (bucket == CacheBucketType.SESSION_BUCKET) {
            return getMemcachedPool();
        } else if (bucket == CacheBucketType.STATS_BUCKET) {
            return getCouchbasePool();
        }        
        //Default
        return getMemcachedPool();        
    }
    
    public static GenericObjectPool<CouchbaseClient> getPool() throws CouchbaseOperationException {
        return getMemcachedPool();        
    }
    
    private static GenericObjectPool<CouchbaseClient> getMemcachedPool() throws CouchbaseOperationException {
        
        if(memcachedClientObjectPool != null){
            return memcachedClientObjectPool;
        }
        
        URI server;
        try {
            server = new URI(AppXMLConfiguration.MODULE_COUCHBASE_URI);

        } catch (URISyntaxException ex) {
            logger.error(LogUtils.getLogMessage(WSLog.buildShortLog(HEADER_NAME, WSLogOrigin.REMOTE_ARCHITECTURE, String.valueOf(Thread.currentThread().getId()), ErrorID.DATABASE_OPERATION.getId(), ex.getMessage())), ex);
            throw new CouchbaseOperationException("URI de conexion incorrecta " +ex.getMessage());
        }
        ArrayList<URI> serverList = new ArrayList<URI>();
        serverList.add(server);

        memcachedClientObjectPool = new GenericObjectPool(new PoolableCouchbaseClientObjectFactory(serverList, AppXMLConfiguration.MODULE_CACHE_BUCKET_NAME, AppXMLConfiguration.MODULE_CACHE_BUCKET_PASSWORD), AppXMLConfiguration.MODULE_CACHE_POOL_SIZE);
        memcachedClientObjectPool.setMaxIdle(AppXMLConfiguration.MODULE_CACHE_POOL_SIZE);
        memcachedClientObjectPool.setMaxWait(AppXMLConfiguration.MODULE_COUCHBASE_MAXWAIT_MILLIS);
        memcachedClientObjectPool.setMinIdle(AppXMLConfiguration.MODULE_COUCHBASE_MIN_IDLE);
        memcachedClientObjectPool.setTimeBetweenEvictionRunsMillis(AppXMLConfiguration.MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS);
        memcachedClientObjectPool.setMinEvictableIdleTimeMillis(AppXMLConfiguration.MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME);
        
        //lastCreatedPoolTime = currentDate;
        
        return memcachedClientObjectPool;
    }

    private static GenericObjectPool<CouchbaseClient> getCouchbasePool() throws CouchbaseOperationException {
        
        if(couchbaseClientObjectPool != null){
            return couchbaseClientObjectPool;
        }
        
        URI server;
        try {
            server = new URI(AppXMLConfiguration.MODULE_COUCHBASE_URI);
        } catch (URISyntaxException ex) {
            logger.error(LogUtils.getLogMessage(WSLog.buildShortLog(HEADER_NAME, WSLogOrigin.REMOTE_ARCHITECTURE, String.valueOf(Thread.currentThread().getId()), ErrorID.DATABASE_OPERATION.getId(), ex.getMessage())), ex);
            throw new CouchbaseOperationException("URI de conexion incorrecta " +ex.getMessage());
        }
        ArrayList<URI> serverList = new ArrayList<URI>();
        serverList.add(server);

        couchbaseClientObjectPool = new GenericObjectPool(new PoolableCouchbaseClientObjectFactory(serverList, AppXMLConfiguration.MODULE_STATS_BUCKET_NAME, AppXMLConfiguration.MODULE_STATS_BUCKET_PASSWORD), AppXMLConfiguration.MODULE_STATS_POOL_SIZE);
        couchbaseClientObjectPool.setMaxIdle(AppXMLConfiguration.MODULE_STATS_POOL_SIZE);
        couchbaseClientObjectPool.setMaxWait(AppXMLConfiguration.MODULE_COUCHBASE_MAXWAIT_MILLIS);
        couchbaseClientObjectPool.setMinIdle(AppXMLConfiguration.MODULE_COUCHBASE_MIN_IDLE);
        couchbaseClientObjectPool.setTimeBetweenEvictionRunsMillis(AppXMLConfiguration.MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS);
        couchbaseClientObjectPool.setMinEvictableIdleTimeMillis(AppXMLConfiguration.MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME);
        
        //lastCreatedPoolTime = currentDate;
        
        return couchbaseClientObjectPool;
    }
}
