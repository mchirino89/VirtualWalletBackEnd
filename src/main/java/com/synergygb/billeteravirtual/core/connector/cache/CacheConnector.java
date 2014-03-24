package com.synergygb.billeteravirtual.core.connector.cache;

import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import java.util.List;

/**
 * Connector that transfers entites between a cache component to a logic
 * component.
 * 
 * The function of this connector is to manage the entities in a cache. It
 * finds, saves, and removes objects from the storage component.
 * 
 * @author SYNERGY Global Business Venezuela, C.A.
 */
public interface CacheConnector {
    
    /**
     * Saves the entity in cache, given the key that identify it in cache and
     * and the object that represents the entity.
     * 
     * @param   key identifier of the entity in cache
     * @param   obj object that represents the entity
     *
     * @throws  ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    void save(String prefix ,Object obj, String cookie) throws CouchbaseOperationException;
    
    /**
     * Saves a set of entites in cache, given a set of keys that identify every
     * one of them, and the objects that represent every entitiy.
     * The value of <i>i</i>-index element of the set of keys identifies the 
     * entity represented by the <i>i</i>-index element of the set of objects.
     * 
     * @param   keys    set of identifiers of the entities          
     * @param   objs    objects that represent the entities
     * 
     * @throws  ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    void save(String prefix, List<Object> entities, String cookie) throws CouchbaseOperationException;
    
    /**
     * Gets the entity from cache, given the key that identifies it in cache.
     * 
     * @param   key identifier of the entit in cache
     * 
     * @return  the object that represents the entity
     * 
     * @throws ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    Object get(String prefix, String cookie) throws CouchbaseOperationException;
    /*
     * Gets the entity from cache and restart the counter of living time in
     * cache, given the key that identifies it in cache.
     * 
     * @param   key identifier of the entit in cache
     * 
     * @return  the object that represents the entity
     * 
     * @throws ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    Object getAndTouch(String prefix, String cookie) throws CouchbaseOperationException;/*
     * Increments the counter in
     * cache, given the key that identifies it in cache.
     * 
     * @param   key identifier of the entit in cache
     * 
     * @return  the object that represents the entity
     * 
     * @throws ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    Object incr(String prefix) throws CouchbaseOperationException;
    
    /**
     * Removes the entity from cache, given the key that identifies it in cache.
     * 
     * @param   key identifier of the entity in cache
     * 
     * @throws ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    void remove(String prefix, String cookie) throws CouchbaseOperationException;
    
    /**
     * Removes the entities from cache, given the set of keys that identify
     * every one of them.
     * 
     * @param   keys    set of identifiers of the entities
     * 
     * @throws ConnectorException  If a exception ocurred when communicating
     *                              with the cache client.
     */
    void remove(String prefix, List<String> keys, String cookie) throws CouchbaseOperationException;
}
