package Utiliti;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

public class SYPAGOcredit {

    public static String geyStatusReport(String Token, String apiUrl, String internal_id)  throws IOException {

        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        // Correcto: se envía el token directamente
        connection.setRequestProperty("Authorization", "Bearer " + Token);
        connection.setRequestProperty("Content-Type" ,"application/json");

        // Construir el cuerpo de la solicitud JSON
        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            String jsonInputString = "{\"internal_id\": \"" + internal_id + "\" }";
            os.writeBytes(jsonInputString);
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        // Logica corregida para leer del InputStream en respuestas exitosas ademas de agregar (HTTP_OK) para esperar status code 200 ajustado para casos API
        if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED){
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
            }
            connection.disconnect();
            return response.toString();
        }
        //Logica para leer el ErrorStram en respuesta de error
        else {
            StringBuilder errorResponse = new StringBuilder();

            try(BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream())) ){
                String line;
                while((line = errorReader.readLine()) != null){
                    errorResponse.append(line);
                }
            }
            catch (Exception e){
                //Si el flujo de error no esta disponible, se ignora
            }
            connection.disconnect();
            throw new RuntimeException("La solicitud HTTP falló con el código: " + responseCode + ". Detalles de Error: "  + errorResponse.toString());
        }
    }

    public static void main(String [] args){

        String Token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmZXpQcl9HSWhIZ05jOVc1cU5Td2FIQXBRMVRqeUlqbWtpY0d5V1hHUjFzIn0.eyJleHAiOjE3NTUxMjUzMTksImlhdCI6MTc1NTA4OTMxOSwianRpIjoiYjBlNTM1NGYtOTJhZC00ZjgzLTk5OTMtZDMwMmU1NjgxZWE5IiwiaXNzIjoiaHR0cHM6Ly9wcnVlYmFzLnN5cGFnby5uZXQ6ODA4MS9yZWFsbXMvc3lwYWdvIiwic3ViIjoiNjM2Mjc0ZDktZGU0YS00Y2VhLWI4ZTMtNTBhZWU4YjY2NTZkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiam9zZSIsInNjb3BlIjoic3lwYWdvX2FwaV9rZXlfc2NvcGU6NTVhNGVjMzktNDI0Zi00NDIzLWI5MTgtYjgxMWZkMDQ3OTk2LlVzZXIiLCJjbGllbnRIb3N0IjoiMTcyLjIwLjAuMSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMjAuMC4xIiwiY2xpZW50X2lkIjoiam9zZSJ9.oQnzC1-mk-ARdo5X5NfV5eWahWOBDpR8w9Ha5x8OXkPn2UQrZei2sDka4B_V_A6r3oiK5-w0jx0W5NhzWNr28FLeov_1AKNBEBL1TrCxuYMmXHESeFBc3d3CQzpFBYLRyEkaqV0T6-aMfYjHqTSJSu2NyIMVW56ahFh1wzjGwEMSzSXhivgw0pligFzW8WX7B3SNcy-nYs9ya5e18YnuQxsfK9qj6KRWbVnPzDy8sIsBOwzw3cKWwvlzz0O-hgzBVAVxkIOOQNyje3ufhegBopFDmSSzBf4N49MqWasIk9i2dkKzhOREKti2sVIJ1RyQSot-Gvac5sz5gxByUnLtGA";
        String urlAPI = "https://pruebas.sypago.net:8086/api/v1/transaction/credit";
        String internal_id = "B30E237C80F2";

        try{
            System.out.println("Iniciando Credito SYPAGO....");
            String response = geyStatusReport(Token, urlAPI, internal_id);
            System.out.println("Request Completed" );
            System.out.println("Respuesta del Servidor: " + response);
        } catch (IOException e) {
            System.out.println("Se produjo un error de I/O" + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e){
            System.out.println("Se produjo un error durante la solicitud HTTP: " + e.getMessage());
        }
    }
}