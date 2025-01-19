package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class PerfilUtil {
    public static List<PerFil> leeDelArchivo(Context context) {
        List<PerFil> listaDatos = new ArrayList<>();
        File ruta = context.getFilesDir();
        String nombreArch = "archivo.tpo";

        try {
            FileInputStream leeArch = new FileInputStream(new File(ruta, nombreArch));
            ObjectInputStream streamArch = new ObjectInputStream(leeArch);
            listaDatos = (ArrayList<PerFil>) streamArch.readObject();
            streamArch.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDatos;
    }
}
