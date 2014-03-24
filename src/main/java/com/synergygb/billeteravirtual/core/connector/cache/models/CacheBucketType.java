/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synergygb.billeteravirtual.core.connector.cache.models;

/**
 * BancaPlus REST Web Services
 * 
 * Enum type defining bucket addresses for cache databases
 * 
 * @author Synergy-GB
 * @author Javier Fernandez
 * @version 1.0
 */
public enum CacheBucketType {
    /**
     * Default Cache Bucket
     */
    DEFAULT_BUCKET,
    /**
     * Statistics Cache Bucket
     */
    STATS_BUCKET,
    /**
     * 
     */
    SESSION_BUCKET;
    
    private CacheBucketType(){}
}
