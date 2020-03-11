package com.example.sasistencia.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.sasistencia.R;
import com.example.sasistencia.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegistrationActivity extends AppCompatActivity implements RegistroView {

    // views
    EditText etxtNombre;
    EditText etxtEmail;
    EditText etxPass;
    Button btnEmpezar, btnLoguearse;
    LinearLayout parentRegistro;
    ProgressBar progressBar;

    // firebase
    private FirebaseAuth mAuth;
    DatabaseReference maestrosRef;

    RegistroPresenter registroPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removerTitleBar();
        setContentView(R.layout.activity_registration);

        iniciarViews();

        registroPresenter = new RegistroPresenterImpl(this);

        mAuth = FirebaseAuth.getInstance();

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registroPresenter.validarInputs(etxtEmail.getText().toString(),
                                                        etxtNombre.getText().toString(),
                                                        etxPass.getText().toString())){
                    registroPresenter.manejarRegistro(etxtEmail.getText().toString(),
                            etxtNombre.getText().toString(),
                            etxPass.getText().toString());
                }else{
                    mostrarError("Debe llenar todos los campos");
                }
            }
        });

        btnLoguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityLogin();
            }
        });
    }
/*
    private void crearCuenta(final String nombre, final String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Un Nombre",Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Un Correo Electrónico",Toast.LENGTH_LONG);
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Una Contraseña", Toast.LENGTH_LONG);
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Maestro maestro = new Maestro(email,nombre,"activo","docente");
                            String emailArreglado = email.replace("@","-")
                                    .replace(".","_");
                            maestrosRef = FirebaseDatabase.getInstance().getReference()
                                    .child("maestros").child(emailArreglado);
                            maestrosRef.setValue(maestro);
                            Toast.makeText(getApplicationContext(), "Registro Correcto", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Error " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
*/
    private void abrirActivityLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void iniciarViews(){
        parentRegistro = findViewById(R.id.registro_activity_parent);
        etxtNombre = findViewById(R.id.etxt_nombre);
        etxtEmail = findViewById(R.id.etxt_email);
        etxPass = findViewById(R.id.etxt_pass);
        btnEmpezar = findViewById(R.id.btn_empezar);
        btnLoguearse = findViewById(R.id.btn_loguearse);
        progressBar = findViewById(R.id.progressBar);
    }


    public void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
    }

    private void setInputs(boolean enable){
        etxtNombre.setEnabled(enable);
        etxtEmail.setEnabled(enable);
        etxPass.setEnabled(enable);
        btnEmpezar.setEnabled(enable);
        btnLoguearse.setEnabled(enable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registroPresenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        registroPresenter.onStop();
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
    public void mostrarError(String error) {
        habilitarInputs();
        Snackbar.make(parentRegistro,error,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void mostrarProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void exitoRegistro() {
        habilitarInputs();
        ocultarProgress();
        abrirActivityLogin();
    }

    @Override
    public void iniciarSesion() {
        abrirActivityLogin();
    }
}
