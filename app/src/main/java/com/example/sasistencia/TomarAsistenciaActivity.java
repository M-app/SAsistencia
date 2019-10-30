package com.example.sasistencia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TomarAsistenciaActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private String GRADO = "";
    private String FECHA = "";

    private ListView mListaAlumnos;
    private AlumnoAdapter mAdapter;

    Button btnEnviar;
    TextView txtNombreGrado;

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mAlumnosReference;
    private DatabaseReference mReportesRef;
    List<Alumno> alumnos;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_tomar_asistencia);

        GRADO = getIntent().getStringExtra("EXTRA_NOMBRE_GRADO");
        FECHA = getIntent().getStringExtra("EXTRA_FECHA");
        // inicializar firebase
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mAlumnosReference = mFirebaseDataBase.getReference().child("grados/"+GRADO);

        // inicializar referencias a views
        mListaAlumnos = findViewById(R.id.list_alumnos);
        btnEnviar = findViewById(R.id.btn_enviar);
        txtNombreGrado = findViewById(R.id.txt_nombre_grado);
        txtNombreGrado.setText(GRADO);

        // Inicializar alumnos listview y su adaptador
        alumnos = new ArrayList<>();

        calendar = Calendar.getInstance();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mAdapter != null)
                    mAdapter.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    alumnos.add(data.getValue(Alumno.class));
                }
                mAdapter = new AlumnoAdapter(TomarAsistenciaActivity.this,R.layout.item_estudiante,alumnos,FECHA);
                mListaAlumnos.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        mAlumnosReference.addValueEventListener(valueEventListener);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarEstatusAsistencia();
                Intent intent = new Intent(TomarAsistenciaActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        String email = FirebaseAuth.getInstance()
                .getCurrentUser().getEmail()
                .replace("@","-")
                .replace(".","_");
        DatabaseReference maestroRef = FirebaseDatabase.getInstance().getReference("maestros/"+email);
        maestroRef.addListenerForSingleValueEvent(maestrosListener);
        mReportesRef = FirebaseDatabase.getInstance().getReference("reportes/"+FECHA.replace("/","-")+"/"+GRADO);
        mReportesRef.addListenerForSingleValueEvent(reportesListener);
    }

    Maestro maestro = null;
    ValueEventListener maestrosListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue() != null)
                maestro = dataSnapshot.getValue(Maestro.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    ValueEventListener reportesListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Reporte reporte = dataSnapshot.getValue(Reporte.class);
            if(reporte == null){
                mReportesRef.setValue(new Reporte(Grados.getGradoById(GRADO),false,maestro.getNombre()));
                btnEnviar.setEnabled(true);
            }else{
                btnEnviar.setEnabled(true);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    private void cambiarEstatusAsistencia(){
        mReportesRef.setValue(new Reporte(Grados.getGradoById(GRADO),true,maestro.getNombre()));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int posicion = mListaAlumnos.getPositionForView(compoundButton);
        if(posicion != ListView.INVALID_POSITION){
            mAdapter.mEstadoCheck[posicion] = b;
            Alumno alumno = alumnos.get(posicion);
            if(alumno.getInasistencias() == null)
                alumno.setInasistencias(new ArrayList<String>());// si no existe ninguna inasistencia en el alumno
            String fechaActual = FECHA;
            boolean existe = false;
            for (String s : alumno.getInasistencias()){
                if(s.equals(fechaActual)){
                    existe = true;
                }
            }
            if(!b){ // no vino - debemos agregar inasistencia
                if(!existe){
                    alumno.getInasistencias().add(fechaActual);
                    String idAlumno = alumno.getClave()-1+"";
                    mAlumnosReference.child(idAlumno).setValue(alumno);
                }
            }else { // el alumno si vino
                if (existe) {
                    int posicionABorrar = alumno.getInasistencias().indexOf(fechaActual);
                    alumno.getInasistencias().remove(posicionABorrar);
                    String idAlumno = alumno.getClave() - 1 + "";
                    mAlumnosReference.child(idAlumno).setValue(alumno);
                }
            }
        }
    }
}
