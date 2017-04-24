package com.example.enrique.oroverde.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enrique.oroverde.R;

public class detalle extends AppCompatActivity {

    String precio2;
    EditText cant;
    TextView titu,descri, idpro;
    ImageView img;
    Button comprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        //inicializa los elementos del layout
        idpro = (TextView)findViewById(R.id.txtid);
        titu = (TextView)findViewById(R.id.tvTitulo2);
        descri = (TextView)findViewById(R.id.txtprecio);
        img = (ImageView)findViewById(R.id.lvImagen2);
        cant =(EditText)findViewById(R.id.cbcantidades);
        comprar = (Button) findViewById(R.id.btncomprar);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(detalle.this, compras.class);
                i.putExtra("idpf", idpro.getText().toString());
                i.putExtra("precio", descri.getText());
                i.putExtra("cantidad",cant.getText().toString());
                startActivity(i);
                Toast.makeText(detalle.this, "Comprando...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Recibe los extras enviados desde la lista
        Bundle extras = getIntent().getExtras();

        TextView nombre = (TextView) findViewById(R.id.textViewNombre);

        if(extras!=null){
            //asigna los valores a variables
            String datoid = (String)extras.get("id_pf");
            String datoTitulo = (String)extras.get("titulo");
            String datoDescri = (String)extras.get("descripcion");
            String imagen = extras.get("imagen").toString();

            //convierte bytes en imagen
            byte[] decodeString = Base64.decode(imagen,Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

            //asigna los valores a elementos del layout
            idpro.setText(datoid);
            img.setImageBitmap(decoded);
            titu.setText(datoTitulo);
            descri.setText(datoDescri);
            nombre.setText(datoTitulo);
        }

    }
}
