package com.synergygb.billeteravirtual.core.connector.cache.models;

import com.synergygb.asegura.core.config.AppXMLConfiguration;

/**
 * BancaPlus REST Web Services
 *
 * Class defining couchbase key prefixes
 *
 * @author Synergy-GB
 * @author Javier Fernandez
 * @version 1.0
 */
public class CouchbaseKeyPrefix {

//    /**
//     * Cache key associated to a user session
//     */
    public static String USER_SESSION_PREFIX = AppXMLConfiguration.COUCHBASE_PREFIX + "session-";
//    /**
//     * Cache key associated to a user session
//     */
//    public static String ACTIVE_USER_SESSION = IntegrationProperties.COUCHBASE_PREFIX + "active-session-";
//    /**
//     * user session language
//     */
//    public static String USER_SESSION_LANG = IntegrationProperties.COUCHBASE_PREFIX + "session-lang-";
//    /**
//     * Cache key associated to a user beneficiary directory
//     */
//    public static String USER_DIRECTORY = "-directory";
//    /**
//     * Cache key associated to a user global statement
//     */
    public static String USER_GLOBAL_STATEMENT = "-gs";
//    /**
//     * Cache key associated to a user transactional data
//     */
//    public static String USER_TRANS_DATA = "-td";
//    /**
//     * Cache key associated to a user global statement
//     */
//    public static String USER_INSTRUMENT_DETAILS = "-details-";
//    /**
//     * Cache key associated to a user instrument
//     */
//    public static String USER_INSTRUMENT = IntegrationProperties.COUCHBASE_PREFIX + "instrument-";
//    /**
//     * Cache key for external internal match hash map
//     */
//    public static String EXTERNAL_INTERNAL_MATCH = IntegrationProperties.COUCHBASE_PREFIX + "instrument-extint-";
//    /**
//     * Cache key for internal external match hash map
//     */
//    public static String INTERNAL_EXTERNAL_MATCH = IntegrationProperties.COUCHBASE_PREFIX + "instrument-intext-";
//    /**
//     * Cache key for internal external match hash map
//     */
//    public static String BENEFICIARY_MATCH = IntegrationProperties.COUCHBASE_PREFIX + "instrument-benef-";
//    /**
//     * Cache key for terms agreement
//     */
    public static String TERMS_AGREEMENT = AppXMLConfiguration.COUCHBASE_PREFIX + "terms-";
//    /**
//     * Cache key user session global count
//     */
    public static String USER_SESSION_TOTAL_COUNT = AppXMLConfiguration.COUCHBASE_PREFIX + "session-stat-count";
//    /**
//     * Cache key for user specific session count
//     */
//    public static String USER_SESSION_COUNT = "-session-stat-count";
//    /**
//     * Cache key prefix for user session index associated wit a given user. The
//     * variable index is given by USER_SESSION_STAT_COUNT and refers to the
//     * global transaction index
//     */
//    public static String USER_SESSION_INDEXER = "-sessionIndex-";
//    /**
//     * Cache key user session
//     */
//    public static String USER_SESSION_STAT = IntegrationProperties.COUCHBASE_PREFIX + "session-stat-";
//    /**
//     * Cache key user session stat list counter index for the given dat
//     */
//    public static String USER_SESSION_STAT_GLOBAL_LIST = IntegrationProperties.COUCHBASE_PREFIX + "session-list-";
//    /**
//     * Cache key for total executed transaction count
//     */
    public static String TOTAL_TRANSACTION_COUNT = AppXMLConfiguration.COUCHBASE_PREFIX + "transactionCount";
//    /**
//     * Cache key prefix for user associated information
//     */
    public static String USER_PREFIX = AppXMLConfiguration.COUCHBASE_PREFIX + "user-";
//    /**
//     * Cache key prefix for transaction count index associated with a given user
//     */
//    public static String USER_TRANSACTION_COUNT = "-transactionCount";
//    /**
//     * Cache key prefix for user transaction index associated wit a given user.
//     * The variable index is given by USER_TRANSACTION_COUNT and refers to the
//     * global transaction index
//     */
//    public static String USER_TRANSACTION_INDEXER = "-transactionIndex-";
//    /**
//     * Cache key prefix for user transaction statistics
//     */
    public static String USER_TRANSACTION_STAT = AppXMLConfiguration.COUCHBASE_PREFIX + "transaction-";
//    /**
//     * Cache key prefix for user contact info hash
//     */
//    public static String USER_CONTACT_INFO_LIST = "-contactList";
//    /**
//     * Cache key prefix for user session stat list
//     */
//    public static String USER_SESSION_STAT_LIST = "-sessionStatList";
//    /**
//     * Cache key prefix for user session statistics id
//     */
    public static String USER_SESSION_STAT_ID = AppXMLConfiguration.COUCHBASE_PREFIX + "session-stat-id-";
//    /**
//     * Cache key prefix for transaction count by transaction by month
//     */
//    public static String TRANSACTION_COUNT_BY_MONTH = IntegrationProperties.COUCHBASE_PREFIX + "transaction-count-by-month-";
//    /**
//     * Cache key prefix for transaction count by transaction by month
//     */
//    public static String USER_COUNT_BY_MONTH = IntegrationProperties.COUCHBASE_PREFIX + "user-count-by-month-";
//    /**
//     * Cache key prefix for remote service average time counter
//     */
    public static String SERVICE_AVERAGE_TIME = AppXMLConfiguration.COUCHBASE_PREFIX + "service-avg-time-";
    public static String SERVICE_AVERAGE_TIME_COUNTER = AppXMLConfiguration.COUCHBASE_PREFIX + "service-avg-time-counter";
    public static String REMOTE_LOGIN_SERVICE = AppXMLConfiguration.COUCHBASE_PREFIX + "login";
//    public static String REMOTE_STATEMENT_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "statement";
//    public static String REMOTE_ACCOUNT_DETAILS_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "account-details";
//    public static String REMOTE_LOAN_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "loan-details";
//    public static String REMOTE_CC_DETAILS_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "creditcard-details";
//    public static String REMOTE_TRANSDATA_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "trans-data";
    public static String LOCAL_LOGIN_SERVICE = AppXMLConfiguration.COUCHBASE_PREFIX + "local-login";
//    public static String LOCAL_STATEMENT_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "local-statement";
//    public static String LOCAL_ACCOUNT_DETAILS_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "local-account-details";
//    public static String LOCAL_CC_DETAILS_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "local-cc-details";
//    public static String LOCAL_LOAN_DETAILS_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "local-loan-details";
//    public static String LOCAL_TRANSDATA_SERVICE = IntegrationProperties.COUCHBASE_PREFIX + "local-trans-data";
    public static String ENCRYPTION_AVG_TIME = AppXMLConfiguration.COUCHBASE_PREFIX + "encryption-avg-time";
//    public static String DECRYPTION_AVG_TIME = IntegrationProperties.COUCHBASE_PREFIX + "decryption-avg-time";
    public static String ENCRYPTION_AVG_TIME_COUNTER = AppXMLConfiguration.COUCHBASE_PREFIX + "encryption-avg-time-count";
//    public static String DECRYPTION_AVG_TIME_COUNTER = IntegrationProperties.COUCHBASE_PREFIX + "decryption-avg-time-count";

    public static String BRANCHES_VERSION = AppXMLConfiguration.COUCHBASE_PREFIX + "branches-vesion";
    public static String BRANCHES_INFO = AppXMLConfiguration.COUCHBASE_PREFIX + "branches-info";

}
