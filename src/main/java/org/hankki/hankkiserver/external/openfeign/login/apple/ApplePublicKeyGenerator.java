package org.hankki.hankkiserver.external.openfeign.login.apple;

import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.InternalServerException;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.ApplePublicKey;
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

    private static final String KEY_ID_HEADER_KEY = "kid";
    private static final String SIGN_ALGORITHM_HEADER_KEY = "alg";

    public PublicKey generatePublicKey(Map<String, String> headers, ApplePublicKeys applePublicKeys) {
        ApplePublicKey applePublicKey = applePublicKeys
                .getMatchedPublicKey(headers.get(KEY_ID_HEADER_KEY), headers.get(SIGN_ALGORITHM_HEADER_KEY));
        return getPublicKey(applePublicKey);
    }

    private PublicKey getPublicKey(ApplePublicKey applePublicKey) {

        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(AuthErrorCode.UNSUPPORTED_ALGORITHM);
        } catch (InvalidKeySpecException e) {
            throw new BadRequestException(AuthErrorCode.INVALID_KEY_SPEC);
        } catch (Exception e) {
            throw new InternalServerException(AuthErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
