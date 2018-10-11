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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DescifrarVentana extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    String pathArchivo2;
    Button Buscar;
    Button Descifrar;
    String nombre;
    EditText etNivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descifrar_ventana);


        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        Buscar =(Button) findViewById(R.id.btnBuscarArchivo);
        Descifrar = (Button) findViewById(R.id.btnDescifrar);
        etNivel =(EditText) findViewById(R.id.etNivelDescifrado);
        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                performFileSearch();
            }
        });
        Descifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                escribirArchivo();

            }
        });
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();
                pathArchivo2 = path;

                nombre =path.substring(0,path.indexOf("."));
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

    private String readText(String input) {

        File file = new File(Environment.getExternalStorageDirectory(), input);
        byte[] values = new byte[(int)file.length()];
        StringBuilder text = new StringBuilder();
        try {

            FileInputStream fileStream = new FileInputStream(file);

            fileStream.read(values);
            fileStream.close();
            String content = new String(values,"UTF-8");
            return  content;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return values.toString();
    }

    public void escribirArchivo() {

        //INSTANCIO MI CLASE
        Descifrado des = new Descifrado();
        String fileName = nombre +"Descifrado.txt";
        String nuevo = readText(pathArchivo2);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            FileOutputStream fos2 = new FileOutputStream(file);
            //SI EL NIVEL ES 1 MANDARLO A ESCRIBIR TAL Y COMO ESTÁ
            //SI ES MAYOR A 1 MANDA A LLAMAR AL MÉTODO
            //EL MÉTODO DEVUELVE UN STRING Y HAY QUE MANDARLE UN STRING Y UN INT
            // VALIDAR QUE EL NIVEL QUE SE INGRESA SEA UN INT

            if(etNivel.getText().toString().trim().isEmpty()){
                Toast.makeText(this,"Llene todo el formulario",Toast.LENGTH_SHORT).show();
            }else{

                String nivel = String.valueOf(etNivel.getText());
                int nivel2 = Integer.valueOf(nivel);
                if (nivel2==1)
                {
                    String escribir = readText(pathArchivo2);
                    fos2.write(escribir.getBytes());
                    fos2.close();
                    Toast.makeText(this,"Guardado",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String escribir = des.Descifrar(nuevo,nivel2);
                    fos2.write(escribir.getBytes());
                    fos2.close();
                    Toast.makeText(this,"Guardado",Toast.LENGTH_SHORT).show();
                }

            }

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
