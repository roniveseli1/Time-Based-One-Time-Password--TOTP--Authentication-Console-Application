package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {

    private static final String SECRET = "JBSWY3DPEHPK3PXP"; // Base32 encoded secret key
    private static final Logger logger = Logger.getLogger("ServerLog");

    public static void main(String[] args) {
        try {
            // Setup logging
            FileHandler fh = new FileHandler("server.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

            // Set up the SSL context and server socket
            System.setProperty("javax.net.ssl.keyStore", "server.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "Roni.2004");
            System.setProperty("javax.net.ssl.trustStore", "server.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "Roni.2004");

            // Create SSL server socket
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(8080);

            System.out.println("Server started and awaiting authentication requests...");

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String data = in.readLine();
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(data, JsonObject.class);

                String username = json.get("username").getAsString();
                String totp = json.get("totp").getAsString();

                System.out.println("Received authentication request for user: " + username);
                System.out.println("TOTP provided: " + totp);

                String response = validateTotp(username, totp);
                out.println(response);

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String validateTotp(String username, String totp) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(SECRET, Integer.parseInt(totp));

        if (isCodeValid) {
            logger.info("Authentication successful for user: " + username);
            return "Authentication successful";
        } else {
            logger.warning("Authentication failed for user: " + username);
            return "Authentication failed";
        }
    }
}
