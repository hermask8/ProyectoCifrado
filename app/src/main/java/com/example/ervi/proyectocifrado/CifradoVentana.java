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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class CifradoVentana extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button agregarArchivo;
    Button btnCifrar;
    EditText etTexto;
    EditText etNivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cifrado_ventana);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        btnCifrar =(Button) findViewById(R.id.btnCifrar);
        etTexto =(EditText) findViewById(R.id.etTexto);
        etNivel =(EditText) findViewById(R.id.etNivel);
        btnCifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                escribirArchivo();

            }
        });
    }

    private void performFileSearch()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode== READ_REQUEST_CODE && resultCode == RESULT_OK)
        {
            if (data!=null)
            {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                Toast.makeText(this,"" + path,Toast.LENGTH_SHORT).show();


                /*
                pathArchivo2 = path;
                tvPath.setText(path);
                pathArchivo3 =path.substring(0,path.indexOf("."));
                ;
                tvBinario.setText(miArbol.getBinarioVentana());
                tvAscii.setText(miArbol.getPasarAscii());
                */
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_REQUEST_STORAGE)
        {
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"permission granted!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void escribirArchivo()
    {

        String fileName = "Cifrado.txt";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            FileOutputStream fos2 = new FileOutputStream(file);

            String guardar2 = hacerCifrado();
            fos2.write(guardar2.getBytes());
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
     public String hacerCifrado()
     {
         String numeros = String.valueOf(etNivel.getText());
         int nivel = Integer.valueOf(numeros);
         int numCaracteres= (nivel-2)+nivel;
         String todo = String.valueOf(etTexto.getText());
         int numero = todo.length()%numCaracteres;
         while(numero!=0)
         {
             todo = todo+"%";
             numero = todo.length()%numCaracteres;
         }
         Cifrado miCifrado = new Cifrado(todo,nivel);

         return miCifrado.getCadenaCifrada();
     }

     /*
    private String readText(String input)
    {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text = new StringBuilder();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int contador = 1;
            while ((line=br.readLine())!=null)
            {

                if (contador==1)
                    Caracteres = line;
                else
                    Ascii=line;
                contador++;
            }
            br.close();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return text.toString();
    }
    */
}
