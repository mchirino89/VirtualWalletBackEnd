
package com.synergygb.billeteravirtual.core.utils;


import com.synergygb.billeteravirtual.core.exceptions.EncryptionException;
import java.util.Date;
import java.util.Random;

public class CookieUtils {
    
    public static String calculateCookieId(String dcn) throws EncryptionException{
        Random r = new Random();
        String rnum = String.valueOf(r.nextInt(1000000));
        
        Date now = new Date(System.currentTimeMillis());
        String timestamp = now.toString();
        
        return MD5.encrypt(dcn + timestamp + rnum);
    }
}
