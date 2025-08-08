package Utiliti;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SYPAGOapi {

    public static String geyStsusReports(String username, String apiKEY, String apiUrl, String client_id) throws IOException {

        String auth = username + ";" + apiKEY;
        String encodeAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encodeAuth);
        connection.setRequestProperty("Content-Type", "application/json");

        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            String jsonInputString = "{\"client_id\": \"" + client_id + "\", \"secret\": \"" + apiKEY + "\"}";
            os.writeBytes(jsonInputString);
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        // Logica corregida para leer del InputStream en respuestas exitosas ademas de agregar (HTTP_OK) para esperar status code 200 ajustado para casos API
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            connection.disconnect();
            return response.toString();
        } else {
            // Logica para leer del ErrorStream en respuestas de error
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
            } catch (Exception e) {
                // Si el flujo de error no está disponible, se ignora
            }
            connection.disconnect();
            throw new RuntimeException("La solicitud HTTP falló con el código: " + responseCode + " Detalles de Error: " + errorResponse.toString());
        }
    }

    public static void main(String [] args){
        String user = "jose";
        String apiKEY = "ibv8RLCUDSjgh2BwibN33SzynnKM404I";
        String apiUrl = "https://pruebas.sypago.net:8086/api/v1/auth/token";
        String client_id = "jose";

        try {
            System.out.println("Iniciando SYPAGO request...");
            String response = geyStsusReports(user, apiKEY, apiUrl, client_id);
            System.out.println("Request completed.");
            System.out.println("Respuesta del Servidor: " + response);
        } catch (IOException e) {
            System.err.println("Se produjo un error de I/O: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Se produjo un error durante la solicitud HTTP: " + e.getMessage());
        }
    }
}