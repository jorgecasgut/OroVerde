package com.example.enrique.oroverde.usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enrique.oroverde.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class usuario extends AppCompatActivity {

    private static final String TAG = "Toast";

    public TextView tvnombre;
    public TextView tvapelido;
    public TextView tvdireccion;
    public TextView tvcorreo;
    public TextView tvtelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        tvnombre = (TextView) findViewById(R.id.txtnombre);
        tvapelido = (TextView) findViewById(R.id.txtapellido);
        tvdireccion = (TextView) findViewById(R.id.txtdireccion);
        tvcorreo = (TextView) findViewById(R.id.txtcorreo);
        tvtelefono = (TextView) findViewById(R.id.txttelefono);

        verPreferencias();
    }



    private void verPreferencias() {
        try {
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String cuenta = prefs.getString("nombre", "").toString();
            Toast.makeText(usuario.this, cuenta,Toast.LENGTH_LONG).show();

            String strAccion = "ListarUsuarioMolvil";
            String strURL = "http://wshenequen.azurewebsites.net/UI/WebService.asmx/";
            String UrlWebService = strURL + strAccion + "?user=" + cuenta;

            new JSONTask().execute(UrlWebService);


        }catch (Exception c){
            //Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_LONG).show();
        }
    }

    public class JSONTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... Parametros){
            HttpURLConnection conexion = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(Parametros[0]);
                conexion = (HttpURLConnection)url.openConnection();
                conexion.connect();
                InputStream stream = conexion.getInputStream();
                reader = new BufferedReader((new InputStreamReader(stream)));
                StringBuffer buffer = new StringBuffer();
                String Line = "";
                while ((Line = reader.readLine()) != null){
                    buffer.append(Line);
                }
                return buffer.toString();
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if(conexion != null){
                    conexion.disconnect();
                }
                try{
                    if(reader!= null){
                        reader.close();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String Resultado){
            super.onPostExecute(Resultado);
            try{
                JSONArray ResultadosArray = null;
                try{
                    String dato1 = "";
                    String dato2 = "";
                    String dato3 = "";
                    String dato4 = "";
                    String dato5 = "";

                    ResultadosArray=new JSONArray(Resultado);
                    for(int i = 0; i < ResultadosArray.length(); i++) {
                        JSONObject objetos = ResultadosArray.getJSONObject(i);
                        dato1 = objetos.getString("Nombre");
                        dato2 = objetos.getString("Apellidos");
                        dato3 = objetos.getString("Direccion");
                        dato4 = objetos.getString("Correo");
                        dato5 = objetos.getString("Telefono");

                        tvnombre.setText(dato1);
                        tvapelido.setText(dato2);
                        tvdireccion.setText(dato3);
                        tvcorreo.setText(dato4);
                        tvtelefono.setText(dato5);
                    }

                }catch (Exception e){}
            }
            catch (Throwable t){
                t.printStackTrace();
            }
        }
    }
}
