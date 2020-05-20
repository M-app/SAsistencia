package com.example.sasistencia.detalle_alumno;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sasistencia.detalle_alumno.events.DetalleAlumnoEvent;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FirebaseHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

public class DetalleAlumnoRepositoryImpl implements DetalleAlumnoRepository {

    FirebaseHelper helper;
    private String ID_GRADO = "";

    public DetalleAlumnoRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void setGrado(String grado) {
        ID_GRADO = grado;
    }

    @Override
    public void mostrarDatosAlumno(String claveAlumno) {
        helper.getGradoRef(ID_GRADO)
                .child(Integer.parseInt(claveAlumno)-1+"")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Alumno alumno = dataSnapshot.getValue(Alumno.class);
                        postEvent(DetalleAlumnoEvent.ALUMNO_OBTENIDO,alumno);
                    }
                    @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
    }

    @Override
    public void getCantidadAlumnos(String idGrado) {
        helper.getGradoRef(idGrado).orderByKey().limitToLast(1)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Alumno alumno = dataSnapshot.getValue(Alumno.class);
                        postEvent(DetalleAlumnoEvent.NUMERO_ALUMNOS,alumno.getClave());
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
    }

    @Override
    public void eliminarFalta(Alumno alumno, String fecha) {
        Log.v("INDICE",fecha);
        helper.getGradoRef(ID_GRADO)
                .child(alumno.getClave()-1+"")
                .child("inasistencias").
                child(fecha).removeValue();
    }

    @Override
    public void agregarAlumno(Alumno alumno) {
        helper.getGradoRef(ID_GRADO)
                .child(alumno.getClave()-1+"")
                .setValue(alumno);
        postEvent(DetalleAlumnoEvent.ALUMNO_AGREGADO,alumno);
    }

    @Override
    public void modificarAlumno(Alumno alumno) {
        helper.getGradoRef(ID_GRADO)
                .child(alumno.getClave()-1+"")
                .setValue(alumno);
        postEvent(DetalleAlumnoEvent.ALUMNO_MODIFICADO,alumno);
    }

    private void postEvent(int tipo, Object mensaje){
        DetalleAlumnoEvent detalleAlumnoEvent = new DetalleAlumnoEvent();
        detalleAlumnoEvent.setTipoEvento(tipo);
        if (mensaje != null){
            detalleAlumnoEvent.setMensaje(mensaje);
        }
        EventBus.getDefault().post(detalleAlumnoEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
