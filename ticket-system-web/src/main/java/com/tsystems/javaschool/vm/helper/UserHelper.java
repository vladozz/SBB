package com.tsystems.javaschool.vm.helper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UserHelper {
    private static final Logger LOGGER = Logger.getLogger(UserHelper.class);

    public UserHelper() {
    }

    public String sha256(String text) {
        //the hash code
        StringBuilder code = new StringBuilder();
        try {
            byte[] digest = MessageDigest.getInstance("sha-256").digest(text.getBytes());
            for (byte aDigest : digest) {
                code.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Hash sha-256 exception!", e);
        }
        return null;
    }

    //TODO: change 3 to 10
    public String generatePassword() {
        return sha256(Double.toString(Math.random())).substring(0,3);
    }

}
