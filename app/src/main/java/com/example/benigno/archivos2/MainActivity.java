package com.example.benigno.archivos2;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText nombre, domicilio, edad, sueldo;
    final String nomArch="info.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.nom);
        domicilio = findViewById(R.id.dom);
        edad = findViewById(R.id.edad);
        sueldo = findViewById(R.id.sue);
    }

    private boolean validarSD(){
        String resp = Environment.getExternalStorageState();
        if(resp.compareTo(Environment.MEDIA_MOUNTED)==0){
            return true;
        }
        return false;
    }

    public void guardarArchivo(View v){
        String data = nombre.getText().toString()+"&"+domicilio.getText().toString()+"&"+
                edad.getText().toString()+"&"+sueldo.getText().toString();

        try{
            OutputStreamWriter archivo = new OutputStreamWriter(
                    openFileOutput(nomArch,MODE_PRIVATE));

            archivo.write(data);
            archivo.close();
            limpiar();
            mensaje("Se capturó datos! EXITO!");
        }catch (Exception e){
            mensaje("ERROR: "+e.getMessage());
        }
    }

    public void guardarArchivoSD(View v){
        String data = nombre.getText().toString()+"&"+domicilio.getText().toString()+"&"+
                edad.getText().toString()+"&"+sueldo.getText().toString();
        if(!validarSD()){
            mensaje("Ups! no tienes memoria SD insertada o esta en modo SOLO LECTURA");
            return;
        }

        try{
            File rutaSD = Environment.getExternalStorageDirectory();
            File datosArchivo = new File(rutaSD.getAbsolutePath(),"archivo.txt");
            OutputStreamWriter archivo = new OutputStreamWriter(
                    new FileOutputStream(datosArchivo));

            archivo.write(data);
            archivo.close();
            limpiar();
            mensaje("Se capturó datos en SD!");
        }catch (Exception e){
            mensaje("ERROR: "+e.getMessage());
        }
    }

    public void abrirArchivoSD(View v){
        if(!validarSD()){
            mensaje("Ups! no tienes memoria SD insertada");
            return;
        }

        try{
            File rutaSD = Environment.getExternalStorageDirectory();
            File datosArchivo = new File(rutaSD.getAbsolutePath(),"archivo.txt");

            BufferedReader archivo = new BufferedReader(new InputStreamReader(
                                                                new FileInputStream(datosArchivo)));

            String data = archivo.readLine();
            String[] vector = data.split("&");

            archivo.close();
            nombre.setText(vector[0]);
            domicilio.setText(vector[1]);
            edad.setText(vector[2]);
            sueldo.setText(vector[3]);
        }catch (Exception e){
            mensaje("Error: "+e.getMessage());
        }
    }

    public void abrirArchivo(View v){
        try{
            BufferedReader archivo = new BufferedReader(new InputStreamReader(openFileInput(nomArch)));

            String data = archivo.readLine();
            String[] vector = data.split("&");

            archivo.close();
            nombre.setText(vector[0]);
            domicilio.setText(vector[1]);
            edad.setText(vector[2]);
            sueldo.setText(vector[3]);
        }catch (Exception e){
            mensaje("Error: "+e.getMessage());
        }
    }

    public void limpiarCampos(View v){
        limpiar();
    }

    private void limpiar(){
        nombre.setText("");
        domicilio.setText("");
        edad.setText("");
        sueldo.setText("");
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}
