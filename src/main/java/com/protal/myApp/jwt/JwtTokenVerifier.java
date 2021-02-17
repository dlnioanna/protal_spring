package com.protal.myApp.jwt;

import com.google.common.base.Strings;
import com.protal.myApp.Entity.User;
import com.protal.myApp.Repository.UserRepository;
import com.protal.myApp.Service.UserService;
import com.protal.myApp.Utils.DateUtils;
import com.protal.myApp.Utils.JsonUtils;
import io.jsonwebtoken.*;
import lombok.var;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.crypto.SecretKey;
import javax.jws.soap.SOAPBinding;
import javax.management.remote.rmi._RMIConnection_Stub;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


public class JwtTokenVerifier extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfig jwtConfig, UserRepository userRepository) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //παίρνω το authorization header. Αν δεν υπάρχει δεν γίνεται authorization
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        //παίρνω το τοκεν τους header και αφαιρώ το Bearer
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
//Αν το τοκεν ειναι της google
        if (GoogleTokenVerification.googleTokenVerify(token)) {
            //ψαψνω με βάση το email ως μοναδικό αναγνωριστικό
            User user = userRepository.findByEmail(GoogleTokenVerification.socialUser.getEmail());
            Authentication authentication = null;
                    /*αν ο χρήστης δε εχει κάνει εγγραφή και δεν υπάρχει στη βαση
                   βαζω στο τοκεν το όνομα που έχει στη google και τον κάνω απλό χρήστη     */
            if (user == null) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("USER");
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
                simpleGrantedAuthorities.add(simpleGrantedAuthority);
                authentication = new UsernamePasswordAuthenticationToken(
                        GoogleTokenVerification.socialUser.getUsername(),
                        null,
                        simpleGrantedAuthorities
                );
                String tokenResponse = Jwts.builder()
                        .setSubject(GoogleTokenVerification.socialUser.getUsername())
                        .claim("authorities", simpleGrantedAuthorities)
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                        .signWith(secretKey)
                        .compact();
                response.setHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + tokenResponse);
            } else {
                //αν ο χρήστης εχει κάνει εγγραφή και υπάρχει στη βαση βαζω στο τοκεν το όνομα και τον ρολο που εχει στη βάση
                String role = user.getRole();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
                simpleGrantedAuthorities.add(simpleGrantedAuthority);
                authentication = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        simpleGrantedAuthorities
                );
                String tokenResponse = Jwts.builder()
                        .setSubject(user.getName())
                        .claim("authorities", simpleGrantedAuthorities)
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                        .signWith(secretKey)
                        .compact();
                response.setHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + tokenResponse);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                var authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
            }
        }
        filterChain.doFilter(request, response);
    }
}
