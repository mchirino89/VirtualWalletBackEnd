
package com.synergygb.billeteravirtual.notificacion.services.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Asegura+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class StatsThreadPool {
    /**
     * Pool of statistic threads that will perform the stats insertion work
     */
    public static ExecutorService statsThreadPoolExecutor = Executors.newFixedThreadPool(8);
}
