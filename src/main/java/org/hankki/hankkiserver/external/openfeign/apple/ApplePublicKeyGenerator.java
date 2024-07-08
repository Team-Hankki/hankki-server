package org.hankki.hankkiserver.external.openfeign.apple;

import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKey;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class ApplePublicKeyGenerator {

    private static final String SIGN_ALGORITHM_HEADER_KEY = "alg";
    private static final String KEY_ID_HEADER_KEY = "kid";

    public PublicKey generatePublicKey(
            Map<String, String> headers,
            ApplePublicKeys applePublicKeys
    ) {
        ApplePublicKey applePublicKey = applePublicKeys
                .getMatchedPublicKey(headers.get(KEY_ID_HEADER_KEY), headers.get(SIGN_ALGORITHM_HEADER_KEY));

        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new UnauthorizedException(ErrorCode.UNSUPPORTED_ALGORITHM);
        } catch (InvalidKeySpecException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_KEY_SPEC);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
