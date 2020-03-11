package com.example.sasistencia.tomar_asistencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FechaHelper;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.seleccionar_grado.SeleccionarGradoActivity;


import java.util.ArrayList;
import java.util.List;

public class TomarAsistenciaActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, TomarAsistenciaView {

    private String GRADO = "";
    // ------------------ views ------------------------
    private ListView mListaAlumnos;
    private AlumnoAdapter mAdapter;
    Button btnEnviar;
    TextView txtNombreGrado;

    List<Alumno> mAlumnos;
    TomarAsistenciaPresenter presenter;

    // ------------- MANTENER LA POSICION DEL LIST VIEW------------
    private int mIndex = 0;
    private View viewPosition = null;
    private int mTop = 0;
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removerTitleBar();
        setContentView(R.layout.activity_tomar_asistencia);
        presenter = new TomarAsistenciaPresenterImpl(this);
        setGrado(getIntent().getStringExtra("EXTRA_NOMBRE_GRADO"));
        initViews();
        presenter.getListaAlumnos(GRADO);

        presenter.getMaestroActual();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiarEstatusAsistencia();

                Intent intent = new Intent(TomarAsistenciaActivity.this, SeleccionarGradoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void setGrado(String grado){
        GRADO = grado;
    }

    private void initViews(){
        mListaAlumnos = findViewById(R.id.list_alumnos);
        btnEnviar = findViewById(R.id.btn_enviar);
        txtNombreGrado = findViewById(R.id.txt_nombre_grado);
        txtNombreGrado.setText(NombresGrados.getNombreGradoById(GRADO));
        mAlumnos = new ArrayList<>();
    }

    public void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
    }

    @Override
    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        mAlumnos =  listaAlumnos;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int posicion = mListaAlumnos.getPositionForView(compoundButton);

        if(posicion != ListView.INVALID_POSITION){
            mAdapter.mEstadoCheck[posicion] = b;
            Alumno alumno = mAlumnos.get(posicion);
            if (b){ // marcando el checkbox
                if (alumno.getInasistencias() != null){ // existen inasistencias
                    for (String s : alumno.getInasistencias() ){
                        if (s.equals(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA))){
                            alumno.getInasistencias().remove(
                                    alumno.getInasistencias().indexOf(
                                            FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA)));
                            // modificar alumno
                            presenter.modificarAsistenciaAlumno(GRADO,alumno);
                        }
                    }
                }
            }else{ // ingresando falta
                if (alumno.getInasistencias() == null){ // si no existe ninguna inasistencia en el alumno
                    alumno.setInasistencias(new ArrayList<String>());
                    alumno.getInasistencias().add(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA));
                    presenter.modificarAsistenciaAlumno(GRADO,alumno);
                }else{
                    boolean existe = alumno.getInasistencias()
                            .contains(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA));

                    if (!existe){
                        alumno.getInasistencias().add(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA));
                        // modificar mAlumnos
                        presenter.modificarAsistenciaAlumno(GRADO,alumno);
                    }
                }
            }
        }
    }

    @Override
    public void llenarListaAlumnos(List<Alumno> listaAlumnos) {
        /*if (mAdapter == null){
            mAdapter = new AlumnoAdapter(
                    TomarAsistenciaActivity.this,R.layout.item_estudiante_asistencia,
                    listaAlumnos);
            mListaAlumnos.setAdapter(mAdapter);
            Log.v("ASISTENCIA","NOTIFYDATASETCHANGED");
        }
        else{
            //mAdapter.notifyDataSetChanged();
            mAdapter.clear();
            mAdapter.addAll(listaAlumnos);
            //mListaAlumnos.setSelectionFromTop(mIndex,mTop);
        }
        //mListaAlumnos.smoothScrollToPosition(mIndex);*/
        if (mAdapter != null){
            mAdapter.clear();
        }
        if (!mAlumnos.isEmpty()){
            mAlumnos.clear();
        }else{
            mAlumnos.addAll(listaAlumnos);
        }
        if (mAdapter == null){
            mAdapter = new AlumnoAdapter(
                    TomarAsistenciaActivity.this,R.layout.item_estudiante_asistencia,
                    listaAlumnos);
            mListaAlumnos.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }




    @Override
    public void abrirActivitySeleccionarGrado() {

    }
}