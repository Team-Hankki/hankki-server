package org.hankki.hankkiserver.oauth.apple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class ApplePrivateKeyGenerator {

    @Value("${oauth.apple.private-key}")
    private String privateKey;

    protected PrivateKey getPrivateKey() throws Exception {

        byte[] keyBytes = Base64.getDecoder().decode(privateKey);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(spec);

    }
}
