package com.example.sasistencia.login;

import androidx.annotation.NonNull;

import com.example.sasistencia.domain.FirebaseHelper;
import com.example.sasistencia.login.events.LoginEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper helper;
    private FirebaseAuth mAuth;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        mAuth = helper.getFirebaseAuth();
    }

    @Override
    public void loginUsuario(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // envía el rol del maestro autenticado
                            helper.getRolUsuarioAutenticado();
                        }else{
                            // enviar mensaje de error
                            postEvent(LoginEvent.ERROR_LOGIN,task.getException().getLocalizedMessage().toString());
                        }
                    }
                }
        );
    }

    @Override
    public void checkLogin() {
        if (helper.getCurrentUser() != null)
            helper.getRolUsuarioAutenticado();
    }

    private void postEvent(int tipo, String mensajeError){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setTipoEvento(tipo);
        if (mensajeError != null){
            loginEvent.setMensaje(mensajeError);
        }

        EventBus.getDefault().post(loginEvent);
    }

    private void postEvent(int tipo){
        postEvent(tipo,null);
    }
}