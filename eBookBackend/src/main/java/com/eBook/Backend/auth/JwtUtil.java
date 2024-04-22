package com.eBook.Backend.auth;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.eBook.Backend.models.AuthUser;

import java.util.Date;

import java.util.concurrent.TimeUnit;

@Component
// Class responsible for managing JWT token.
public class JwtUtil {


	//Secret key used in encryption and token validity time.
    private final String secret_key = "mysecretkey";
    private long accessTokenValidity = 60*60*1000;

    //A parser for reading JWT strings, used to convert them into a JWT object.
    private final JwtParser jwtParser;

    //Strings used in token validation.
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    //All args constructor.
    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    // Function to create a JWT token and assigns it to a user.
    public String createToken(AuthUser user) {
        Claims claims = Jwts.claims().setSubject(user.getId());
        claims.put("userName",user.getUsername());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }
    
    
    // Parses the input token.
    private Claims parseJwtClaims(String token){
    	return jwtParser.parseClaimsJws(token).getBody();
    }

    // All the following methods help in JWT token validation.
    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    
    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

}