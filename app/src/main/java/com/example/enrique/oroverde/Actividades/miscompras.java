package com.example.enrique.oroverde.Actividades;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enrique.oroverde.R;
import com.example.enrique.oroverde.clases.Item2;
import com.example.enrique.oroverde.clases.ListViewAdapter2;

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

public class miscompras extends AppCompatActivity {

    String Datos = "";
    String folio = "";
    String fecha = "";
    Double total = 0.0;


    //Declaracion de los tipos de listas
    ListView ListaElementos = null;
    ArrayList<Item2> arrayItem = null;
    ListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscompras);

        //iniciamos las listas en el onCreate
        ListaElementos = (ListView) findViewById(R.id.listcompras);
        arrayItem= new ArrayList<>();
        ListaElementos.setAdapter(adapter);

        //metodo que muestra los datos
        verPreferencias();




    }

    private void verPreferencias() {
        try {
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String cuenta = prefs.getString("nombre", "").toString();
            Toast.makeText(miscompras.this, cuenta,Toast.LENGTH_LONG).show();

            String strAccion = "ListarCompraMovil";
            String strURL = "http://wshenequen.azurewebsites.net/UI/WebService.asmx/";
            String UrlWebService = strURL + strAccion + "?user=" + cuenta;

            new JSONTask().execute(UrlWebService);


        }catch (Exception c){
            //Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_LONG).show();
        }
    }

    //metodo para mostrar los datos en una lista
    private void mostrarDatos(){
        JSONArray ResultadosArray = null;
        try {
            ResultadosArray = new JSONArray(Datos);

            for(int i =0; i< 4; i++) {
                JSONObject object = ResultadosArray.getJSONObject(i);
                folio = object.getString("Folio");
                fecha = object.getString("Fecha_Compra");
                total = object.getDouble("Total");

                //Agrega los valores a un arrarlist
                arrayItem.add(new Item2(folio, fecha, total.toString()));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        //agrega los items a una lista
        adapter = new ListViewAdapter2(miscompras.this, arrayItem);
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
