package com.example.challenge.userlogin.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.challenge.userlogin.constants.JwtConstants;

import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

public class JwtUtility {

    public static String getBasicAuth(String username, String password) {
        String userInfo = username + ":" + password;
        return new String(Base64.getEncoder().encode(userInfo.getBytes()));
    }

    public static String getAccessToken(String basicAuth) {
        HashMap<String, String> jwtTokenClaims = new HashMap<>();
        jwtTokenClaims.put(JwtConstants.CLAIM_BASIC, basicAuth);
        return getJwtToken(jwtTokenClaims);
    }

    public static String getJwtToken(HashMap<String, String> jwtTokenClaims) {
        Algorithm algorithm = Algorithm.HMAC256(JwtConstants.JWT_SECRET);
        JWTCreator.Builder builder = JWT.create();
        Set<String> claimsKeys = jwtTokenClaims.keySet();
        for (String claimsKey : claimsKeys) {
            builder.withClaim(claimsKey, jwtTokenClaims.get(claimsKey));
        }
        builder.withIssuer(JwtConstants.TOKEN_ISSUER);
        return builder.sign(algorithm);
    }

    public static String parseJwtToken(String authorization) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtConstants.JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(JwtConstants.TOKEN_ISSUER).build();
            DecodedJWT token = verifier.verify(authorization);
            return token.getClaim(JwtConstants.CLAIM_BASIC).asString();
        }
        catch (JWTVerificationException exception) {
            return JwtConstants.TOKEN_INVALID_KEY;
        }
    }

}
