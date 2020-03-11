package com.example.sasistencia.domain;

import androidx.annotation.NonNull;

import com.example.sasistencia.login.events.LoginEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

public class FirebaseHelper {

    private DatabaseReference maestrosReference;
    private DatabaseReference gradosReference;
    private DatabaseReference reportesReference;
    private FirebaseAuth fAuth;

    private final static String SUSTITUTO_ARROBA = "-";
    private final static String SUSTITUTO_DIAGONAL = "-";
    private final static String SUSTITUTO_PUNTO = "_";

    private final static String RUTA_MAESTROS = "maestros";
    private final static String RUTA_GRADOS = "grados";
    private final static String RUTA_REPORTES = "reportes";

    private final static String ROL_MAESTRO = "maestro";
    private final static String ROL_ADMINISTRADOR = "administrador";

    private static class SingletonHolder{
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public FirebaseHelper(){
        fAuth = FirebaseAuth.getInstance();
        maestrosReference = FirebaseDatabase.getInstance().getReference(RUTA_MAESTROS);
        gradosReference = FirebaseDatabase.getInstance().getReference(RUTA_GRADOS);
        reportesReference = FirebaseDatabase.getInstance().getReference(RUTA_REPORTES);
    }

    public static FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    // ----------------- AUTENTICACIÓN --------------------


    public FirebaseAuth getFirebaseAuth() {
        return fAuth;
    }

    public FirebaseUser getCurrentUser(){
        return fAuth.getCurrentUser();
    }

    public String arreglarEmailParaFirebase(String email){
        return email
                .replace("@",SUSTITUTO_ARROBA)
                .replace(".",SUSTITUTO_PUNTO);
    }

    public String getEmailDesdeUsuarioAutenticado(){
        return getCurrentUser().getEmail();
    }


    //  ------------------- FIN AUTENTICACIÓN -----------------

    // -------------------- DATABASE MAESTROS ----------------

    public DatabaseReference getMaestrosRef(){
        return maestrosReference;
    }

    /**
     * inserta un email de un maestro en la base de datos
     * --> es utilizado por el método getRolUsuario
     * @return la referencia de la base de datos del email del maestro insertado
     */

    public DatabaseReference insertarMaestroAutenticadoInDatabase(){
        return getMaestrosRef().child(arreglarEmailParaFirebase(getEmailDesdeUsuarioAutenticado()));
    }

    /**
     * obtiene el rol del usuario autenticado
     * @return "docente" o "administrador"
     */

    public void getRolUsuarioAutenticado(){
        insertarMaestroAutenticadoInDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Maestro maestro = dataSnapshot.getValue(Maestro.class);
                postEvent(LoginEvent.EXITO_LOGIN,maestro.getRol());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     * ------------------------------------DATABASE GRADOS -----------------------------
     */

    public DatabaseReference getGradosRef(){
        return gradosReference;
    }

    public DatabaseReference getGradoRef(String nombreGrado){
        return gradosReference.child(nombreGrado);
    }


    private void postEvent(int tipo, String mensaje){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setTipoEvento(tipo);
        if (mensaje != null){
            loginEvent.setMensaje(mensaje);
        }

        EventBus.getDefault().post(loginEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }

    /**
     * -------------------------------------DATABASE REPORTES -----------------------------
     */

    public static String arreglarFechaFirebase(String fechaCorta){
        return fechaCorta.replace("/",SUSTITUTO_DIAGONAL);
    }

    public DatabaseReference getReportesReference(){
        return reportesReference;
    }

    /**
     * @getFechaActual --> SE PUEDEN CAMBIAR LOS REPORTES DE CUALQUIER GRADO, PERO SOLO DEL DÍA ACTUAL, LOS ANTERIORES NO.
     * @param nombreGrado el nombre del grado del reporte que se va a modificar
     * @return la referencia hacia el reporte específico creado
     */
    public DatabaseReference crearReporteRef(String nombreGrado ){
        return getReportesReference()
                .child(arreglarFechaFirebase(
                        FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)))
                .child(nombreGrado);
    }

}
