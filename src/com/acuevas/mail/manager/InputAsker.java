/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acuevas.mail.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manages input from the user.
 *
 * @author Mar
 */
public abstract class InputAsker {

    /**
     * asks for a string
     *
     * @param texto
     * @return
     */
    public static String pedirCadena(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cadena = "";
        do {
            try {
                System.out.println(texto);
                cadena = br.readLine();
                if (cadena.equals("")) {
                    System.out.println("This camp can't be blank.");
                }
            } catch (IOException ex) {
                System.out.println("Input/Output Error");
            }
        } while (cadena.equals(""));
        return cadena;
    }

    /**
     * asks for a double
     *
     * @param texto
     * @return
     */
    public static double pedirDouble(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double num = 0;
        boolean error;
        do {
            try {
                System.out.println(texto);
                num = Double.parseDouble(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Input/Output Error");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("You must insert a number");
                error = true;
            }
        } while (error);
        return num;
    }

    /**
     * asks for an integer
     *
     * @param texto
     * @return
     */
    public static int pedirEntero(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(texto);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Input/Output Error");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("You must insert an integer number");
                error = true;
            }
        } while (error);
        return num;
    }

    /**
     * returns if a number is integer or not
     */
    public static boolean esEntero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
