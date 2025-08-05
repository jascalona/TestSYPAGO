package Utiliti;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author usersycom
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SYPAGOUtilTest {

    public static void main(String[] args) throws IOException {
        String user = "sycom_gateway_user";
        String password = "3b17-0e38206Pae-88_@fb8476dff92f271A7C";
        String auth = user + ":" + password;

        //String jsonInputString = "{\"data\": \"Sample Data\"}";
        String jsonInputString = "{\"PmtStsReq\": {\"TransactionId\": \"11111111111\",\"LclInstrm\": \"01\",\"Purp\": \"string\",\"SendingBankCode\": \"\"}}";

        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        URL url = new URL("http://10.0.62.20:8086/sypago/gateway/api/v1/solest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
        connection.setRequestProperty("Content-Type", "application/json");

        System.out.println("000");
        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(jsonInputString);
            os.flush();
        }

        System.out.println("111");
        int responseCode = connection.getResponseCode();
        System.out.println("222");

        System.out.println("Response Code : " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) {
            // HTTP_OK or 200 response code generally means that the server ran successfully without any errors
            StringBuilder response = new StringBuilder();

            // Read response content
            // connection.getInputStream() purpose is to obtain an input stream for reading the server's response.
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line); // Adds every line to response till the end of file.
                }
            }
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("Error: HTTP Response code - " + responseCode);
        }
        connection.disconnect();
    }

}
