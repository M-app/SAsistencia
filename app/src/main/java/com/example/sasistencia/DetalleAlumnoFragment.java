package com.example.sasistencia;


import android.content.Intent;
import android.os.Bundle;

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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAlumnoFragment extends Fragment {

    DatabaseReference gradoRef;
    DatabaseReference alumnoRef;
    DatabaseReference alumnoFaltasRef;

    ValueEventListener getDatosAlumnoListener;
    ValueEventListener getFaltasAlumnoListener;
    ValueEventListener getAlumnosDeGradoListener;

    String GRADO = "";
    String CLAVE_ALUMNO = "";
    String OPERACION = "";

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
    List<String> faltas;
    List<Alumno> alumnos;
    FaltasAdapter mAdapter;

    Alumno mAlumno = null;

    public DetalleAlumnoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_alumno, container, false);
        OPERACION = getArguments().getString("OPERACION");
        GRADO = getArguments().getString("ID_GRADO");
        if (OPERACION.equals("Editar Alumno")){
            CLAVE_ALUMNO = Integer.parseInt(getArguments().getString("CLAVE_ALUMNO"))-1+"";
        }
        initViews(view);
        initListeners(OPERACION);
        configViewFromOpcion(OPERACION);
        configClickListeners();
        return view;
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
            mAdapter.remove(falta);
            alumnoFaltasRef.setValue(faltas);
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    private Alumno getAlumnoFromInputs(int clave){
        return new Alumno(clave,editApellidos.getText().toString(),
                            editNombres.getText().toString(),
                            Integer.parseInt(editTelefono.getText().toString()),null);
    }

    private void initListeners(String operacion){
        if (operacion == "Nuevo Alumno"){
            alumnos = new ArrayList<>();
            getAlumnosDeGradoListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Alumno alumno = data.getValue(Alumno.class);
                        alumnos.add(alumno);
                    }
                    gradoRef.child(alumnos.size()+"").setValue(getAlumnoFromInputs(alumnos.size()+1));
                    Fragment listaAlumnosFragment = new ListaAlumnosFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("ID_GRADO",GRADO);
                    listaAlumnosFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,listaAlumnosFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };

        }else{
            getDatosAlumnoListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mAlumno = dataSnapshot.getValue(Alumno.class);
                    setDatosAlumno(mAlumno.getClave()+"",mAlumno.getNombres(),
                            mAlumno.getApellidos(),mAlumno.getTelefono()+"");
                }@Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };

            getFaltasAlumnoListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null){
                        if(mAdapter != null){
                            mAdapter.clear();
                        }
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            String falta = data.getValue(String.class);
                            faltas.add(falta);
                        }
                        mAdapter = new FaltasAdapter(getActivity(),R.layout.item_falta_alumno,faltas);
                        listaFaltas.setAdapter(mAdapter);
                    }
                }@Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };
        }
    }



    private void initViews(View view){
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

    private void configViewFromOpcion(String opcion){
        gradoRef = FirebaseDatabase.getInstance().getReference().child("grados/"+GRADO);

        if (opcion.equals("Nuevo Alumno")){
            txtFaltas.setVisibility(View.GONE);
            listaFaltas.setVisibility(View.GONE);
            alumnos = new ArrayList<>();
        }else{
            faltas = new ArrayList<>();
            editNombres.setEnabled(false);
            editApellidos.setEnabled(false);
            editTelefono.setEnabled(false);
            btnAgregarNuevoAlumno.setVisibility(View.GONE);

            registerForContextMenu(listaFaltas);
            alumnoRef = gradoRef.child(CLAVE_ALUMNO);
            alumnoFaltasRef = alumnoRef.child("inasistencias");
            configTextWatchers();
            if (getDatosAlumnoListener != null)
                alumnoRef.addValueEventListener(getDatosAlumnoListener);
            if (getFaltasAlumnoListener != null)
                alumnoFaltasRef.addValueEventListener(getFaltasAlumnoListener);
        }
    }

    private void setDatosAlumno(String clave, String nombres, String apellidos, String telefono){
        txtClave.setText(clave);
        editNombres.setText(nombres);
        editApellidos.setText(apellidos);
        editTelefono.setText(telefono);
    }

    private void configTextWatchers(){
        TextWatcher watcherNombres = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                mAlumno.setNombres(editable.toString());
                alumnoRef.setValue(mAlumno);
            }
        };
        editNombres.addTextChangedListener(watcherNombres);

        TextWatcher watcherApellidos = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                mAlumno.setApellidos(editable.toString());
                alumnoRef.setValue(mAlumno);
            }
        };
        editApellidos.addTextChangedListener(watcherApellidos);

        TextWatcher watcherTelefono = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                mAlumno.setTelefono(Integer.parseInt(editable.toString()));
                alumnoRef.setValue(mAlumno);
            }
        };
        editTelefono.addTextChangedListener(watcherTelefono);
    }

    private void configClickListeners(){
        btnEditarNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNombres.isEnabled()){
                    //guardar registro en la base de datos
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
                    editTelefono.setEnabled(false);
                }else{
                    editTelefono.setEnabled(true);
                }
            }
        });
        btnAgregarNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAlumnosDeGradoListener != null)
                    gradoRef.addListenerForSingleValueEvent(getAlumnosDeGradoListener);

            }
        });
    }

}
