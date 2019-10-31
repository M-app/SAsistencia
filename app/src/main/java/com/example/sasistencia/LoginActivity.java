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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //Views
    EditText etxtEmail,etxtPass;
    Button btnIniciarSesion,btnRegistrarse;
    ProgressBar progressBarLogin;

    // Firebase
    FirebaseAuth mAuth;
    DatabaseReference maestrosRef;
    DatabaseReference maestroRef;

    String mRolMaestro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removerTitleBar();
        setContentView(R.layout.activity_login);

        iniciarView();

        //firebase
        mAuth = FirebaseAuth.getInstance();



        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsuario();
            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityRegistrarse();
            }
        });
        maestrosRef = FirebaseDatabase.getInstance().getReference("maestros");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        if(usuarioActual != null){
            maestroRef = maestrosRef.child(getEmailArreglado(mAuth.getCurrentUser().getEmail()));
            maestroRef.addValueEventListener(maestroListener);
        }
    }

    private void loginUsuario(){
        progressBarLogin.setVisibility(View.VISIBLE);
        String email,pass;
        email = etxtEmail.getText().toString();
        pass = etxtPass.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Un Correo Electrónico",Toast.LENGTH_LONG);
            return;
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Una Contraseña", Toast.LENGTH_LONG);
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBarLogin.setVisibility(View.GONE);
                            maestroRef = maestrosRef.child(getEmailArreglado(mAuth.getCurrentUser().getEmail()));
                            maestroRef.addValueEventListener(maestroListener);
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                            progressBarLogin.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    private String getEmailArreglado(String email){
        return email
                .replace("@","-")
                .replace(".","_");
    }

    ValueEventListener maestroListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Maestro maestro = dataSnapshot.getValue(Maestro.class);
            mRolMaestro = maestro.getRol();
            if (mRolMaestro.equals("docente")){
                abrirActivityMain();
            }else if (mRolMaestro.equals("administrador")){
                abrirActivityAdmin();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    private void abrirActivityMain(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void abrirActivityAdmin(){
        Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
        startActivity(intent);
    }

    private void abrirActivityRegistrarse(){
        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }


    private void iniciarView(){
        etxtEmail = findViewById(R.id.etxt_email_login);
        etxtPass = findViewById(R.id.etxt_pass_login);
        btnIniciarSesion = findViewById(R.id.btn_iniciarSesion);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        progressBarLogin = findViewById(R.id.progressBarLogin);
    }

    private void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
    }
}
