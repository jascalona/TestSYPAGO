package Utiliti;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 *
 * @author usersycom
 */
public class SYPAGOapi {

    /**
     * Sends an HTTP POST request to the SYPAGO gateway API.
     *
     * @param username userName
     * @param apiKEY password for Basic Authentication.
     * @param apiUrl The URL of the API endpoint.
     * @param jsonPayload The JSON string to send in the request body.
     * @return The server's response body as a String if the request is
     * successful (HTTP 202 Accepted).
     * @throws IOException If an I/O error occurs during the connection or data
     * transfer.
     * @throws RuntimeException If the HTTP response code indicates an error
     * (not 202 Accepted).
     */
    public static String geyStsusReports(String username, String apiKEY, String apiUrl, String internal_id) throws IOException {

        String auth = username + ";" + apiKEY;
        String encodeAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic" + encodeAuth);
        connection.setRequestProperty("Content-Type", "application/json");

        //Envios de datos JSON
        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes("{\\\"PmtStsReq\\\": {\\\"internal_id\\\": \\\"\" + internal_id + \"\\\",\\\"LclInstrm\\\": \\\"01\\\",\\\"product\\\": \\\"string\\\",\\\"bank_code\\\": \\\"\\\"}}");
            os.flush();
        }

        int responseCode = connection.getResponseCode(); //Obtener el status code HTTP
        System.out.println("Response code: " + responseCode); //debugin

        if (responseCode == HttpURLConnection.HTTP_ACCEPTED) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            connection.disconnect();
            return response.toString();
        } else {
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
            } catch (Exception e) {
                // Ignorar si el flujo de error no está disponible
            }
            connection.disconnect();
            throw new RuntimeException("La solicitud HTTP fallo con el codigo" + responseCode + ".Detalles de Error: " + errorResponse.toString());
        }
    }
    
    public static void main(String [] args){
        String user = "jose";
        String apiKEY = "ibv8RLCUDSjgh2BwibN33SzynnKM404I";
        String apiUrl = "https://pruebas.sypago.net:8086/api/v1/transaction/credit/";
        String internal_id = "11111111111";
        
        try{
            System.out.println("Iniciando API...");
            String response = geyStsusReports(user, apiKEY, apiUrl, internal_id);
            System.out.println("Request completado.");
            System.out.println("Respuesta del servidor: " + response);
            
        } catch(IOException e){
            System.out.println("Se produjo un error de I/O" + e.getMessage());
            e.printStackTrace();
        } catch(RuntimeException e){
            System.out.println("Se produjo un error durante la solicitud HTTP: "+ e.getMessage());
        }
        
    }
    
    

}
