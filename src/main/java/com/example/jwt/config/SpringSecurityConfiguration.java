package com.example.jwt.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.hibernate.grammars.hql.HqlParser.SecondContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurityConfiguration
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    public SecurityFilterChain defaultSecurityFilterChain(
        HttpSecurity httpSecurity
    ) {
        try {
            return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeHttpRequests ->
                    authorizeHttpRequests
                        .requestMatchers("")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .build();
        } catch (Exception e) {
            throw new RuntimeException(
                "Spring Security configuration failed",
                e
            );
        }
    }

    // @Bean
    // public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {
    // NimbusJwtEncoder jwtEncoder;
    // try {
    // // jwtEncoder = new NimbusJwtEncoder()
    // }
    // }

    // @Bean
    // public JWKSource<SecurityContext> jwtSource() throws NoSuchAlgorithmException
    // {
    // KeyPair keyPair = generateRsaKey();
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // RSAKey rsaKey = new RSAKey.Builder(publicKey)
    // .keyID(UUID.randomUUID().toString())
    // .build();
    // JWKSet jwtSet = new JWKSet(rsaKey);
    // return new ImmutableJWKSet<>(jwtSet);
    // }

    // @Bean
    // private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
    // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    // keyPairGenerator.initialize(2048);
    // return keyPairGenerator.generateKeyPair();
    // }

    public static SecretKey generateKey()
        throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        // Store the key in a safe place

        return key;
    }
}
