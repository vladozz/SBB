package com.tsystems.javaschool.vm.service;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static final Logger LOGGER = Logger.getLogger(Hasher.class);

    private Hasher() {
    }

    public static String hash(String text, String alg)  {
        //the hash code
        StringBuilder code = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(alg);
            byte[] bytes = text.getBytes();

            //create code
            byte[] digest = messageDigest.digest(bytes);
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Hash exception!", e);


        }
        return null;
    }

    public static String md5(String text) {
        //the hash code
        StringBuilder code = new StringBuilder();
        try {
            byte[] digest = MessageDigest.getInstance("md5").digest(text.getBytes());
            for (byte aDigest : digest) {
                code.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Hash md5 exception!", e);
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
