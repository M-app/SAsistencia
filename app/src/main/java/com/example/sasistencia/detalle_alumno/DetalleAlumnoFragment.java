package com.example.sasistencia.detalle_alumno;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.lista_alumnos.ListaAlumnosFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAlumnoFragment extends Fragment implements DetalleAlumnoView{


    String ID_GRADO = "";
    String CLAVE_ALUMNO = "";

    LinearLayout fragDetalleAlumno;
    TextView txtFaltas;
    TextView txtClave;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    ImageButton btnEditarNombres;
    ImageButton btnEditarApellidos;
    ImageButton btnEditarTelefono;
    Button btnAgregarNuevoAlumno;
    ListView listaFaltas;
    List<String> faltas = new ArrayList<>();
    FaltasAdapter mAdapter;

    DetalleAlumnoPresenter presenter;

    public DetalleAlumnoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void setClaveAlumnoNuevo(String clave) {
        txtClave.setText(clave);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_alumno, container, false);

        presenter = new DetalleAlumnoPresenterImpl(this);
        ID_GRADO = getArguments().getString("ID_GRADO");
        if (getArguments().getString("CLAVE_ALUMNO")!=null){
            CLAVE_ALUMNO = Integer.parseInt(getArguments().getString("CLAVE_ALUMNO"))+"";
        }
        else {
            CLAVE_ALUMNO = "";
        }
        initViews(view);
        presenter.setGrado(ID_GRADO);
        if(!CLAVE_ALUMNO.isEmpty())
            presenter.mostrarDatosAlumnos(CLAVE_ALUMNO);
        else{
            presenter.getCantidadAlumnos(ID_GRADO);

        }
        //initListeners(OPERACION);
        //configViewFromOpcion(OPERACION);
        configClickListeners();
        return view;
    }

    @Override
    public void mostrarDatos(Alumno alumno) {
        txtClave.setText(alumno.getClave()+"");
        editNombres.setText(alumno.getNombres());
        editApellidos.setText(alumno.getApellidos());
        editTelefono.setText(alumno.getTelefono()+"");
        if (alumno.getInasistencias() != null){
            registerForContextMenu(listaFaltas);
            faltas.clear();
            for (String falta : alumno.getInasistencias()){
                faltas.add(falta);
            }
            if (mAdapter == null)
                mAdapter = new FaltasAdapter(getActivity(),R.layout.item_falta_alumno,faltas);
            listaFaltas.setAdapter(mAdapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.eliminar_falta_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String falta = mAdapter.getItem(info.position);
        if (item.getItemId() == R.id.item_menu_eliminar_falta){
            //alumnoFaltasRef.setValue(faltas);
            presenter.eliminarFalta(getAlumnoFromInputs(Integer.parseInt(txtClave.getText().toString())),faltas.indexOf(falta)+"");
            mAdapter.remove(falta);
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }


    // recibe la clave porque está ya está fija
    private Alumno getAlumnoFromInputs(int clave){
        return new Alumno(clave,editApellidos.getText().toString(),
                            editNombres.getText().toString(),
                            Integer.parseInt(editTelefono.getText().toString()),
                faltas.size() > 0 ? new ArrayList<String>(faltas): null);
    }

    private void initViews(View view){
        fragDetalleAlumno = view.findViewById(R.id.frag_detalle_alumno);
        txtClave = view.findViewById(R.id.txt_detalle_alumno_clave);
        editNombres = view.findViewById(R.id.edit_detalle_alumno_nombres);
        editApellidos = view.findViewById(R.id.edit_detalle_alumno_apellidos);
        editTelefono = view.findViewById(R.id.edit_detalle_alumno_telefono);
        btnEditarNombres = view.findViewById(R.id.btn_detalle_alumno_editar_nombres);
        btnEditarApellidos = view.findViewById(R.id.btn_detalle_alumno_editar_apellidos);
        btnEditarTelefono = view.findViewById(R.id.btn_detalle_alumno_editar_telefono);
        txtFaltas = view.findViewById(R.id.txt_faltas);
        listaFaltas = view.findViewById(R.id.lista_faltas_alumnos);
        btnAgregarNuevoAlumno = view.findViewById(R.id.btn_agregar_nuevo_alumno);
    }

    private void configClickListeners(){
        btnEditarNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNombres.isEnabled()){
                    editNombres.setEnabled(false);

                }else{
                    editNombres.setEnabled(true);
                }
            }
        });
        btnEditarApellidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editApellidos.isEnabled()){
                    //guardar registro en la base de datos
                    //presenter.modificarAlumno(getAlumnoFromInputs(Integer.parseInt(txtClave.getText().toString())));
                    editApellidos.setEnabled(false);
                }else{
                    editApellidos.setEnabled(true);
                }
            }
        });
        btnEditarTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTelefono.isEnabled()){
                    //guardar registro en la base de datos
                    //presenter.modificarAlumno(getAlumnoFromInputs(Integer.parseInt(txtClave.getText().toString())));
                    editTelefono.setEnabled(false);
                }else{
                    editTelefono.setEnabled(true);
                }
            }
        });
        btnAgregarNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alumno alumno = getAlumnoFromInputs(Integer.parseInt(txtClave.getText().toString()));
                if (!alumno.getNombres().isEmpty() &&
                    !alumno.getApellidos().isEmpty() &&
                    !alumno.getTelefono().toString().isEmpty()){
                    presenter.agregarAlumno(alumno);
                }else{
                    mostrarMensaje("No pueden haber datos vacios.");
                }
            }
        });
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Snackbar.make(fragDetalleAlumno,mensaje,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void abrirFragmentListaAlumnos() {
        Fragment listaAlumnosFragment = new ListaAlumnosFragment();
        // pasar argumentos al fragment
        Bundle bundle = new Bundle();
        bundle.putString("ID_GRADO", NombresGrados.getIdGradoByNombre(ID_GRADO));
        listaAlumnosFragment.setArguments(bundle);
        // transaccion fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,listaAlumnosFragment);
        transaction.commit();
    }
}
