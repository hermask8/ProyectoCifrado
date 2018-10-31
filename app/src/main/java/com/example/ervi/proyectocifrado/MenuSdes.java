package com.example.ervi.proyectocifrado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuSdes extends AppCompatActivity {

    Button Descifrar;
    Button Cifrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sdes);

        Cifrar = (Button) findViewById(R.id.btnCIfradoSdes);
        Cifrar = (Button) findViewById(R.id.btnDescifradoVentana);
    }

    public void ventanaCifrado(View view)
    {
        Intent ventanaCif = new Intent(this,CIfradoSdes.class);
        startActivity(ventanaCif);
    }

    public void ventanaDescifrado(View view)
    {
        Intent ventanaCif2 = new Intent(this,DescifradoSdes.class);
        startActivity(ventanaCif2);
    }
}
