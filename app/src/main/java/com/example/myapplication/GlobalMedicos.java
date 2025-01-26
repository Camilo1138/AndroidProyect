
package com.example.myapplication;

import java.util.ArrayList;

public class GlobalMedicos {
    public static ArrayList<Medico> listaMedicos = new ArrayList<>();

    static {
        // Médicos predefinidos
        listaMedicos.add(new Medico("Carolina Cáceres", "Odontología", "0991234567"));
        listaMedicos.add(new Medico("Marco Echeverría", "Ginecología", "0987654321"));
        listaMedicos.add(new Medico("Luis Paredes", "Pediatría", "0971239876"));
    }
}