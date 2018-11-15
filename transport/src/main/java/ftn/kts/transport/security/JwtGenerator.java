package ftn.kts.transport.security;

import ftn.kts.transport.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    @Value("${secret-key}")
    private String secret;

    public String generate(User user) {


        Claims claims = Jwts.claims()
                .setSubject(user.getUsername())
                .setId(user.getPassword());
        claims.put("role", user.getRoles().get(0));


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
