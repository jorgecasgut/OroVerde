package com.example.enrique.oroverde.Actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class compras extends AppCompatActivity {

    Random generador;

    String idpro, datoid="", datoprecio="", datocant="", e="vendido";
    Double calcular, total1,total2, cantdato, preciodato;
    Integer numero, idc=125;
    EditText folio, fecha, cantidad, iva, idp, subtotal, usuario, total;
    Button compras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        generador = new Random();

        folio = (EditText)findViewById(R.id.cbfolio);
        fecha = (EditText)findViewById(R.id.cbfecha);
        cantidad = (EditText)findViewById(R.id.cbcantidad);
        iva = (EditText)findViewById(R.id.cbiva);
        idp = (EditText)findViewById(R.id.cbidp);
        subtotal = (EditText)findViewById(R.id.cbsubt);
        usuario = (EditText)findViewById(R.id.cbuser);
        total = (EditText)findViewById(R.id.cbtotal);
        compras =(Button) findViewById(R.id.btncomprartodo);
        llenar();


        compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Accion que esta en la URL.
                String strAccion = "InsertarCompraMovil";
                String strURL = "http://wshenequen.azurewebsites.net/UI/WebService.asmx/";
                String UrlWebService = strURL + strAccion +
                        "?F=" + folio.getText().toString() +
                        "&Cant=" + cantidad.getText().toString() +
                        "&Fecha=" + fecha.getText().toString() +
                        "&T=" + total.getText().toString() +
                        "&SubT=" + subtotal.getText().toString() +
                        "&IVA=" + iva.getText().toString() +
                        "&IDP=" + idp.getText().toString() +
                        "&U=" + usuario.getText().toString() +
                        "&IDC=" + idc.toString() +
                        "&E=" + e.trim();

                new JSONTask().execute(UrlWebService);
            }
        });

    }

    private void llenar(){
        try {
            Bundle extras = getIntent().getExtras();
            if(extras!= null) {
                datoid = extras.getString("idpf");
                datocant = extras.getString("cantidad");
                datoprecio = extras.getString("precio");
            }


            numero = Math.abs(generador.nextInt() % 10000);
            folio.setText(numero.toString());
            fecha.setText(getDatePhone());
            verPreferencias();
            cantidad.setText(datocant);
            subtotal.setText(datoprecio);
            cantdato = Double.parseDouble(datocant);
            preciodato = Double.parseDouble(datoprecio);
            idp.setText(datoid);


            calcular = preciodato * .16;
            total1 = calcular + preciodato;
            total2 = total1 * cantdato;

            iva.setText(calcular.toString());
            total.setText(total2.toString());


        }
        catch (Exception ex){
            Log.e("hola", ex.getMessage());
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
                    int dato = 0;

                    ResultadosArray=new JSONArray(Resultado);
                    for(int i = 0; i < ResultadosArray.length(); i++) {
                        JSONObject objetos = ResultadosArray.getJSONObject(i);
                        dato = objetos.getInt("Mensaje");
                    }
                    if(dato == 1) {

                        Intent i = new Intent(compras.this, pagare.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(compras.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){}



            }
            catch (Throwable t){
                t.printStackTrace();
            }
        }
    }

    private void verPreferencias() {
        try {
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String cuenta = prefs.getString("nombre", "").toString();
            usuario.setText(cuenta);
            Toast.makeText(compras.this, cuenta,Toast.LENGTH_LONG).show();


        }catch (Exception c){
            //Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_LONG).show();
        }
    }

    private String getDatePhone(){

        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String formatteDate = df.format(date);

        return formatteDate;
    }
}
