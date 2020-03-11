package com.example.sasistencia.lista_alumnos;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.lista_alumnos.events.ListaAlumnosEvent;
import com.example.sasistencia.tomar_asistencia.events.TomarAsistenciaEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ListaAlumnosRepositoryImpl implements ListaAlumnosRepository {

    FirebaseHelper helper;

    public ListaAlumnosRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void mostrarAlumnos(String nombreGrado) {
        helper.getGradoRef(nombreGrado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> listaAlumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    listaAlumnos.add(data.getValue(Alumno.class));
                }
                postEvent(ListaAlumnosEvent.LISTA_ALUMNOS_OBTENIDA,listaAlumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void eliminarAlumno(String nombreGrado, String claveAlumno) {
        helper.getGradoRef(nombreGrado).child(Integer.parseInt(claveAlumno)-1+"").removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        postEvent(ListaAlumnosEvent.ALUMNO_ELIMINADO);
                    }
                });
    }

    private void postEvent(int tipo, Object mensaje){
        ListaAlumnosEvent listaAlumnosEvent = new ListaAlumnosEvent();
        listaAlumnosEvent.setTipoEvento(tipo);
        if (mensaje != null){
            listaAlumnosEvent.setMensaje(mensaje);
        }
        EventBus.getDefault().post(listaAlumnosEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
