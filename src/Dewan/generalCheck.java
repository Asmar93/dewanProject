package Dewan;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonParser;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class generalCheck {
    private static final String APPLICATION_NAME = "Gmail API Verification Code Retriever";
    private static final String CREDENTIALS_FILE_PATH = "path/to/client_id.json";
    private static final JsonFactory JSON_FACTORY = new JsonObjectParser();

    public static void main(String[] args) throws IOException {
        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();

        // Load client secrets from your credentials file
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        // Set up the OAuth2.0 flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singletonList(GmailScopes.GMAIL_READONLY))
                .setCredentialCreatedListener((credential, tokenResponse) -> {
                    credential.setAccessToken(tokenResponse.getAccessToken());
                    credential.setRefreshToken(tokenResponse.getRefreshToken());
                })
                .setCredentialRefreshListener((credential, tokenResponse) -> {
                    // Handle token refresh if needed
                })
                .build();

        // Authorize and get credentials
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new VerificationCodeReceiver() {
            @Override
            public String getRedirectUri() throws IOException {
                return "urn:ietf:wg:oauth:2.0:oob";
            }

            @Override
            public void stop() throws IOException {
                // Handle stop event if needed
            }

            @Override
            public String waitForCode() throws IOException {
                // Enter the verification code manually
                System.out.print("Enter the authorization code: ");
                return new Scanner(System.in).nextLine();
            }
        }).authorize("user");

        // Create Gmail service
        Gmail gmailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, setHttpTimeout(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List Gmail messages (you can customize this part to locate the verification code email)
        ListMessagesResponse response = gmailService.users().messages().list("me").execute();
        List<Message> messages = response.getMessages();

        if (messages != null && !messages.isEmpty()) {
            Message message = messages.get(0); // Assuming the first message contains the code

            // Get the message content
            Message fullMessage = gmailService.users().messages().get("me", message.getId()).execute();
            String messageContent = fullMessage.getSnippet(); // or use fullMessage.get("raw")

            // Extract the verification code from the message content
            // Implement your code parsing logic here

            System.out.println("Verification Code: " + extractedVerificationCode);
        } else {
            System.out.println("No messages found.");
        }
    }

    private static HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer) {
        return request -> {
            requestInitializer.initialize(request);
            request.setReadTimeout(60000);  // 1 minute read timeout
        };
    }
}

