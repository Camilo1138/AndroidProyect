package com.example.myapplication;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilManager {
    private static final String FILE_NAME = "perfiles.txt";

    // Guarda un perfil en un archivo
    public static void guardarPerfil(Context context, Perfil perfil) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter writer = new BufferedWriter(osw)) {

            writer.write(perfil.toJson());
            writer.newLine(); // Separar cada perfil en una nueva línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carga todos los perfiles desde un archivo
    public static List<Perfil> cargarPerfiles(Context context) {
        List<Perfil> perfiles = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                Perfil perfil = Perfil.fromJson(line);
                perfiles.add(perfil);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return perfiles;
    }
}