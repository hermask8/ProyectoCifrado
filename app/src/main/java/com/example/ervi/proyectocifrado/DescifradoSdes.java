package com.example.ervi.proyectocifrado;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescifradoSdes extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    SDES cifrarDatos = new SDES();
    String pathArchivo;
    Button agregarArchivo;
    Button cifrar;
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descifrado_sdes);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        clave = (EditText) findViewById(R.id.edDclave);
        agregarArchivo =(Button) findViewById(R.id.btnBuscarDSdes);
        cifrar = (Button) findViewById(R.id.btnDescifrarSdes);
        agregarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                performFileSearch();
            }
        });
        cifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                readText(pathArchivo);
            }
        });
    }


    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(), input);
        StringBuilder stb = new StringBuilder();
        try {
            int c;
            FileInputStream fileStream = new FileInputStream(file);
            cifrarDatos.GenerarLlaves(Integer.parseInt(clave.getText().toString()));
            while ((c = fileStream.read()) != -1) {
                char a = (char) c;
                stb.append(cifrarDatos.Descifrar(c));
            }
            escribirArchivo(stb.toString());
            fileStream.close();
            /*
            FileReader entrada = new FileReader(pathArchivo);
            int c;
            while ((c = entrada.read()) != -1) {
                char nuevo = ((char) c);
            }

            return content;
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void escribirArchivo(String aEscribir) {

        String nombre =pathArchivo.substring(0,pathArchivo.indexOf("."));

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),nombre +"DEscifrado.txt");
        try {
            FileOutputStream fos2 = new FileOutputStream(file);
            String escribir = aEscribir;
            fos2.write(escribir.getBytes());
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
