package Utiliti;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


public class StatusReportDom {


    public static String getDom(String token, String urlAPI) throws IOException {

        URL url =new URL(urlAPI);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", token);
        connection.setRequestProperty("Content-Type", "aplication/json");

        //Construccion del cuerpo de la solicitud JSON
        int responseCode = connection.getResponseCode();
        System.out.println("Codigo de respuesta: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK){

            StringBuilder responde =new StringBuilder();

            try(BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream()))){

                String line;
                while((line = reader.readLine()) != null){
                    responde.append(line);
                }
                connection.disconnect();
                return responde.toString();
            }

        } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
            StringBuilder conflictResquest =new StringBuilder();

            try(BufferedReader conflitReader =new BufferedReader(new InputStreamReader(connection.getErrorStream()))){
                String line;
                while((line = conflitReader.readLine()) != null){
                    conflictResquest.append(line);
                }
            }
            connection.disconnect();
            return conflictResquest.toString();

        }

        else {
            StringBuilder errorResponse =new StringBuilder();
            try(BufferedReader errorReader= new BufferedReader(new InputStreamReader(connection.getErrorStream()))){
                String line;
                while((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }

            } catch (Exception e){
                System.out.println("Se esta Quedando en la exception con un: " + responseCode);
            }

            connection.disconnect();
            return errorResponse.toString();

        }

    }


    public static void main(String []args){
        String token = "Barer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmZXpQcl9HSWhIZ05jOVc1cU5Td2FIQXBRMVRqeUlqbWtpY0d5V1hHUjFzIn0.eyJleHAiOjE3NTU4MTY0NzUsImlhdCI6MTc1NTc4MDQ3NSwianRpIjoiNmI0YmM2YjQtOGM1Yy00ODIwLWJiMzgtODg1NTIzZTBjN2U1IiwiaXNzIjoiaHR0cHM6Ly9wcnVlYmFzLnN5cGFnby5uZXQ6ODA4MS9yZWFsbXMvc3lwYWdvIiwic3ViIjoiNTc1NTk1YmItNTMyMy00MjkyLWI2NzktNWZiZDEzNmVmN2EzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiam9zZSIsInNjb3BlIjoic3lwYWdvX2FwaV9rZXlfc2NvcGU6NTVhNGVjMzktNDI0Zi00NDIzLWI5MTgtYjgxMWZkMDQ3OTk2LlVzZXIiLCJjbGllbnRIb3N0IjoiMTcyLjIwLjAuMSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMjAuMC4xIiwiY2xpZW50X2lkIjoiam9zZSJ9.HQINZrzxW62KF44nv35SKstfEsmO_gWPM6hHAjgphmFZvXnAoVV_HJ0T14EHhTDClQCb6iWx-BlUpy9mBCM6ymAQLmz8qzwf9pkKYa20qpgRtA2ZvnydZaNZPTIADgqSnnHl7ZR_wTnML6g41MpSW-3PNnGvife0KxrQ8rbsiaRcCz_GTUiqFMnfrgA4KoCmQYrOS_n0z7_AnuMmbJ2GEwv7-nxlutigwx8Cb5Ny9UPYGYw6Ya1_V_yachtHXAMFTHpUnYVn39lZoDC4VRwRnZ9LzmZNRM5oMWzy5AQBnu3TG_Oiy_hQHH4AXrNB20F72_NyVaGKSmbgElUxf_fjPw";
        String transaction_id = "null";
        String apiUrl = "https://pruebas.sypago.net:8086/api/v1/transaction/" + transaction_id;

        String tes = "01058349143957777777";
        System.out.println(tes.length());
        try{
            System.out.println("Iniciando API Domiciliacion...");
            String response = getDom(token, apiUrl);
            System.out.println("\nRespuesta del servidor: " + response);

        } catch (IOException e){
            System.out.println("Ha ocurrido un error de E/S: " + e.getMessage());
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            System.out.println("Se produjo un error durante la solicitud HTTP: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
