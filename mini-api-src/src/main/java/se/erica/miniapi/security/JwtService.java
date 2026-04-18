package se.erica.miniapi.security; 

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.io.Decoders; 
import io.jsonwebtoken.security.Keys; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Service; 

import javax.crypto.SecretKey; 
import java.util.Date; 
import java.util.function.Function; 

@Service 
public class JwtService { 

    private final String secretKey; 
    private final long expirationMs; 

    public JwtService( 
            @Value("${app.jwt.secret}") String secretKey, 
            @Value("${app.jwt.expiration-ms}") long expirationMs 
    ) { 
        this.secretKey = secretKey; 
        this.expirationMs = expirationMs; 
    } 

    public String generateToken(UserDetails userDetails) { 
        long now = System.currentTimeMillis(); 

        return Jwts.builder() 
                .subject(userDetails.getUsername()) 
                .issuedAt(new Date(now)) 
                .expiration(new Date(now + expirationMs)) 
                .signWith(getSignInKey()) 
                .compact(); 
    } 

    public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 

    public boolean isTokenValid(String token, UserDetails userDetails) { 
        String username = extractUsername(token); 
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); 
    } 

    private boolean isTokenExpired(String token) { 
        return extractClaim(token, Claims::getExpiration).before(new Date()); 
    } 

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        Claims claims = Jwts.parser() 
                .verifyWith(getSignInKey()) 
                .build() 
                .parseSignedClaims(token) 
                .getPayload(); 

        return claimsResolver.apply(claims); 
    } 

    private SecretKey getSignInKey() { 
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
} 
