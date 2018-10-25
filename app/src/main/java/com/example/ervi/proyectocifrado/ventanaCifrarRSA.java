package com.example.ervi.proyectocifrado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ventanaCifrarRSA extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button buscarArchivo;
    Button buscarLLaves;
    Button Cifrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_cifrar_rs);

        buscarArchivo = (Button) findViewById(R.id.btnBuscarArchivoCifrarRSA);
        buscarLLaves = (Button) findViewById(R.id.btnBuscarLlave);
        Cifrar = (Button) findViewById(R.id.btnCifrarArchivoRSA);
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
