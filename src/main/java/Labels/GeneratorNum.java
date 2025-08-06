/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Labels;

import java.util.Random;

/**
 *
 * @author usersycom
 */
public class GeneratorNum {

    public static void GeneratorN(int longitud) {

        int min = 1;
        int max = 100;
        int calRango = max - min + 1;

        for (int i = 0; i < 100; i++) {
            int numAletorio = (int) (Math.random() * calRango);
            System.out.println(numAletorio);
        }
    }

    public static void main(String[] args) {
        GeneratorN(0);
        
        String test = "J34625721";
        System.out.println();
    
    }

}
