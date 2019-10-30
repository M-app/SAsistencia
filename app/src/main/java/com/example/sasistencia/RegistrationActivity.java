package com.example.sasistencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    // views
    EditText etxtNombre;
    EditText etxtEmail;
    EditText etxPass;
    Button btnEmpezar, btnLoguearse;
    ProgressBar progressBar;

    // firebase
    private FirebaseAuth mAuth;
    DatabaseReference maestrosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removerTitleBar();
        setContentView(R.layout.activity_registration);

        iniciarViews();

        mAuth = FirebaseAuth.getInstance();

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCuenta(etxtNombre.getText().toString(),
                            etxtEmail.getText().toString(),
                            etxPass.getText().toString());
            }
        });

        btnLoguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityLogin();
            }
        });
    }

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

    private void abrirActivityLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void iniciarViews(){
        etxtNombre = findViewById(R.id.etxt_nombre);
        etxtEmail = findViewById(R.id.etxt_email);
        etxPass = findViewById(R.id.etxt_pass);
        btnEmpezar = findViewById(R.id.btn_empezar);
        btnLoguearse = findViewById(R.id.btn_loguearse);
        progressBar = findViewById(R.id.progressBar);
    }

    private void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
    }
}
