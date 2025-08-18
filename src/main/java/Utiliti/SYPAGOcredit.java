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

        String Token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmZXpQcl9HSWhIZ05jOVc1cU5Td2FIQXBRMVRqeUlqbWtpY0d5V1hHUjFzIn0.eyJleHAiOjE3NTU1NTcxMjEsImlhdCI6MTc1NTUyMTEyMSwianRpIjoiN2FjYzIxZTMtOTdhZi00YmI4LWE0ZmMtNDBhNWM3YjZiMDUzIiwiaXNzIjoiaHR0cHM6Ly9wcnVlYmFzLnN5cGFnby5uZXQ6ODA4MS9yZWFsbXMvc3lwYWdvIiwic3ViIjoiNjM2Mjc0ZDktZGU0YS00Y2VhLWI4ZTMtNTBhZWU4YjY2NTZkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiam9zZSIsInNjb3BlIjoic3lwYWdvX2FwaV9rZXlfc2NvcGU6NTVhNGVjMzktNDI0Zi00NDIzLWI5MTgtYjgxMWZkMDQ3OTk2LlVzZXIiLCJjbGllbnRIb3N0IjoiMTcyLjIwLjAuMSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMjAuMC4xIiwiY2xpZW50X2lkIjoiam9zZSJ9.OgC7J1gbo3d0ega3Tq6vagfzwe24oC8zAAVHKcjd5ZilRwlMt5SnJ7PK41M-pM7Z9CJdWWPRAEulgyiH3s1LFv6pBPkQSojKVGzWVg3_aJOxOQ8s3vmhS9fIOechmvt0mgL3pwjWBKGjP1htEiI2CklbfbMJu8Pr2ZPhDB0OcurWkRsLNYHqq3nN8Ln4GcyJ4t3qvE_GwujUFyXlXkN76Y2arA7MuZ3ai_OnmJ8foqCzH-w9kVmXAMI2hhyAP80CWdOmJYEM53BmGJZlWenqxeQ8zJp8byHa5sJC69lhwY2gtB5zznO4lVWnlqRQwJHlGeGc7-tJdViajzOxZIYZ-w";
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