
package com.synergygb.billeteravirtual.core.exceptions;

import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;

/**
 * BancaPlus REST Web Services
 * 
 * Class defining a couchbase operation error
 * 
 * @author Synergy-GB
 * @author Javier Fernandez
 * @version 1.0
 */
public class CouchbaseOperationException extends LayerCommunicationException {

    public static String STATUS_CODE = "COUCHBASE_OPERATION_FAILURE";
    public static String STATUS_MSG = "Coouchbase operation unexpected error ";
    
    /**
     * Creates a new instance of
     * <code>CouchbaseOperationEsception</code> without detail message.
     */
    public CouchbaseOperationException() {
    }

    /**
     * Constructs an instance of
     * <code>CouchbaseOperationEsception</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CouchbaseOperationException(String msg) {
        super(msg);
    }
}
