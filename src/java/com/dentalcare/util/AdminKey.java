package com.dentalcare.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Secret key that unlocks the admin login page (/admin/login.jsp?secret_key=...).
 * Read from the ADMIN_SECRET_KEY environment variable; when it is not set, the page stays closed.
 *
 * @author Syahir
 */
public class AdminKey {
    private static final String KEY = System.getenv("ADMIN_SECRET_KEY");

    public static boolean matches(String candidate) {
        if(KEY == null || KEY.isEmpty() || candidate == null) {
            return false;
        }
        //constant-time comparison, so the key cannot be guessed from response times
        return MessageDigest.isEqual(KEY.getBytes(StandardCharsets.UTF_8), candidate.getBytes(StandardCharsets.UTF_8));
    }

    //path to forward to the admin login page, including the key
    public static String loginPath() {
        try {
            return "/admin/login.jsp?secret_key=" + URLEncoder.encode(KEY == null ? "" : KEY, "UTF-8");
        }
        catch(UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
