package com.example.caboc.ficherosenandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button botonEscribir;
    Button botonMostrar;
    TextView txtMostrar;
    EditText editText;
    TextView textViewPalabraLarga;

    String palabraLarga;

    String FILE_NAME="fichero.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonEscribir = (Button) findViewById(R.id.botonEscribir);
        botonMostrar = (Button) findViewById(R.id.botonMostrar);
        txtMostrar = (TextView) findViewById(R.id.textViewMostrar);
        editText = (EditText) findViewById(R.id.editText);

        botonMostrar.setOnClickListener(this);
        botonEscribir.setOnClickListener(this);

        textViewPalabraLarga = (TextView) findViewById(R.id.textViewPalabraLarga);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonEscribir:

                if(!editText.getText().toString().equals("")){

                    try {
                        FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                        String mensajeGuardar = new String(editText.getText().toString());
                        DataOutputStream dos = new DataOutputStream(fos);
                        dos.writeBytes(mensajeGuardar);
                        editText.setText("");
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Tienes que escribir algo", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.botonMostrar:

                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    DataInputStream dis = new DataInputStream(fis);

                    byte[] buff = new byte[1000];
                    dis.read(buff);

                    String textoMostrar = new String(buff);
                    txtMostrar.setText(textoMostrar);

                    textViewPalabraLarga.setText(conseguirPalabraMasLarga(textoMostrar));

                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    // este es el metodo que utilizo para ver la palabra mas larga
    private String conseguirPalabraMasLarga(String textoMostrar) {

        ArrayList<String> palabras = new ArrayList<String>();

        int palabraMasLarga = 0;
        String palabraLarga="";

        StringTokenizer st = new StringTokenizer(textoMostrar);

        // aqui separo por palabras
        while(st.hasMoreTokens()) {
            String palabra = st.nextToken();
            palabras.add(palabra);
        }

        // aqui busco la palabra mas larga
        for(int x = 0 ; x < palabras.size()-1; x++){

            if(palabras.get(x).length() > palabraMasLarga){
                palabraMasLarga = palabras.get(x).length();
                palabraLarga = palabras.get(x);

            }

        }

        Log.d("prueba",palabraLarga);
        return palabraLarga;

/// hasta aki
    }
}
