package com.example.sasistencia.registro;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.Maestro;
import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.registro.events.RegistroEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.greenrobot.eventbus.EventBus;

public class RegistroRepositoryImpl implements RegistroRepository {

    FirebaseHelper helper;
    FirebaseAuth fAuth;
    DatabaseReference maestroRef;

    public RegistroRepositoryImpl(){
        this.helper = FirebaseHelper.getInstance();
        fAuth = helper.getFirebaseAuth();
        maestroRef = helper.getMaestrosRef();
    }

    @Override
    public void manejarRegistro(final String email, final String nombre, final String pass) {
        fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Crear nuevo maestro
                    Maestro maestro = new Maestro(email,nombre,"activo","docente");
                    // Ingresar maestro en la base de datos
                    maestroRef.child(helper.arreglarEmailParaFirebase(email)).setValue(maestro);
                    // evento logueo exitoso
                    postEvent(RegistroEvent.EXITO_REGISTRO);
                }else{
                    // enviar evento error
                    postEvent(RegistroEvent.ERROR_REGISTRO,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    private void postEvent(int tipo, String mensaje){
        RegistroEvent registroEvent = new RegistroEvent();
        registroEvent.setTipoEvento(tipo);
        if (mensaje != null){
            registroEvent.setMensaje(mensaje);
        }

        EventBus.getDefault().post(registroEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}
