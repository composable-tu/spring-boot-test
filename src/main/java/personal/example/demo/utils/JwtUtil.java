package personal.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import personal.example.demo.config.JwtConfig;

@Component
public class JwtUtil {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  public JwtUtil(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
    this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String account) {
    return Jwts.builder()
        .subject(account)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
        .signWith(secretKey)
        .compact();
  }

  public Claims extractClaims(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  public String extractAccount(String token) {
    return extractClaims(token).getSubject();
  }

  public boolean isTokenValid(String token) {
    try {
      extractClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
