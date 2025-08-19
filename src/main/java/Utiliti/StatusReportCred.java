package Utiliti;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatusReportCred {

    public static String getSatusCredit(String Token, String apiUrl)  throws IOException {

        URL url =new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        // Correcto: se envía el token directamente
        connection.setRequestProperty("Authorization", Token);
        connection.setRequestProperty("Content-Type" ,"application/json");

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
                System.out.println("Credenciales papu");
            }
            connection.disconnect();
            return errorResponse.toString();
        }
    }


    public static void main(String [] args){

        String Token = "Barer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmZXpQcl9HSWhIZ05jOVc1cU5Td2FIQXBRMVRqeUlqbWtpY0d5V1hHUjFzIn0.eyJleHAiOjE3NTU2NTI3OTMsImlhdCI6MTc1NTYxNjc5MywianRpIjoiMTExMGEzOTAtMWE1NS00Yjk4LTg4NmYtMzhkNzg2OTliNDMyIiwiaXNzIjoiaHR0cHM6Ly9wcnVlYmFzLnN5cGFnby5uZXQ6ODA4MS9yZWFsbXMvc3lwYWdvIiwic3ViIjoiNTc1NTk1YmItNTMyMy00MjkyLWI2NzktNWZiZDEzNmVmN2EzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiam9zZSIsInNjb3BlIjoic3lwYWdvX2FwaV9rZXlfc2NvcGU6NTVhNGVjMzktNDI0Zi00NDIzLWI5MTgtYjgxMWZkMDQ3OTk2LlVzZXIiLCJjbGllbnRIb3N0IjoiMTcyLjIwLjAuMSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMjAuMC4xIiwiY2xpZW50X2lkIjoiam9zZSJ9.z__gnLsiFoaUc-r1AN3hBpQsvZkg8Gyof9twowvAODnDjgYtQ9VGNEIzg1MyU2Y5XxRjeX3WhvC1r8wMN8ZdBlszn3CgRPmWKQijjWvj5G1htJgRTraDNL7SzEkjIs1L6zAInu2PmLbxHk9N9H4hXCEWknV9onulsfkKiKtsfCqxyXCKbL70zEDF0hVSS7HfakjsCGKSWTCw-mWxSrcl4FS4XOY2E98wVIhJxvbu6rmxGFNxKkgteBRrUX1stdDDtCt_UgmziuZhnjrJA_hr64bI-OpTcj8mPqZ-w-TBYfMxh1sF4rqcB4Fq0k1Jwj9UqfLAViAcgWfb-Bbm7i3MpA";
        String internal_id = "BC4D41219CBC";
        String urlAPI = "https://pruebas.sypago.net:8086/api/v1/transaction/" + internal_id;

        try{
            System.out.println("Iniciando Credito SYPAGO....");
            String response = getSatusCredit(Token, urlAPI);
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





