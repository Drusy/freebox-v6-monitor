package drusy.utils;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    static String HmacSha1(String key, String message) {
        SecretKeySpec keySpec = new SecretKeySpec(
                key.getBytes(),
                "HmacSHA1");

        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] rawHmac = mac.doFinal(message.getBytes());

        StringBuffer result = new StringBuffer();
        for (byte b : rawHmac) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
