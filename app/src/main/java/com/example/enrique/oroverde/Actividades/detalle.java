package com.example.enrique.oroverde.Actividades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enrique.oroverde.R;

public class detalle extends AppCompatActivity {

    TextView titu,descri;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        titu = (TextView)findViewById(R.id.tvTitulo2);
        descri = (TextView)findViewById(R.id.txtprecio);
        img = (ImageView)findViewById(R.id.lvImagen2);


        Bundle extras = getIntent().getExtras();

        TextView nombre = (TextView) findViewById(R.id.textViewNombre);

        if(extras!=null){
            String datoTitulo = (String)extras.get("titulo");
            String datoDescri = (String)extras.get("descripcion");
            String imagen = extras.get("imagen").toString();
            byte[] decodeString = Base64.decode(imagen,Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
            img.setImageBitmap(decoded);
            titu.setText(datoTitulo);
            descri.setText(datoDescri);
            nombre.setText(datoTitulo);
        }

    }
}
