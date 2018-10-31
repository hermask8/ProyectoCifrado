package com.example.ervi.proyectocifrado;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button menuSdes;
    Button menuRSA2;
    Button Zig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        menuSdes = (Button) findViewById(R.id.btnCifrarZig_Zag);
        menuRSA2 = (Button) findViewById(R.id.btnCifradoRSA2);
        Zig = (Button) findViewById(R.id.btnCifradoSdes2);
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

    public void ventanaCifradoZig(View view)
    {
        Intent ventanaCif = new Intent(this,MenuCifrado.class);
        startActivity(ventanaCif);
    }

    public void ventanaCifradoSdes(View view)
    {
        Intent ventanaCif2 = new Intent(this,MenuSdes.class);
        startActivity(ventanaCif2);
    }

    public void ventanaCifradoRsa(View view)
    {
        Intent ventanaCif2 = new Intent(this,menuRSA.class );
        startActivity(ventanaCif2);
    }
}
