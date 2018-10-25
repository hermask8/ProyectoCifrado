package com.example.ervi.proyectocifrado;

import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {




    public String Cifrar(int texto,int N,int llavePublica) {
        String result = "";

        BigInteger Char = BigInteger.valueOf(texto);
        BigInteger n = BigInteger.valueOf(N);
        BigInteger e = BigInteger.valueOf(llavePublica);
        BigInteger power = Char.modPow(e,n);
        //BigInteger ascii = new BigInteger(String.valueOf(power.doubleValue() % N));
        result = String.valueOf(Character.toChars((int)power.longValue()));

        return result;
    }

    public String Descifrar(int texto,int N , int LlavePrivada) {
        String result = "";
        BigInteger Char = BigInteger.valueOf(texto);
        BigInteger n = BigInteger.valueOf(N);
        BigInteger e = BigInteger.valueOf(LlavePrivada);
        BigInteger power = Char.modPow(e,n);
        //BigInteger ascii = new BigInteger(String.valueOf(power.doubleValue() % N));
        result = String.valueOf(Character.toChars((int)power.longValue()));

        return result;
    }


}
