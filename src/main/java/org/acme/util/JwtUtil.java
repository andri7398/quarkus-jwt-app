package org.acme.util;

import io.smallrye.jwt.build.Jwt;

import java.time.Duration;
import java.util.Set;

public class JwtUtil {
    public static String generateToken(String username) {
        return Jwt.issuer("auth-app")
                .upn(username)
                .groups(Set.of("user"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}
