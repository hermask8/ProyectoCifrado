package com.example.ervi.proyectocifrado;

import java.util.ArrayList;

public class Descifrado {

    public String Descifrar(String textoCifrado, int nivel){
        int TamañoOla = (nivel * 2) - 2;
        int NumOlas = (textoCifrado.length()/TamañoOla);
        NumOlas = NumOlas==0?1:NumOlas;
        int TamañoBloque = NumOlas*2;
        String texto = "";
        String Valles = "";
        String Crestas = "";
        ArrayList<String> Bloques = new ArrayList<>();

        //CRESTAS Y VALLES
        for (int i = 0; i < NumOlas; i++) {
            Crestas += textoCifrado.charAt(i);
        }
        for (int i = textoCifrado.length()-1; i > (textoCifrado.length()-1-NumOlas); i--) {
            Valles = textoCifrado.charAt(i) + Valles;
        }

        //LLENAR BLOQUES
        int contadorBloque = 0;
        for (int i = NumOlas; i < textoCifrado.length()-NumOlas; i++) {
            contadorBloque++;
            texto += textoCifrado.charAt(i);
            if (contadorBloque == TamañoBloque){
                Bloques.add(texto);
                texto = "";
                contadorBloque = 0;
            }
        }

        //DESCIFRAR
        texto = "";
        int posCresta = 0;
        for (int i = 0; i < TamañoBloque; i++) {
            if ((i%2)==0){
                texto += Crestas.charAt(posCresta);
                for (int j = 0; j < Bloques.size(); j++) {
                    texto += Bloques.get(j).charAt(i);
                }
                texto += Valles.charAt(posCresta);
                posCresta++;
            }else{
                for (int j = Bloques.size()-1 ; j >= 0; j--) {
                    texto += Bloques.get(j).charAt(i);
                }
            }
        }
        //QUITAR RELLENO
        String textoTerminado = "";
        for (int i = 0; i < texto.length(); i++) {
            if(texto.charAt(i) != '%'){
                textoTerminado += texto.charAt(i);
            }
        }

        return textoTerminado;
    }
}
