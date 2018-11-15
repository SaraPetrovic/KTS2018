package ftn.kts.transport.security;

import ftn.kts.transport.model.Role;
import ftn.kts.transport.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class JwtValidator {

    @Value("${secret-key}")
    private String secret;

    public User validate(String token) {

        User user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            user = new User();

            user.setUsername(body.getSubject());
            user.setPassword(body.getId());
            user.setRoles(Role.valueOf((String)body.get("role")));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return user;
    }
}
