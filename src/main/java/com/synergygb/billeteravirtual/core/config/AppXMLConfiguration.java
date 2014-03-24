/**
 *
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE
 * LICENCIAMIENTO. SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE
 * LICENCIAMIENTO DEL CÓDIGO FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE,
 * TRANSFIERA, O EN CUALQUIER CASO UTILICE EL CÓDIGO FUENTE.
 *
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”)
 * se realiza entre Synergy Global Business, C.A. (en adelante, “Synergy-GB”) y
 * el Licenciatario (en adelante, “el Cliente”).
 *
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en EL
 * ACUERDO en cualquier momento y sin previo aviso. EL ACUERDO está descrito y
 * accesible a través de la dirección siguiente:
 * http://www.synergy-gb.com/licenciamiento.pdf
 *
 */
package com.synergygb.billeteravirtual.core.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Asegura+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class AppXMLConfiguration {

    private static final String CONFIG_FILE_NAME = "META-INF/config.xml";
    public static final String COUCHBASE_PREFIX = "asegura-";
    public static String TERMS_VERSION = "Y";
    public static final XMLConfiguration CONFIG;
    // Config XML file node label variables
    private static final String MODULE_COUCHBASE_URI_LABEL = "Couchbase.moduleCouchbaseURL";
    private static final String MODULE_COUCHBASE_MAXWAIT_MILLIS_LABEL = "Couchbase.maxWaitMillis";
    private static final String MODULE_COUCHBASE_MIN_IDLE_LABEL = "Couchbase.minIdle";
    private static final String MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS_LABEL = "Couchbase.timeBetweenEvictions";
    private static final String MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME_LABEL = "Couchbase.minIdleEvictionTime";
    private static final String MODULE_COUCHBASE_SESSION_TIMEOUT_LABEL = "Couchbase.sessionTimeOut";
    private static final String MODULE_STATS_BUCKET_NAME_LABEL = "moduleStats.bucketName";
    private static final String MODULE_STATS_BUCKET_PASSWORD_LABEL = "moduleStats.bucketPassword";
    private static final String MODULE_STATS_POOL_SIZE_LABEL = "moduleStats.poolSize";
    private static final String MODULE_STATS_OBJECTS_SECS_LABEL = "moduleStats.objectsSecs";
    private static final String MODULE_CACHE_BUCKET_NAME_LABEL = "moduleCache.bucketName";
    private static final String MODULE_CACHE_BUCKET_PASSWORD_LABEL = "moduleCache.bucketPassword";
    private static final String MODULE_CACHE_POOL_SIZE_LABEL = "moduleCache.poolSize";
    private static final String MODULE_CACHE_OBJECTS_SECS_LABEL = "moduleCache.objectsSecs";
    private static final String DB_HOST_LABEL = "db.host";
    private static final String DB_PORT_LABEL = "db.port";
    private static final String DB_NAME_LABEL = "db.name";
    private static final String DB_USER_LABEL = "db.user";
    private static final String DB_PASSWORD_LABEL = "db.password";
    private static final String DB_DRIVER_LABEL = "db.driver";
    private static final String CURRENT_BB_PHONE_VERSION_LABEL = "mobileProperties.currentBBPhoneVersion";
    private static final String CURRENT_ANDROID_PHONE_VERSION_LABEL = "mobileProperties.currentAndroidPhoneVersion";
    private static final String CURRENT_IOS_PHONE_VERSION_LABEL = "mobileProperties.currentIOSPhoneVersion";
    private static final String CURRENT_BB_TABLET_VERSION_LABEL = "mobileProperties.currentBBTabletVersion";
    private static final String CURRENT_ANDROID_TABLET_VERSION_LABEL = "mobileProperties.currentAndroidTabletVersion";
    private static final String CURRENT_IOS_TABLET_VERSION_LABEL = "mobileProperties.currentIOSTabletVersion";
    private static final String CURRENT_WEB_VERSION_LABEL = "mobileProperties.currentWebVersion";
    private static final String BLACKBERRY_UPDATE_ID_LABEL = "mobileProperties.bbUpdateId";
    private static final String BLACKBERRY_TABLET_UPDATE_ID_LABEL = "mobileProperties.bbTabletUpdateId";
    private static final String ANDROID_UPDATE_ID_LABEL = "mobileProperties.androidUpdateId";
    private static final String ANDROID_TABLET_UPDATE_ID_LABEL = "mobileProperties.androidTabletUpdateId";
    private static final String IOS_UPDATE_ID_LABEL = "mobileProperties.iosUpdateId";
    private static final String IOS_TABLET_UPDATE_ID_LABEL = "mobileProperties.iosTabletUpdateId";
    private static final String OBLIGATORY_UPDATE_LABEL = "mobileProperties.obligatoryUpdate";
    private static final String WEB_DOWNLOAD_URL_LABEL = "mobileProperties.webDownload";
    private static final String BB_PHONE_DOWNLOAD_URL_LABEL = "mobileProperties.bbPhoneDownload";
    private static final String ANDROID_PHONE_DOWNLOAD_URL_LABEL = "mobileProperties.androidPhoneDownload";
    private static final String IOS_PHONE_DOWNLOAD_URL_LABEL = "mobileProperties.iosPhoneDownload";
    private static final String BB_TABLET_DOWNLOAD_URL_LABEL = "mobileProperties.bbTabletDownload";
    private static final String ANDROID_TABLET_DOWNLOAD_URL_LABEL = "mobileProperties.androidTabletDownload";
    private static final String IOS_TABLET_DOWNLOAD_URL_LABEL = "mobileProperties.iosTabletDownload";
    //MOBILE PROPERTIES
    public static final String CURRENT_BB_PHONE_VERSION;
    public static final String CURRENT_ANDROID_PHONE_VERSION;
    public static final String CURRENT_IOS_PHONE_VERSION;
    public static final String CURRENT_BB_TABLET_VERSION;
    public static final String CURRENT_ANDROID_TABLET_VERSION;
    public static final String CURRENT_IOS_TABLET_VERSION;
    public static final String CURRENT_WEB_VERSION;
    public static final String BLACKBERRY_UPDATE_ID;
    public static final String BLACKBERRY_TABLET_UPDATE_ID;
    public static final String ANDROID_UPDATE_ID;
    public static final String ANDROID_TABLET_UPDATE_ID;
    public static final String IOS_UPDATE_ID;
    public static final String IOS_TABLET_UPDATE_ID;
    public static final String OBLIGATORY_UPDATE;
    public static final String WEB_DOWNLOAD_URL;
    public static final String BB_PHONE_DOWNLOAD_URL;
    public static final String ANDROID_PHONE_DOWNLOAD_URL;
    public static final String IOS_PHONE_DOWNLOAD_URL;
    public static final String BB_TABLET_DOWNLOAD_URL;
    public static final String ANDROID_TABLET_DOWNLOAD_URL;
    public static final String IOS_TABLET_DOWNLOAD_URL;
    //DB PROPERTIES
    public static final String DB_HOST;
    public static final String DB_PORT;
    public static final String DB_NAME;
    public static final String DB_USER;
    public static final String DB_PASSWORD;
    public static final String DB_DRIVER;
    //COUCHBASE
    public static final String MODULE_COUCHBASE_URI;
    public static final long MODULE_COUCHBASE_MAXWAIT_MILLIS;
    public static final int MODULE_COUCHBASE_MIN_IDLE;
    public static final long MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS;
    public static final long MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME;
    public static final int MODULE_COUCHBASE_SESSION_TIMEOUT;
    // Module Cache variables
    public static final String MODULE_CACHE_BUCKET_NAME;
    public static final String MODULE_CACHE_BUCKET_PASSWORD;
    public static final int MODULE_CACHE_POOL_SIZE;
    public static final int MODULE_CACHE_OBJECTS_SECS;
    // Module stats variables
    public static final String MODULE_STATS_BUCKET_NAME;
    public static final String MODULE_STATS_BUCKET_PASSWORD;
    public static final int MODULE_STATS_POOL_SIZE;
    public static final int MODULE_STATS_OBJECTS_SECS;

    static {
        // Loading XML config file
        CONFIG = new XMLConfiguration();
        CONFIG.setFileName(CONFIG_FILE_NAME);
        try {
            CONFIG.load();

        } catch (ConfigurationException e) {
            throw new RuntimeException();
        }

        //Setting couchbase server
        MODULE_COUCHBASE_URI = CONFIG.getString(MODULE_COUCHBASE_URI_LABEL);
        MODULE_COUCHBASE_MAXWAIT_MILLIS = CONFIG.getLong(MODULE_COUCHBASE_MAXWAIT_MILLIS_LABEL);
        MODULE_COUCHBASE_MIN_IDLE = CONFIG.getInt(MODULE_COUCHBASE_MIN_IDLE_LABEL);
        MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS = CONFIG.getLong(MODULE_COUCHBASE_TIME_BETWEEN_EVICTIONS_LABEL);
        MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME = CONFIG.getLong(MODULE_COUCHBASE_MIN_IDLE_EVICTION_TIME_LABEL);
        MODULE_COUCHBASE_SESSION_TIMEOUT = CONFIG.getInt(MODULE_COUCHBASE_SESSION_TIMEOUT_LABEL);
        // Initializing Module Cache Connector
        MODULE_CACHE_BUCKET_NAME = CONFIG.getString(MODULE_CACHE_BUCKET_NAME_LABEL);
        MODULE_CACHE_BUCKET_PASSWORD = CONFIG.getString(MODULE_CACHE_BUCKET_PASSWORD_LABEL);
        MODULE_CACHE_POOL_SIZE = CONFIG.getInt(MODULE_CACHE_POOL_SIZE_LABEL);
        MODULE_CACHE_OBJECTS_SECS = CONFIG.getInt(MODULE_CACHE_OBJECTS_SECS_LABEL);

        // Initializing Module stats Connector
        MODULE_STATS_BUCKET_NAME = CONFIG.getString(MODULE_STATS_BUCKET_NAME_LABEL);
        MODULE_STATS_BUCKET_PASSWORD = CONFIG.getString(MODULE_STATS_BUCKET_PASSWORD_LABEL);
        MODULE_STATS_POOL_SIZE = CONFIG.getInt(MODULE_STATS_POOL_SIZE_LABEL);
        MODULE_STATS_OBJECTS_SECS = CONFIG.getInt(MODULE_STATS_OBJECTS_SECS_LABEL);

        //Initializing RSA parameters
        DB_HOST = CONFIG.getString(DB_HOST_LABEL);
        DB_PORT = CONFIG.getString(DB_PORT_LABEL);
        DB_NAME = CONFIG.getString(DB_NAME_LABEL);
        DB_USER = CONFIG.getString(DB_USER_LABEL);
        DB_PASSWORD = CONFIG.getString(DB_PASSWORD_LABEL);
        DB_DRIVER = CONFIG.getString(DB_DRIVER_LABEL);

        //Initializing MOBILE version parameters
        CURRENT_BB_PHONE_VERSION = CONFIG.getString(CURRENT_BB_PHONE_VERSION_LABEL);
        CURRENT_ANDROID_PHONE_VERSION = CONFIG.getString(CURRENT_ANDROID_PHONE_VERSION_LABEL);
        CURRENT_IOS_PHONE_VERSION = CONFIG.getString(CURRENT_IOS_PHONE_VERSION_LABEL);
        CURRENT_BB_TABLET_VERSION = CONFIG.getString(CURRENT_BB_TABLET_VERSION_LABEL);
        CURRENT_ANDROID_TABLET_VERSION = CONFIG.getString(CURRENT_ANDROID_TABLET_VERSION_LABEL);
        CURRENT_IOS_TABLET_VERSION = CONFIG.getString(CURRENT_IOS_TABLET_VERSION_LABEL);
        CURRENT_WEB_VERSION = CONFIG.getString(CURRENT_WEB_VERSION_LABEL);

        //Initializing MOBILE's update id parameters
        BLACKBERRY_UPDATE_ID = CONFIG.getString(BLACKBERRY_UPDATE_ID_LABEL);
        BLACKBERRY_TABLET_UPDATE_ID = CONFIG.getString(BLACKBERRY_TABLET_UPDATE_ID_LABEL);
        ANDROID_UPDATE_ID = CONFIG.getString(ANDROID_UPDATE_ID_LABEL);
        ANDROID_TABLET_UPDATE_ID = CONFIG.getString(ANDROID_TABLET_UPDATE_ID_LABEL);
        IOS_UPDATE_ID = CONFIG.getString(IOS_UPDATE_ID_LABEL);
        IOS_TABLET_UPDATE_ID = CONFIG.getString(IOS_TABLET_UPDATE_ID_LABEL);
        OBLIGATORY_UPDATE = CONFIG.getString(OBLIGATORY_UPDATE_LABEL);
        WEB_DOWNLOAD_URL = CONFIG.getString(WEB_DOWNLOAD_URL_LABEL);
        BB_PHONE_DOWNLOAD_URL = CONFIG.getString(BB_PHONE_DOWNLOAD_URL_LABEL);
        ANDROID_PHONE_DOWNLOAD_URL = CONFIG.getString(ANDROID_PHONE_DOWNLOAD_URL_LABEL);
        IOS_PHONE_DOWNLOAD_URL = CONFIG.getString(IOS_PHONE_DOWNLOAD_URL_LABEL);
        BB_TABLET_DOWNLOAD_URL = CONFIG.getString(BB_TABLET_DOWNLOAD_URL_LABEL);
        ANDROID_TABLET_DOWNLOAD_URL = CONFIG.getString(ANDROID_TABLET_DOWNLOAD_URL_LABEL);
        IOS_TABLET_DOWNLOAD_URL = CONFIG.getString(IOS_TABLET_DOWNLOAD_URL_LABEL);

    }

    private AppXMLConfiguration() {
    }
}
