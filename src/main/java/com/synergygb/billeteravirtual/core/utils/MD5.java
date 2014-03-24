/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synergygb.billeteravirtual.core.utils;

import com.synergygb.billeteravirtual.core.exceptions.EncryptionException;
import com.synergygb.logformatter.LogUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

/**
 * Nuevo Mundo Mobile Car Insurance REST Web Services
 * 
 * Class defining a web service handler for @link LoginResource post method
 * 
 * @author Synergy-GB
 * @author John Crespo
 * @version 1.0
 */
public class MD5 {
    static final Logger logger = Logger.getLogger(MD5.class);
    public final static String COMMUNICATION_HEADER_NAME = "MD5 Encryption";

    /**
     * Calculates a MD5 encoded string from the string given as input
     * Inspired in the code published on "http://www.geekpedia.com/code114_MD5-Encryption-Using-Java.html"
     */
    public static String encrypt(String toEnc) throws EncryptionException {

        String password = toEnc;
        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
           
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.error(LogUtils.getLogMessage(COMMUNICATION_HEADER_NAME, "Error durante la ejecución del algoritmo de encriptacion", true));
            throw new EncryptionException();
        }
        
    }
}
