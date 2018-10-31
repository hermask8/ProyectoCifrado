package com.example.ervi.proyectocifrado;

import java.util.ArrayList;

public class RSA {




    public int Cifrar(char texto,int N,int llavePublica) {
        String result = "";

        int Char = (int) texto;
        double power = Math.pow(Char, llavePublica);
        int ascii = (int)(power % N);
        result = String.valueOf(Character.toChars(ascii));

        return ascii;
    }

    public String Descifrar(String texto,int N , int LlavePrivada) {
        String result = "";

        int Char = texto.charAt(0);
        double power = Math.pow(Char, LlavePrivada);
        int ascii = (int) power % N;

        result = String.valueOf(Character.toChars(ascii));

        return result;
    }


}
