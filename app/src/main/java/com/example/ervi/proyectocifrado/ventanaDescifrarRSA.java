package com.example.ervi.proyectocifrado;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ventanaDescifrarRSA extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    String pathArchivo;
    String llaves;
    Button buscarArchivo;
    Button buscarLLaves;
    Button Descifrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_descifrar_rs);

        buscarArchivo = (Button) findViewById(R.id.btnBuscarArchivoDescifrarRSA);
        buscarLLaves = (Button) findViewById(R.id.btnBuscarLlavePrivada);
        Descifrar = (Button) findViewById(R.id.btnDescifrarRSA);

        buscarLLaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                performFileSearch();

            }
        });

        buscarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BuscarLLaves();
                performFileSearch();

            }
        });
    }


    public void BuscarLLaves()
    {
        readText(pathArchivo);
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    private void readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text = new StringBuilder();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            llaves = line;
            br.close();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void leerYEscribir(String input) {
        File file = new File(Environment.getExternalStorageDirectory(), input);
        StringBuilder stb = new StringBuilder();
        try {
            int c;

            FileInputStream fileStream = new FileInputStream(file);
            InputStreamReader Fichero = new InputStreamReader(fileStream,"UTF-8");
            RSA miCifrado = new RSA();
            String[] llavesArray = llaves.split(",");
            StringBuilder mb = new StringBuilder();
            while ((c = Fichero.read()) != -1) {
                char a = (char) c;
                int numeros = (int) c;
                String escribirDato = miCifrado.Descifrar(numeros,Integer.valueOf(llavesArray[1]),Integer.valueOf(llavesArray[0]));
                //mb.append(escribirDato);
                Escribir(escribirDato);
            }
            fileStream.close();
            //escribirArchivo3(mb.toString());
            Toast.makeText(this,"Guardado",Toast.LENGTH_SHORT).show();
            llaves = stb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CifrarArchivo2(View view)
    {
        leerYEscribir(pathArchivo);
    }

    public void Escribir(String escribir)
    {
        String nombre =pathArchivo.substring(0,pathArchivo.indexOf("."));

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),nombre+"Descifrado"+".txt");
        try {

            // FileOutputStream fos2 = new FileOutputStream(file);
            FileWriter fos = new FileWriter(file,true);
            BufferedWriter fos2 = new BufferedWriter(fos);
            fos2.write(escribir);
            fos2.close();
            fos.close();
            // Toast.makeText(this,"Guardado",Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();

                pathArchivo = path;

            /*
            ;
             pathArchivo2 = path;
                tvPath.setText(path);
                pathArchivo3 =path.substring(0,path.indexOf("."));
            tvBinario.setText(miArbol.getBinarioVentana());
            tvAscii.setText(miArbol.getPasarAscii());
            */
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void escribirArchivo3(String escrito) {

        String nombre =pathArchivo.substring(0,pathArchivo.indexOf("."));

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),nombre +"DescompresoRSA.txt");
        try {
            FileOutputStream fos2 = new FileOutputStream(file);

            fos2.write(escrito.getBytes());
            fos2.close();
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
}
