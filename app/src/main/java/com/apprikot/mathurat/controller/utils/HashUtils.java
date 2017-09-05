package com.apprikot.mathurat.controller.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String calcHash(String text) {
        try {
            byte[] byteArr = encrypt(text);
            byte[] encodedByteArr = Base64.encodeBase64(byteArr);
            return new String(encodedByteArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(value.getBytes());
        return md.digest();
    }
}