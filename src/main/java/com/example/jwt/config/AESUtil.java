package com.example.jwt.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    public static void storePrivateKeyToFile(PrivateKey privateKey) {
        try {
            FileOutputStream fos = new FileOutputStream("private.key");
            fos.write(privateKey.getEncoded());
            fos.close();
        } catch (IOException e) {
            logger.error("Error writing private key to file", e.getMessage());
        }
    }

    public static void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                    "RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            storePrivateKeyToFile(keyPair.getPrivate());
        } catch (Exception e) {
            logger.error("Error generating key pair", e.getMessage());
        }
    }

    public static PublicKey getPublicKeyFromFile(KeyPair keyPair) {
        try {
            File publicKeyFile = new File("public.key");
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

            // Use the KeyFactory class to create the actual instance
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // The key bytes needs to wrapped with an EncodedKeySpec class
            // X509EncodedKeySpec which represents the default algorithms for Key::getEncode
            // method we used for saving the file
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            logger.error("Error getting public key from file", e.getMessage());
        }
        return null;
    }

    public static PrivateKey getPrivateKeyFromFile(KeyPair keyPair) {
        return keyPair.getPrivate();
    }
}
