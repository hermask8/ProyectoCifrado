package com.example.ervi.proyectocifrado;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuCifrado extends AppCompatActivity {


    Button ventanCifrar;
    Button ventanaDescifrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cifrado);

        ventanaDescifrar = (Button) findViewById(R.id.btnVentanaDescifrar);
        ventanCifrar = (Button) findViewById(R.id.btnCifrar);
    }

    public void CambioVentanaCifrar(View view)
    {
        Intent ventanaCifrar2 = new Intent(this,CifradoVentana.class);
        startActivity(ventanaCifrar2);
    }

    public void CambioVentanaDescifrar(View view)
    {
        Intent ventanaDescifrar2 = new Intent(this,DescifrarVentana.class);
        startActivity(ventanaDescifrar2);
    }
}
