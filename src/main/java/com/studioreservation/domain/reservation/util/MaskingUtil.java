package com.studioreservation.domain.reservation.util;

public class MaskingUtil {
    public static String maskingUserNm(String userNm) {
        if (userNm == null || userNm.isEmpty()) {
            return userNm;
        }
        int length = userNm.length();

        if (length == 1) {
            return userNm;
        } else if (length == 2) {
            return userNm.charAt(0) + "*";
        } else {
            int maskLength = length - 2;
            return userNm.charAt(0)
                    + "*".repeat(maskLength)
                    + userNm.charAt(length - 1);
        }

    }
}