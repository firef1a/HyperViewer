package dev.fire.firemod.devutils;

import java.util.Base64;

public class Base64Utils {
    public static byte[] decodeBase64Bytes(String str) {
        return Base64.getDecoder().decode(str);
    }
}
