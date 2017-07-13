package com.example.user.findgarage10.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by student on 13-07-17.
 */

public class HashMyString {
    private String myHash;

    public HashMyString(String stringToHash){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            myHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getMyHash(){
        return myHash;
    }
}
