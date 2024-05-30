package com.bma.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] bytes = sha1.digest(password.getBytes());

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes){
            builder.append(String.format("%02X ",  b));
        }
        return builder.toString();
    }

    public static HashUtil getInstance(){
        return INSTANCE;
    }
    private HashUtil(){}
    private static final HashUtil INSTANCE = new HashUtil();
}
