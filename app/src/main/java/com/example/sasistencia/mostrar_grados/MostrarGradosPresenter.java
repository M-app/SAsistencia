package com.example.sasistencia.mostrar_grados;

import java.util.ArrayList;

public interface MostrarGradosPresenter {
    void mostrarGrados();
    void mostrarListaGrados(ArrayList<String> listaGrados);
    void onStart();
    void onStop();
    void onDestroy();
}
