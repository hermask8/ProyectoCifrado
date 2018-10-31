package com.example.ervi.proyectocifrado;

public class SDES {

    private String key1;
    private String key2;
    private String S0 [][] = {{"01","00","11","10"},
            {"11","10","01","00"},
            {"00","10","01","11"},
            {"11","01","11","10"}};

    private String S1 [][] = {{"00","01","10","11"},
            {"10","00","01","11"},
            {"11","00","01","00"},
            {"10","01","00","11"}};

    //MÉTODOS PASO A PASO PARA GENERAR LAS LLAVES
    public void GenerarLlaves(int clave){
        String ClaveBits = CovertirClaveEnBits(clave);
        ClaveBits = P10(ClaveBits);
        String Mitad1 = ObtenerClaveMitad1(ClaveBits);
        String Mitad2 = ObtenerClaveMitad2(ClaveBits);
        Mitad1 = LS_1(Mitad1);
        Mitad2 = LS_1(Mitad2);
        key1 = P8(Mitad1,Mitad2);
        Mitad1 = LS_2(Mitad1);
        Mitad2 = LS_2(Mitad2);
        key2 = P8(Mitad1,Mitad2);
    }
    private String CovertirClaveEnBits(int clave){
        String ClaveBits= "";
        int cociente = clave;
        int residuo;
        while (cociente > 1){
            residuo = cociente%2;
            cociente = cociente/2;
            ClaveBits = residuo + ClaveBits;
        }
        ClaveBits = cociente + ClaveBits;
        while (ClaveBits.length() < 10){
            ClaveBits = "0" + ClaveBits;
        }
        return ClaveBits;
    }
    private String P10(String clave){
        String permutada = "abcdefghij";
        permutada = permutada.replace('a', clave.charAt(7));
        permutada = permutada.replace('b', clave.charAt(5));
        permutada = permutada.replace('c', clave.charAt(4));
        permutada = permutada.replace('d', clave.charAt(2));
        permutada = permutada.replace('e', clave.charAt(3));
        permutada = permutada.replace('f', clave.charAt(0));
        permutada = permutada.replace('g', clave.charAt(1));
        permutada = permutada.replace('h', clave.charAt(9));
        permutada = permutada.replace('i', clave.charAt(6));
        permutada = permutada.replace('j', clave.charAt(8));
        return permutada;
    }
    private String ObtenerClaveMitad1(String clave){
        String nueva = "";
        for (int i = 0; i < 5; i++) {
            nueva += clave.charAt(i);
        }
        return nueva;
    }
    private String ObtenerClaveMitad2(String clave){
        String nueva = "";
        for (int i = 5; i < 10; i++) {
            nueva += clave.charAt(i);
        }
        return nueva;
    }
    private String LS_1 (String mitad){
        String nueva = "";
        for (int i = 1; i < 5; i++) {
            nueva += mitad.charAt(i);
        }
        nueva+= mitad.charAt(0);
        return nueva;
    }
    private String LS_2 (String mitad){
        String nueva = "";
        nueva = LS_1(mitad);
        nueva = LS_1(nueva);
        return nueva;
    }
    private String P8 (String mitad1, String mitad2){
        String unida = mitad1+mitad2;
        String permutada = "abcdefgh";
        permutada = permutada.replace('a', unida.charAt(8));
        permutada = permutada.replace('b', unida.charAt(6));
        permutada = permutada.replace('c', unida.charAt(3));
        permutada = permutada.replace('d', unida.charAt(4));
        permutada = permutada.replace('e', unida.charAt(0));
        permutada = permutada.replace('f', unida.charAt(7));
        permutada = permutada.replace('g', unida.charAt(9));
        permutada = permutada.replace('h', unida.charAt(1));
        return permutada;
    }


    //ORDEN DE LLAMADA DE LOS BLOQUES PARA CIFRAR Y DESCIFRAR
    public char Cifrar(int caracter){
        String Bits = ConvertirCharEnBits(caracter);
        Bits = IP(Bits);
        Bits = fk(Bits, 1);
        Bits = SW(Bits);
        Bits = fk(Bits, 2);
        Bits = IP_1(Bits);
        return (char)ConvertirBitsEnInt(Bits);
    }
    public char Descifrar(int caracter){
        String Bits = ConvertirCharEnBits(caracter);
        Bits = IP(Bits);
        Bits = fk(Bits, 2);
        Bits = SW(Bits);
        Bits = fk(Bits, 1);
        Bits = IP_1(Bits);
        return (char)ConvertirBitsEnInt(Bits);
    }

    //BLOQUES DEL PROCESO DE CIFRADO Y DESCIFRADO
    public String fk(String Bits, int key){
        String Mitad1 = ObtenerTextoMitad1(Bits);
        String Mitad2 = ObtenerTextoMitad2(Bits);
        Bits = EP(Mitad2);
        String llave = key==1?key1:key2;
        Bits = XOR(llave,Bits);
        String mitadS0 = ObtenerTextoMitad1(Bits);
        String mitadS1 = ObtenerTextoMitad2(Bits);
        mitadS0 = ObtenerS0(mitadS0);
        mitadS1 = ObtenerS1(mitadS1);
        Bits = P4(mitadS0, mitadS1);
        Mitad1 = XOR(Bits, Mitad1);
        return Mitad1 + Mitad2;
    }
    private String ConvertirCharEnBits(int caracter){
        String Bits= "";
        int cociente = caracter;
        int residuo;
        while (cociente > 1){
            residuo = cociente%2;
            cociente = cociente/2;
            Bits = residuo + Bits;
        }
        Bits = cociente + Bits;
        while (Bits.length() < 8){
            Bits = "0" + Bits;
        }
        return Bits;
    }
    private String IP(String bits){
        String permutada = "abcdefgh";
        permutada = permutada.replace('a', bits.charAt(4));
        permutada = permutada.replace('b', bits.charAt(0));
        permutada = permutada.replace('c', bits.charAt(5));
        permutada = permutada.replace('d', bits.charAt(1));
        permutada = permutada.replace('e', bits.charAt(6));
        permutada = permutada.replace('f', bits.charAt(2));
        permutada = permutada.replace('g', bits.charAt(7));
        permutada = permutada.replace('h', bits.charAt(3));
        return permutada;
    }
    private String ObtenerTextoMitad1(String bits){
        String nueva = "";
        for (int i = 0; i < 4; i++) {
            nueva += bits.charAt(i);
        }
        return nueva;
    }
    private String ObtenerTextoMitad2(String bits){
        String nueva = "";
        for (int i = 4; i < 8; i++) {
            nueva += bits.charAt(i);
        }
        return nueva;
    }
    private String EP(String mitad){
        String permutada = "abcdefgh";
        permutada = permutada.replace('a', mitad.charAt(0));
        permutada = permutada.replace('b', mitad.charAt(2));
        permutada = permutada.replace('c', mitad.charAt(1));
        permutada = permutada.replace('d', mitad.charAt(3));
        permutada = permutada.replace('e', mitad.charAt(3));
        permutada = permutada.replace('f', mitad.charAt(2));
        permutada = permutada.replace('g', mitad.charAt(0));
        permutada = permutada.replace('h', mitad.charAt(1));
        return permutada;
    }
    private String XOR(String llave, String texto){
        String resultado = "";
        for (int i = 0; i < texto.length(); i++) {
            resultado+=(llave.charAt(i)==texto.charAt(i))?"0":"1";
        }
        return resultado;
    }
    private String ObtenerS0(String mitad){
        int fila = ObtenerInt(mitad.charAt(0)+"", mitad.charAt(3) + "");
        int columna = ObtenerInt(mitad.charAt(1)+"", mitad.charAt(2) + "");
        return S0[fila][columna];
    }
    private String ObtenerS1(String mitad){
        int fila = ObtenerInt(mitad.charAt(0)+"", mitad.charAt(3) + "");
        int columna = ObtenerInt(mitad.charAt(1)+"", mitad.charAt(2) + "");
        return S1[fila][columna];
    }
    private int ObtenerInt(String mitad1, String mitad2){
        String cadena = mitad1+mitad2;
        int valor = cadena.equals("00")?0:cadena.equals("01")?1:cadena.equals("10")?2:3;
        return valor;
    }
    private String P4(String mitad1,String mitad2){
        String unida = mitad1+mitad2;
        String permutada = "abcd";
        permutada = permutada.replace('a', unida.charAt(3));
        permutada = permutada.replace('b', unida.charAt(2));
        permutada = permutada.replace('c', unida.charAt(1));
        permutada = permutada.replace('d', unida.charAt(0));
        return permutada;
    }
    private String SW(String cadena){
        String mitad1 = "";
        String mitad2 = "";
        for (int i = 0; i < 4; i++) {
            mitad1 += cadena.charAt(i);
        }
        for (int i = 4; i < 8; i++) {
            mitad2 += cadena.charAt(i);
        }
        return mitad2+mitad1;
    }
    private String IP_1(String bits){
        String permutada = "abcdefgh";
        permutada = permutada.replace('a', bits.charAt(1));
        permutada = permutada.replace('b', bits.charAt(3));
        permutada = permutada.replace('c', bits.charAt(5));
        permutada = permutada.replace('d', bits.charAt(7));
        permutada = permutada.replace('e', bits.charAt(0));
        permutada = permutada.replace('f', bits.charAt(2));
        permutada = permutada.replace('g', bits.charAt(4));
        permutada = permutada.replace('h', bits.charAt(6));
        return permutada;
    }
    private int ConvertirBitsEnInt(String bits){
        int valor = 0;
        valor+= bits.charAt(0) == '1'?128:0;
        valor+= bits.charAt(1) == '1'?64:0;
        valor+= bits.charAt(2) == '1'?32:0;
        valor+= bits.charAt(3) == '1'?16:0;
        valor+= bits.charAt(4) == '1'?8:0;
        valor+= bits.charAt(5) == '1'?4:0;
        valor+= bits.charAt(6) == '1'?2:0;
        valor+= bits.charAt(7) == '1'?1:0;
        return valor;
    }
}

