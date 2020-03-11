package com.example.sasistencia.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.sasistencia.AdminActivity;
import com.example.sasistencia.seleccionar_grado.SeleccionarGradoActivity;
import com.example.sasistencia.R;
import com.example.sasistencia.registro.RegistrationActivity;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements LoginView {

    //Views
    EditText etxtEmail,etxtPass;
    Button btnIniciarSesion,btnRegistrarse;
    ProgressBar progressBarLogin;
    LinearLayout viewParentLogin;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removerTitleBar();
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterImpl(this);

        iniciarView();
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manejarLogin();
            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityRegistro();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.onResumen();
        loginPresenter.checkLogin();
    }

    @Override
    public void abrirActivityAdmin(){
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    private void iniciarView(){
        viewParentLogin = findViewById(R.id.view_parent_login);
        etxtEmail = findViewById(R.id.etxt_email_login);
        etxtPass = findViewById(R.id.etxt_pass_login);
        btnIniciarSesion = findViewById(R.id.btn_iniciarSesion);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        progressBarLogin = findViewById(R.id.progressBarLogin);
    }

    private void setInputs(boolean enabled){
        etxtEmail.setEnabled(enabled);
        etxtPass.setEnabled(enabled);
        btnIniciarSesion.setEnabled(enabled);
        btnRegistrarse.setEnabled(enabled);
    }

    @Override
    public void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
    }

    @Override
    public void habilitarInputs() {
        setInputs(true);
    }

    @Override
    public void desabilitarInputs() {
        setInputs(false);
    }

    @Override
    public void mostrarProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    /**
     * validar login en el presenter
     */
    @Override
    public void manejarLogin() {
        loginPresenter.validarLogin(etxtEmail.getText().toString(),etxtPass.getText().toString());
    }

    @Override
    public void abrirActivityRegistro() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    @Override
    public void mostrarError(String error) {
        // mostrar error
        ocultarProgressBar();
        habilitarInputs();
        Snackbar.make(viewParentLogin,error,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void abrirActivityDocente() {
        startActivity(new Intent(LoginActivity.this, SeleccionarGradoActivity.class));
    }
}
