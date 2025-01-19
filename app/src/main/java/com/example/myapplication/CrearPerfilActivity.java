package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CrearPerfilActivity extends AppCompatActivity {
    private List<PerFil> listaDatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);
    }

    public void guardar(View view) {
        GuardaEnLista();

        File ruta = getApplicationContext().getFilesDir();
        String nombreArch = "archivo.tpo";

        try {
            // Verifica que la ruta y el nombre del archivo sean correctos
            File file = new File(ruta, nombreArch);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream escribirArch = new FileOutputStream(file);
            ObjectOutputStream streamArch = new ObjectOutputStream(escribirArch);
            streamArch.writeObject(listaDatos);
            streamArch.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Muestra un mensaje más detallado sobre el error
            Toast.makeText(this, "Error al guardar el perfil: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        LeeDelArchivo();

        if (listaDatos == null) {
            Toast.makeText(this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!listaDatos.isEmpty()) {
            PerFil ultimoPerfil = listaDatos.get(listaDatos.size() - 1);
            String mensaje = "Perfil agregado: \nNombre: " + ultimoPerfil.getNombre() +
                    "\nRelación: " + ultimoPerfil.getRelacion() +
                    "\nEmail: " + ultimoPerfil.getEmail();
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No se encontraron perfiles en la lista", Toast.LENGTH_SHORT).show();
        }
    }

    public void GuardaEnLista() {
        EditText txtNombre = findViewById(R.id.etNombre);
        EditText txtRelacion = findViewById(R.id.etrelacion);
        EditText txtEmail = findViewById(R.id.etEmail);

        PerFil nuevoPerfil = new PerFil(
                txtNombre.getText().toString(),
                txtRelacion.getText().toString(),
                txtEmail.getText().toString()
        );

        listaDatos.add(nuevoPerfil);

        Toast.makeText(this, "Agregado a la lista", Toast.LENGTH_SHORT).show();

        txtNombre.setText("");
        txtRelacion.setText("");
        txtEmail.setText("");
    }

    public void LeeDelArchivo() {
        File ruta = getApplicationContext().getFilesDir();
        String nombreArch = "archivo.tpo";

        listaDatos = new ArrayList<>();

        try {
            FileInputStream leeArch = new FileInputStream(new File(ruta, nombreArch));
            ObjectInputStream streamArch = new ObjectInputStream(leeArch);
            listaDatos = (ArrayList<PerFil>) streamArch.readObject();
            streamArch.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    public List<PerFil> getListaDatos() {
        return listaDatos;
    }
}
