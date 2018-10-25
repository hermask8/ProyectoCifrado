package com.example.ervi.proyectocifrado;

import java.util.ArrayList;

public class RSA {




    public String Cifrar(String texto,int N,int llavePublica) {
        String result = "";

        int Char = texto.charAt(0);
        double power = Math.pow(Char, llavePublica);
        int ascii = (int) (power % N);
        result = String.valueOf(Character.toChars(ascii));

        return result;
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
