package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider
{
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-millisecond}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication)
    {
        String username =authentication.getName();

        Date currentDate = new Date();

        Date expireDate= new Date(currentDate.getTime()+jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key()
    {
        return  Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //get username from jwt token

    public String getUsername(String token)
    {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //validate jwt token

    public boolean validateToken(String token)
    {
       try
       {
           Jwts.parser()
                   .verifyWith((SecretKey) key())
                   .build()
                   .parse(token);
           return true;
       }
       catch (MalformedJwtException e)
       {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT Token");
       }
       catch (ExpiredJwtException exception)
       {
           throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
       }
       catch (UnsupportedJwtException e)
       {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
       }
       catch (IllegalArgumentException e)
       {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT Claims String is null or empty");
       }
    }
}
