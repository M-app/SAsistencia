package com.example.sasistencia.seleccionar_grado;

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

import com.example.sasistencia.DatePickerFragment;
import com.example.sasistencia.R;
import com.example.sasistencia.domain.FechaHelper;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.login.LoginActivity;
import com.example.sasistencia.tomar_asistencia.TomarAsistenciaActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class SeleccionarGradoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, SeleccionarGradoView {

    //views
    Spinner spinner_carreras, spinner_grados;
    Button btnListo, btnFecha;
    TextView txtFecha, txtNombreUsuario;
    ImageButton ibtnCerrarSesion;

    Calendar calendar = Calendar.getInstance();

    SeleccionarGradoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SeleccionarGradoPresenterImpl(this);
        removerTitleBar();
        initViews();
        disableControls();
        presenter.obtenerNombreMaestro();
        configPrimerSpinner();
        configSegundoSpinner();

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment f = new DatePickerFragment();
                f.show(getSupportFragmentManager(),"Date Picker");
            }
        });
        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityTomarAsistencia();
            }
        });
        ibtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cerrarSesion();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void initViews(){
        ibtnCerrarSesion = findViewById(R.id.ibtn_cerrar_sesion);
        txtNombreUsuario = findViewById(R.id.txt_nombre_usuario);
        spinner_carreras = findViewById(R.id.spinner_carreras);
        spinner_grados = findViewById(R.id.spinner_grados);
        btnFecha = findViewById(R.id.btn_fecha);
        txtFecha = findViewById(R.id.txt_fecha);
        btnListo = findViewById(R.id.btn_listo);
        setFecha();
    }

    private void removerTitleBar(){
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
    }

    @Override
    public void abrirActivityLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param idCarrera NombresGrados.ID_CARRERA o cualquier otro
     * @return un arrayadapter con la lista de grados de la carrera seleccionada
     */
    public ArrayAdapter<CharSequence> getAdapterById(int idCarrera){
        switch (idCarrera){
            case NombresGrados.ID_CARRERAS:
                return ArrayAdapter.createFromResource(this,
                        R.array.lista_carreras, R.layout.spinner_carreras_item);
            case NombresGrados.ID_CARRERA_BASICO:
                return ArrayAdapter.createFromResource(this,
                        R.array.lista_basicos, R.layout.spinner_carreras_item);
            case NombresGrados.ID_CARRERA_BACHILLERATO:
                return ArrayAdapter.createFromResource(this,
                        R.array.lista_bachilleratos,
                        R.layout.spinner_carreras_item);
            case NombresGrados.ID_CARRERA_MAGISTERIO:
                return ArrayAdapter.createFromResource(this,
                        R.array.lista_magisterios,
                        R.layout.spinner_carreras_item);
            default:
                return null;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        FechaHelper.setFechaGuardadaLarga(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        FechaHelper.setFechaGuardadaCorta(DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()));
        txtFecha.setText(FechaHelper.fechaGuardadaLarga);
    }

    @Override
    public void setFecha() {
        txtFecha.setText(FechaHelper.getFechaActual(FechaHelper.FECHA_LARGA));
    }

    @Override
    public void mostrarNombre(String nombre) {
        txtNombreUsuario.setText(nombre);
    }

    @Override
    public void disableControls() {
        setControles(false);
    }

    @Override
    public void enableControls() {
        setControles(true);
    }

    private void setControles(boolean enable){
        spinner_carreras.setEnabled(enable);
        spinner_grados.setEnabled(enable);
        txtFecha.setEnabled(enable);
        btnFecha.setEnabled(enable);
        txtFecha.setEnabled(enable);
        txtNombreUsuario.setEnabled(enable);
        ibtnCerrarSesion.setEnabled(enable);
    }

    @Override
    public void configPrimerSpinner() {
        spinner_carreras.setAdapter(getAdapterById(NombresGrados.ID_CARRERAS));
    }

    @Override
    public void configSegundoSpinner() {
        spinner_carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String carrera = spinner_carreras.getSelectedItem().toString();
                if (carrera.equals("Básico")){
                    spinner_grados.setAdapter(getAdapterById(NombresGrados.ID_CARRERA_BASICO));
                }else if(carrera.equals("Bachillerato")){
                    spinner_grados.setAdapter(getAdapterById(NombresGrados.ID_CARRERA_BACHILLERATO));
                }else if(carrera.equals("Magisterio")){
                    spinner_grados.setAdapter(getAdapterById(NombresGrados.ID_CARRERA_MAGISTERIO));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void abrirActivityTomarAsistencia() {
        Intent intent = new Intent(this, TomarAsistenciaActivity.class);
        intent.putExtra("EXTRA_NOMBRE_GRADO",NombresGrados.getIdGradoFromSpinners(
                spinner_carreras.getSelectedItem().toString(),
                spinner_grados.getSelectedItem().toString()));
        startActivity(intent);
        Log.v("ABRIR ACTIVITY",NombresGrados.getIdGradoFromSpinners(
                spinner_carreras.getSelectedItem().toString(),
                spinner_grados.getSelectedItem().toString()));
        Log.v("FECHA CORTA",FechaHelper.getFechaGuardadaCorta());
        Log.v("FECHA LARGA", FechaHelper.getFechaGuardadaLarga());
    }
}