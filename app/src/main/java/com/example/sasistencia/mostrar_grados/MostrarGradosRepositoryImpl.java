package com.example.sasistencia.mostrar_grados;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.mostrar_grados.events.MostrarGradoEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MostrarGradosRepositoryImpl implements MostrarGradosRepository {

    FirebaseHelper helper;

    public MostrarGradosRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void mostrarGrados() {
        final List<String> grados = new ArrayList<>();
        helper.getGradosRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    grados.add(NombresGrados.getNombreGradoById(data.getKey()));
                }
                postEvent(MostrarGradoEvent.GRADOS_RECUPERADOS,grados);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void postEvent(int tipo, Object mensaje){
        MostrarGradoEvent mostrarGradoEvent = new MostrarGradoEvent();
        mostrarGradoEvent.setTipoEvento(tipo);
        if (mensaje != null){
            mostrarGradoEvent.setMensaje(mensaje);
        }

        EventBus.getDefault().post(mostrarGradoEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
