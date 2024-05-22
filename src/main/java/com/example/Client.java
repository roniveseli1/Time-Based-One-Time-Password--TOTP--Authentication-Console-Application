package com.example;

import com.google.gson.JsonObject;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client {

    private static final String SECRET = "JBSWY3DPEHPK3PXP"; // Base32 encoded secret key
    private static final Logger logger = Logger.getLogger("ClientLog");

    public static void main(String[] args) {
        try {
            // Setup logging
            FileHandler fh = new FileHandler("client.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to TOTP Authentication System.");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            // Generate the TOTP based on the secret
            String generatedTotp = generateTotp();
            System.out.println("Generated TOTP: " + generatedTotp);
            System.out.print("Enter your TOTP: ");
            String enteredTotp = scanner.nextLine();

            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("totp", enteredTotp);

            // Load the trust store
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream trustStoreFis = new FileInputStream("clienttruststore.jks")) {
                trustStore.load(trustStoreFis, "password".toCharArray());
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Create SSL socket
            SSLSocketFactory factory = sslSocketFactory;
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 8080);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(json.toString());

            String response = in.readLine();
            System.out.println(response);

            if (response.contains("successful")) {
                logger.info("User " + username + " authenticated successfully.");
                System.out.println("Authentication successful. You now have access to the system.");
            } else {
                logger.warning("User " + username + " failed to authenticate.");
                System.out.println("Authentication failed. Please try again.");
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateTotp() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return String.valueOf(gAuth.getTotpPassword(SECRET));
    }
}
