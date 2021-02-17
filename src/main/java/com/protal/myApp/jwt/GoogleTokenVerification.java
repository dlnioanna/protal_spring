package com.protal.myApp.jwt;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Repository.UserRepository;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GoogleTokenVerification {
    private static final String GOOGLE_ID = "495787401556-ri9600efu58daort2k5rn5ass0nc4fe8.apps.googleusercontent.com";
    private static boolean TOKEN_VERIFIED;
    public static User socialUser;

    public static boolean googleTokenVerify(String token) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(GOOGLE_ID))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                // επειδή στο entity τα εχω ορίσει ΝoΝull δίνω στάνταρ τιμές
                Long tempTelephone = 0000000000L;
                String tempPassword = "noPassword";
                String tempRole = "USER";
                socialUser = new User(givenName,familyName,tempTelephone,email,name,tempPassword,tempRole);
                TOKEN_VERIFIED = true;
            } else {
                TOKEN_VERIFIED = false;
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TOKEN_VERIFIED;
    }
}
