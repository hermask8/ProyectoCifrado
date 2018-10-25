package com.example.ervi.proyectocifrado;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class generarLlavesVentana extends AppCompatActivity {

    EditText numero1;
    EditText numero2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_llaves_ventana);

        numero1 = (EditText) findViewById(R.id.edtPrimerNumero);
        numero2 = (EditText) findViewById(R.id.edtSegundoNumero);
    }

    public void Generar(View view)
    {
        generarLlaves misLlaves = new generarLlaves();
        if(Integer.valueOf(numero1.getText().toString())%2!=0 && Integer.valueOf(numero2.getText().toString())%2!=0)
        {
            misLlaves.generarLlaves(Integer.valueOf(numero1.getText().toString()),Integer.valueOf(numero2.getText().toString()));
            EscribirLlavePrivada(misLlaves.getLlavePrivada());
            EscribirLlavePublica(misLlaves.getLlavePublica());
        }
        else
        {
            Toast.makeText(this,"Alguno de los 2 numeros no son pares",Toast.LENGTH_SHORT).show();
        }

    }
    public void EscribirLlavePrivada(String escribir)
    {

        String nombre= String.valueOf(numero1.getText()) +","+ String.valueOf(numero2.getText());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Private"+nombre+".key");
        try {

           // FileOutputStream fos2 = new FileOutputStream(file);
            FileWriter fos = new FileWriter(file,true);
            BufferedWriter fos2 = new BufferedWriter(fos);
            fos2.write(escribir);
            fos2.close();
            fos.close();
            Toast.makeText(this,"Guardado",Toast.LENGTH_SHORT).show();
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Archivo no encontrado",Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"ERROR Guardando",Toast.LENGTH_SHORT).show();
        }

    }
    public void EscribirLlavePublica(String escribir)
    {

        String nombre= String.valueOf(numero1.getText()) +","+ String.valueOf(numero2.getText());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Public"+nombre+".key");
        try {

            // FileOutputStream fos2 = new FileOutputStream(file);
            FileWriter fos = new FileWriter(file,true);
            BufferedWriter fos2 = new BufferedWriter(fos);
            fos2.write(escribir);
            fos2.close();
            fos.close();

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Archivo no encontrado",Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"ERROR Guardando",Toast.LENGTH_SHORT).show();
        }

    }
}
