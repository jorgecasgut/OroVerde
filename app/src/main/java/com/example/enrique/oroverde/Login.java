package com.example.enrique.oroverde;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    private Button btningresar;
    private EditText txtuser;
    private EditText txtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser = (EditText) findViewById(R.id.txtuser);
        txtpass = (EditText) findViewById(R.id.txtpass);
        btningresar = (Button) findViewById(R.id.btnentrar);

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUser = txtuser.getText().toString();
                String strPass = txtpass.getText().toString();

                //Accion que esta en la URL.
                String strAccion = "LoginUsuarioMovil";
                String strURL = "http://wshenequen.azurewebsites.net/UI/WebService.asmx/";
                String UrlWebService = strURL + strAccion + "?user=" + strUser + "&password=" + strPass;
                new JSONTask().execute(UrlWebService);
            }
        });

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

                        Intent i = new Intent(Login.this, Barracarga.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Error, Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){}



            }
            catch (Throwable t){
                t.printStackTrace();
            }
        }
    }
}
