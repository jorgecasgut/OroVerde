package com.example.enrique.oroverde.Actividades;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enrique.oroverde.R;
import com.example.enrique.oroverde.clases.Item;
import com.example.enrique.oroverde.clases.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class listaproductos extends AppCompatActivity {

    int cantidad = 3, mostrados = 0;
    String Datos = "";
    String nombre ="";
    String imagen ="";
    Double precio = 0.0;


    ListView ListaElementos = null;
    ArrayList<Item> arrayItem = null;
    ListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaproductos);

        ListaElementos = (ListView) findViewById(R.id.listNombres);
        arrayItem= new ArrayList<>();
        ListaElementos.setAdapter(adapter);
        mostrarDatos();

        ListaElementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tit = (TextView) view.findViewById(R.id.tvTitulo);
                TextView des = (TextView) view.findViewById(R.id.tvContenido);
                ImageView ima = (ImageView)view.findViewById(R.id.lvImagen);
                Intent c = new Intent(listaproductos.this, detalle.class);
                //Recive los datos como el nombre y la descripción.
                c.putExtra("imagen", arrayItem.get(i).getImagen());
                c.putExtra("titulo", tit.getText());
                c.putExtra("descripcion", des.getText());
                startActivity(c);
                Toast.makeText(listaproductos.this, "Informacion del contacto", Toast.LENGTH_SHORT).show();
            }
        });

        //acceso al web service
        String strAccion = "listarpf";
        String strUrl = "http://wshenequen.azurewebsites.net/UI/WebService.asmx/";
        String UrlWebService = strUrl + strAccion;
        new JSONTask().execute(UrlWebService);
    }


    //metodo para mostrar los datos en una lista
    private void mostrarDatos(){
        JSONArray ResultadosArray = null;
        try {
            ResultadosArray = new JSONArray(Datos);
            for(int i = mostrados; i < cantidad;i++) {
                mostrados++;
                JSONObject object = ResultadosArray.getJSONObject(i);
                nombre = object.getString("Nombre");
                precio = object.getDouble("Precio_venta");
                imagen = object.getString("Imagen");



                arrayItem.add(new Item(nombre, precio.toString(), imagen));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        adapter = new ListViewAdapter(listaproductos.this, arrayItem);
        ListaElementos.setAdapter(adapter);
    }

    //metodo para traer los datos del web service como json
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... Parametros) {
            HttpURLConnection conexion = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(Parametros[0]);
                conexion = (HttpURLConnection) url.openConnection();
                conexion.connect();
                InputStream stream = conexion.getInputStream();
                reader = new BufferedReader((new InputStreamReader(stream)));
                StringBuffer buffer = new StringBuffer();
                String Line = "";
                while ((Line = reader.readLine()) != null) {
                    buffer.append(Line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    conexion.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String Resultado) {
            super.onPostExecute(Resultado);

            try {
                Datos = Resultado;
                mostrarDatos();

            } catch (Exception e) {
                Log.e("hola", e.getMessage());
            }
        }
    }


}
