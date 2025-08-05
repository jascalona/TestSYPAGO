package Labels;

import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author usersycom
 */
public class GeneratorUserReferenceId {

    //Metodo statico
    public static String GeneratorChart(int longitud) {
        //Caracteres que se usaran
        String charsId = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder(); //Secuencia de caracteres mutables

        //Intancia Random
        Random generatorRandom = new Random();

        for (int i = 0; i < longitud; i++) {
            //Generar el indice aletorio para seleccionar el caracter
            int indice = generatorRandom.nextInt(charsId.length());
            // Se añade el carácter seleccionado al StringBuilder
            codigo.append(charsId.charAt(indice));
        }
        return codigo.toString();
    }
    
    public static void main(String[] args){
        int longitud = 20; //Longitud del stc
        System.out.println("String creado: " + GeneratorChart(longitud));
    }
    
}
