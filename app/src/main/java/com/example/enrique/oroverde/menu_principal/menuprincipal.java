package com.example.enrique.oroverde.menu_principal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.enrique.oroverde.Actividades.listaproductos;
import com.example.enrique.oroverde.R;
import com.example.enrique.oroverde.view.inicio_sesion.Login;

public class menuprincipal extends AppCompatActivity {

    Button productos;
    Button Comprar;
    Button miscompras;
    Button info;
    Button salir;
    Button ayuda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);

        productos = (Button) findViewById(R.id.btnproductos);
        Comprar = (Button) findViewById(R.id.btncomprar);
        miscompras = (Button) findViewById(R.id.btnmiscompras);
        info = (Button) findViewById(R.id.btninfo);
        salir = (Button) findViewById(R.id.btnsalir);
        ayuda = (Button) findViewById(R.id.btnayuda);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(menuprincipal.this);
                                builder.setMessage("Desea Salir?")
                                                .setTitle("Advertencia")
                                                .setCancelable(false)
                                                .setPositiveButton("Si",
                                                        new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                        // metodo que se debe implementar
                                                                                //envia al otro activity login
                                                                                        Intent intent = new Intent(menuprincipal.this, Login.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                            })
                                                .setNegativeButton("No",
                                                        new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                        dialog.cancel();
                                                                    }
                                                            });
                AlertDialog alert = builder.create();
                               alert.show();
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //envia al otro activity "listaproductos"
                Intent i = new Intent(menuprincipal.this, listaproductos.class);
                startActivity(i);
                finish();


            }
        });
    }


}
