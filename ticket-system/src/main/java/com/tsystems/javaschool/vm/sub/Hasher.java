package com.tsystems.javaschool.vm.sub;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static Logger logger = Logger.getLogger(Hasher.class);

    public static String hash(String text, String alg)  {
        StringBuilder code = new StringBuilder(); //the hash code
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(alg);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Hash exception!", e);

            byte bytes[] = text.getBytes();
            byte digest[] = messageDigest.digest(bytes); //create code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
            return code.toString();
        }
        return null;
    }

    public static String md5(String text) {
        StringBuilder code = new StringBuilder(); //the hash code
        try {
            byte[] digest = MessageDigest.getInstance("md5").digest(text.getBytes());
            for (byte aDigest : digest) {
                code.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Hash md5 exception!", e);
        }
        return null;
    }



    public static long [] reverseArray(long[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            long tmp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[i];
            array[i] = tmp;
        }
        return array;
    }
}
