package ru.geekbrains.smartkt.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Component
// класс работы с токенами - их создание и извлечение из них данных
// JWT = JSON Web Token -- стандарт для создания токенов доступа,
// используемый при передаче данных для авторизации в КСП
public class TokenUtil {
    @Value("${jwt.sign-base}")
    private String signatureBase;

    @Value("${jwt.lifetime}")
    private Integer lifetime;

    // создать пользовательский токен с указанными сроком действия и базой для формирования его подписи
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        System.out.println("base="+signatureBase+", lifetime="+lifetime);
        Date issuedDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(new Date(issuedDate.getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, signatureBase)
                .compact();
    }

    public String getUsernameFromToken(String token) { return getClaimFromToken(token, Claims::getSubject); }

    public List<String> getRoles(String token) {
        return getClaimFromToken(token, claims -> claims.get("roles", List.class));
    }

    public String getEmail(String token) {
        return getClaimFromToken(token, claims -> claims.get("email", String.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signatureBase)
                .parseClaimsJws(token)
                .getBody();
    }
}