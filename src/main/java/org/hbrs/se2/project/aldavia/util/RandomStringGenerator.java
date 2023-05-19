package org.hbrs.se2.project.aldavia.util;

public class RandomStringGenerator {
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(chars.charAt((int) Math.floor(Math.random() * chars.length())));
        }
        return stringBuilder.toString();
    }
}
