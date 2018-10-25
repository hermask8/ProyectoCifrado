package com.example.ervi.proyectocifrado;

import java.util.ArrayList;

public class generarLlaves {


    int e;
    int d;
    int n;
    String llavePublica;
    String llavePrivada;

    private int getE(int n, int on) {
        ArrayList<Integer> iteraciones = new ArrayList<>();
        ArrayList<Integer> coPrimos = new ArrayList<>();

        for (int i = 2; i < on; i++) {
            if(i % 2 != 0) {
                iteraciones.add(i);
            }
        }

        for (int i = 0; i < iteraciones.size(); i++) {
            int num = iteraciones.get(i);

            if((n % num != 0) && (on % num != 0)) {
                coPrimos.add(num);
            }
        }

        int e =  coPrimos.get(0);
        return e;
    }


    private int getD(int e, int phi) {
        int d = 0;
        ArrayList<Integer> opciones = new ArrayList<>();

        for (int i = 0; i < phi*3; i++) {
            if(e*i % phi == 1) {
                opciones.add(i);
            }
        }
        d =  opciones.get(0);

        return d;
    }

    public void generarLlaves(int p, int q) {
        n = p*q;
        int On = (p-1) * (q-1);
        e = getE(n, On);
        d = getD(e, On);
        llavePublica = e + "," + n;
        llavePrivada = d + "," + n;
    }

    public String getLlavePrivada() {
        return llavePrivada;
    }

    public String getLlavePublica() {
        return llavePublica;
    }
}
