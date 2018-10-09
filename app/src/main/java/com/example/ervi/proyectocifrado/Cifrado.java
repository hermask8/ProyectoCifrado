package com.example.ervi.proyectocifrado;

public class Cifrado {
    private String[][] ola;
    public String cadenaCifrada ="";
    int Nivel;
    int TamanoCadena;


    public Cifrado(String cadena,int nivel)
    {
        this.Nivel = nivel;
        int columnas = cadena.length();
        this.TamanoCadena = columnas;
        ola = new String [nivel][columnas];
        int k =0;
        boolean arribaAbajo =true;
        for (int i=0; i<nivel;)
        {
            for (int j=0;j<columnas;j++)
            {
                if (j!=(columnas))
                {
                    if (arribaAbajo==true)
                    {
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        k++;
                        i++;
                    }
                    if (arribaAbajo==false)
                    {
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        k++;
                        i--;
                    }
                    if (i==(nivel-1)&&arribaAbajo==true)
                    {
                        j++;
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        i--;

                        arribaAbajo = false;
                        k++;

                    }
                    if (i==0&&arribaAbajo==false)
                    {
                        if (k!=columnas) {
                            ola[i][j] = String.valueOf(cadena.charAt(k));
                            i++;
                            k++;
                            j++;
                            arribaAbajo = true;
                        }
                        else
                        {
                            i=nivel;
                        }

                    }
                }
                else
                {
                    i=nivel;
                }

            }
        }
        int numero = 0;
    }

    public String getCadenaCifrada()
    {
        for (int i =0;i<Nivel;i++)
        {
            for (int j=0;j<TamanoCadena;j++)
            {
                if (ola[i][j]!=null)
                    cadenaCifrada = cadenaCifrada+ ola[i][j];
            }
        }
        return cadenaCifrada;
    }
}
