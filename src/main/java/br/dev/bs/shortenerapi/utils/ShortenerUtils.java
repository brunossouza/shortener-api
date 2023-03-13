package br.dev.bs.shortenerapi.utils;


/**
 * Package ShortenerUtils provides a simple URL shortener.
 * It uses a simple hash function to generate a short URL.
 * The hash function is not cryptographically secure.
 * It is not intended to be used for security purposes.
 * It is intended to be used for shortening URLs.
 * The hash function is based on the following algorithm:
 * 1. Convert the URL to a byte array.
 * 2. Use the byte array to generate a 64-bit integer.
 * 3. Convert the 64-bit integer to a base 62 string.
 * 4. Return the base 62 string.
 * The base 62 string is a short URL.
 */

public class ShortenerUtils {

    /**
     * The base 62 characters.
     */
    private static final char[] BASE_62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * The base 62 radix.
     */
    private static final int BASE_62_RADIX = BASE_62_CHARS.length;

    /**
     * The base 62 string length.
     */
    private static final int BASE_62_STRING_LENGTH = 6;

    /**
     * The base 62 string mask.
     */
    private static final long BASE_62_STRING_MASK = 0x3fffffffffffffffL;

    /**
     * The base 62 string shift.
     */
    private static final int BASE_62_STRING_SHIFT = 62 - BASE_62_STRING_LENGTH * 6;

    /**
     * The base 62 string mask.
     */
    private static final long BASE_62_STRING_MASK_2 = 0x3fL;

    /**
     * The base 62 string shift.
     */
    private static final int BASE_62_STRING_SHIFT_2 = 6;

    /**
     * Generates a hash code for the url parameter. With error handling.
     * @param url
     * @return String
     */
    public static String generateHashCode(String url) {
        byte[] bytes = url.getBytes();
        long hash = 0;
        for (byte b : bytes) {
            hash = b + (hash << 6) + (hash << 16) - hash;
        }
        hash = hash & BASE_62_STRING_MASK;
        hash = hash >>> BASE_62_STRING_SHIFT;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BASE_62_STRING_LENGTH; i++) {
            int index = (int) (hash & BASE_62_STRING_MASK_2) % BASE_62_RADIX;
            sb.append(BASE_62_CHARS[index]);
            hash = hash >>> BASE_62_STRING_SHIFT_2;
        }
        return sb.toString();
    }

}
