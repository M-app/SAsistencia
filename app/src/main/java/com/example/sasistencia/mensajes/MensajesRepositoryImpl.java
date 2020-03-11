package com.example.sasistencia.mensajes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FechaHelper;
import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.lista_alumnos.events.ListaAlumnosEvent;
import com.example.sasistencia.mensajes.events.MensajesEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MensajesRepositoryImpl implements MensajesRepository {

    FirebaseHelper helper;

    public MensajesRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void getGrados() {
        final List<String> grados = new ArrayList<>();

        helper.getGradosRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    grados.add(NombresGrados.getNombreGradoById(data.getKey()));
                }
                postEvent(MensajesEvent.GRADOS_OBTENIDOS,grados);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosPrimeroBasico() {
        helper.getGradoRef(NombresGrados.ID_PRIMERO_BASICO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.PRIMERO_BASICO,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosSegundoBasico() {
        helper.getGradoRef(NombresGrados.ID_SEGUNDO_BASICO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.SEGUNDO_BASICO,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosTerceroBasico() {
        helper.getGradoRef(NombresGrados.ID_TERCERO_BASICO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.TERCERO_BASICO,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosCuartoBiologicas() {
        helper.getGradoRef(NombresGrados.ID_CUARTO_BIOLOGICAS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.CUARTO_BIOLOGICAS,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosCuartoCCyLL() {
        helper.getGradoRef(NombresGrados.ID_CUARTO_BACH_CC_LL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.CUARTO_CCyLL,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosCuartDisenio() {
        helper.getGradoRef(NombresGrados.ID_CUARTO_BACH_DISENIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.CUARTO_DISENIO,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosCuartoCompu() {
        helper.getGradoRef(NombresGrados.ID_CUARTO_BACH_COMPU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.CUARTO_COMPU,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosQuintoCCyLL() {
        helper.getGradoRef(NombresGrados.ID_QUINTO_BACH_CCyLL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.QUINTO_CCyLL,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosQuintoBiologicas() {
        helper.getGradoRef(NombresGrados.ID_QUINTO_BACH_BIOLOGICAS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.QUINTO_BIOLOGICAS,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosQuintoCompu() {
        helper.getGradoRef(NombresGrados.ID_QUINTO_BACH_COMPU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.QUINTO_COMPU,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosCuartoMagis() {
        helper.getGradoRef(NombresGrados.ID_CUARTO_MAGISTERIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.CUARTO_MAGIS,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosQuintoMagis() {
        helper.getGradoRef(NombresGrados.ID_QUINTO_MAGISTERIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.QUINTO_MAGIS,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void getAlumnosSextoMagis() {
        helper.getGradoRef(NombresGrados.ID_SEXTO_MAGISTERIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alumno alumno = data.getValue(Alumno.class);
                    if (alumno.getInasistencias() != null) {
                        if (alumno.getInasistencias().contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                            alumnos.add(alumno);
                    }
                }
                postEvent(MensajesEvent.SEXTO_MAGIS,alumnos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void postEvent(int tipo, Object mensaje){
        MensajesEvent mensajesEvent = new MensajesEvent();
        mensajesEvent.setTipoEvento(tipo);
        if (mensaje != null){
            mensajesEvent.setMensaje(mensaje);
        }
        EventBus.getDefault().post(mensajesEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
