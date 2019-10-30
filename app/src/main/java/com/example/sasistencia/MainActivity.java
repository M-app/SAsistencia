package com.example.sasistencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //views
    Spinner spinner_carreras, spinner_grados;
    Button btnListo, btnFecha;
    TextView txtFecha, txtNombreUsuario;
    ImageButton ibtnCerrarSesion;

    //firebase
    FirebaseUser usuarioActual;
    DatabaseReference usuarioRef;

    //constantes
    String mFecha = "";
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removerTitleBar();
        ibtnCerrarSesion = findViewById(R.id.ibtn_cerrar_sesion);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        txtNombreUsuario = findViewById(R.id.txt_nombre_usuario);

        String email = usuarioActual.getEmail().replace("@","-");
        String emailArreglado = email.replace("@","-")
                .replace(".","_");
        usuarioRef = FirebaseDatabase.getInstance()
                .getReference().child("maestros").child(emailArreglado);
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Maestro maestro = dataSnapshot.getValue(Maestro.class);
                txtNombreUsuario.setText("Hola " + maestro.nombre);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner_carreras = findViewById(R.id.spinner_carreras);
        spinner_carreras.setAdapter(getCarrerasAdapter());


        spinner_grados = findViewById(R.id.spinner_grados);


        btnFecha = findViewById(R.id.btn_fecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment f = new DatePickerFragment();
                f.show(getSupportFragmentManager(),"Date Picker");
            }
        });

        txtFecha = findViewById(R.id.txt_fecha);
        String fechaActual = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        mFecha = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        txtFecha.setText(fechaActual);

        btnListo = findViewById(R.id.btn_listo);
        configSecondSpinner();
        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAlumnosActivity();
            }
        });

        ibtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                abrirLoginActivity();
            }
        });

    }

    private void abrirListaAlumnosActivity() {
        Intent intent = new Intent(this,TomarAsistenciaActivity.class);
        intent.putExtra("EXTRA_NOMBRE_GRADO",saberGrado());
        intent.putExtra("EXTRA_FECHA",mFecha);
        startActivity(intent);
    }

    private void abrirLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
    }

    private void configSecondSpinner(){
        spinner_carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String carrera = spinner_carreras.getSelectedItem().toString();
                if (carrera.equals("Básico")){
                    spinner_grados.setAdapter(getBasicosAdapter());
                }else if(carrera.equals("Bachillerato")){
                    spinner_grados.setAdapter(getBachilleratosAdapter());
                }else if(carrera.equals("Magisterio")){
                    spinner_grados.setAdapter(getMagisteriosAdapter());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String saberGrado(){
        String carrera = spinner_carreras.getSelectedItem().toString();
        String grado = spinner_grados.getSelectedItem().toString();
        switch (carrera+grado){
            case "BásicoPrimero":
                return  "PrimeroBasico";
            case "BásicoSegundo":
                return "SegundoBasico";
            case "BásicoTercero":
                return "TerceroBasico";
            case "CuartoBachillerato":
                return "CuartoBachillerato";
            case "QuintoBachillerato":
                return "QuintoBachillerato";
            default:
                return "Desconocido";
        }
    }

    private ArrayAdapter<CharSequence> getCarrerasAdapter(){
        return ArrayAdapter.createFromResource(this,
                R.array.lista_carreras, R.layout.spinner_carreras_item);
    }

    private ArrayAdapter<CharSequence> getBasicosAdapter(){
        return ArrayAdapter.createFromResource(this,
                R.array.lista_basicos, R.layout.spinner_carreras_item);
    }

    private ArrayAdapter<CharSequence> getBachilleratosAdapter(){
        return ArrayAdapter.createFromResource(this,
                                                        R.array.lista_bachilleratos,
                                                        R.layout.spinner_carreras_item);
    }

    private ArrayAdapter<CharSequence> getMagisteriosAdapter(){
        return ArrayAdapter.createFromResource(this,
                R.array.lista_magisterios,
                R.layout.spinner_carreras_item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        String fechaActual = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        mFecha = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        txtFecha.setText(fechaActual);
    }
}
