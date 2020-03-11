package com.example.sasistencia.tomar_asistencia;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.Maestro;
import com.example.sasistencia.Reporte;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.tomar_asistencia.events.TomarAsistenciaEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class TomarAsistenciaRepositoryImpl implements TomarAsistenciaRepository {

    FirebaseHelper helper;

    public TomarAsistenciaRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
    }

    /**
     * obtiene la lista de los objetos mAlumnos segun la referencia
     * de firebase /grados/nombreGrado
     * @param nombreGrado
     */
    @Override
    public void getListaAlumnos(String nombreGrado) {
        final ArrayList<Alumno> listaAlumnos = new ArrayList<>();
        helper.getGradoRef(nombreGrado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    listaAlumnos.add(data.getValue(Alumno.class));
                }
                postEvent(TomarAsistenciaEvent.LISTA_ALUMNOS_OBTENIDA,listaAlumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    /**
     * obtiene el objeto del maestro que está logueado en la aplicación
     */
    @Override
    public void getMaestroActual() {
        helper.insertarMaestroAutenticadoInDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Maestro maestro  = dataSnapshot.getValue(Maestro.class);
                postEvent(TomarAsistenciaEvent.MAESTRO_OBTENIDO,maestro);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     * en un principio este método crea el objeto reporte en la bd
     * y tiene el atributo "asistenciaTomara = false"
     * luego cuando se termina de tomar la asistencia se vuelve a llamar
     * y ya modifica el atributo mencionado a true
     * @param reporte
     */
    @Override
    public void cambiarStatusAsistencia(Reporte reporte) {
        if (reporte.isAsistenciaTomada()){
            postEvent(TomarAsistenciaEvent.ASISTENCIA_TOMADA, reporte);
        }
        helper.crearReporteRef(reporte.getNombreGrado()).setValue(reporte);
    }


    /**
     * modifica la lista de asistencias de cada alumno
     * ya sea para agregar una o para eliminar otra
     * @param nombreGrado
     * @param alumno
     */
    @Override
    public void modificarAsistenciaAlumno(String nombreGrado, Alumno alumno) {
        Log.v("EJECUTANTO","MÉTODO ASISTENCIA");
        helper.getGradoRef(nombreGrado).child((alumno.getClave()-1)+"").setValue(alumno);
        postEvent(TomarAsistenciaEvent.ASISTENCIA_ALUMNO_MODIFICADA, alumno);
    }

    /**
     * VERIFICA SI LA RUTA AL REPORTE YA CONTIENE DATOS
     * SI NO --> SE LOS INGRESA
     * @param
     */
    @Override
    public void ingresarReporte(final Reporte reporte){
        helper.crearReporteRef(reporte.getNombreGrado()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Reporte rep = dataSnapshot.getValue(Reporte.class);
                if (rep == null){
                    helper.crearReporteRef(reporte.getNombreGrado()).setValue(reporte);
                }
                postEvent(TomarAsistenciaEvent.REF_REPORTE_CREADA);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void postEvent(int tipo, Object mensaje){
        TomarAsistenciaEvent tomarAsistenciaEvent = new TomarAsistenciaEvent();
        tomarAsistenciaEvent.setTipoEvento(tipo);
        if (mensaje != null){
            tomarAsistenciaEvent.setMensaje(mensaje);
        }
        EventBus.getDefault().post(tomarAsistenciaEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
