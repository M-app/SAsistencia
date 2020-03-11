package com.example.sasistencia.seleccionar_grado;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.Maestro;
import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.seleccionar_grado.events.SeleccionarGradoEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

public class SeleccionarGradoRepositoryImpl implements SeleccionarGradoRepository {

    FirebaseHelper helper;

    public SeleccionarGradoRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void obtenerNombresMaestro() {
        helper.insertarMaestroAutenticadoInDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Maestro maestro  = dataSnapshot.getValue(Maestro.class);
                postEvent(SeleccionarGradoEvent.NOMBRE_MAESTRO_ENCONTRADO,maestro.getNombre());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void cerrarSesion() {
        helper.getFirebaseAuth().signOut();
        postEvent(SeleccionarGradoEvent.SESION_CERRADA);
    }

    private void postEvent(int tipo, String mensaje){
        SeleccionarGradoEvent seleccionarGradoEvent = new SeleccionarGradoEvent();
        seleccionarGradoEvent.setTipoEvento(tipo);
        if (mensaje != null){
            seleccionarGradoEvent.setMensaje(mensaje);
        }
        EventBus.getDefault().post(seleccionarGradoEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
