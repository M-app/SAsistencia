package com.example.sasistencia;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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
public class ListaAlumnosFragment extends Fragment {

    private static String GRADO = "";

    DatabaseReference gradoRef;
    AlumnoListaAdapter mAdapter;
    ListView listaAlumnos;
    List<Alumno> alumnos;
    ImageButton btnAgregarAlumno;

    public ListaAlumnosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_alumnos, container, false);
        GRADO = getArguments().getString("ID_GRADO");
        alumnos = new ArrayList<>();
        listaAlumnos = view.findViewById(R.id.lista_alumnos);
        btnAgregarAlumno = view.findViewById(R.id.btn_agregar_alumno);
        gradoRef = FirebaseDatabase.getInstance().getReference().child("grados/"+GRADO);
        gradoRef.addValueEventListener(getAlumnosPorGradoListener);
        registerForContextMenu(listaAlumnos);
        listaAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Alumno alumno = (Alumno) adapterView.getItemAtPosition(i);
                Fragment detalleAlumnoFragment = new DetalleAlumnoFragment();
                // pasar argumentos al fragment
                Bundle bundle = new Bundle();
                bundle.putString("OPERACION","Editar Alumno");
                bundle.putString("ID_GRADO",GRADO);
                bundle.putString("CLAVE_ALUMNO",alumno.getClave()+"");
                detalleAlumnoFragment.setArguments(bundle);
                // transaccion fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,detalleAlumnoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnAgregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment detalleAlumnoFragment = new DetalleAlumnoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("OPERACION","Nuevo Alumno");
                bundle.putString("ID_GRADO",GRADO);
                detalleAlumnoFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,detalleAlumnoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
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
        Alumno alumno = mAdapter.getItem(info.position);
        if (item.getItemId() == R.id.item_menu_eliminar_falta){
            mAdapter.remove(alumno);
            gradoRef.setValue(alumnos); /// eliminar alumno de la base de datos
            mAdapter.notifyDataSetChanged();
        }
        return true;

    }

    ValueEventListener getAlumnosPorGradoListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (mAdapter != null){
                mAdapter.clear();
            }
            for (DataSnapshot data : dataSnapshot.getChildren()){
                Alumno alumno = data.getValue(Alumno.class);
                alumnos.add(alumno);
            }
            mAdapter = new AlumnoListaAdapter(getActivity(),R.layout.item_lista_alumno,alumnos);
            listaAlumnos.setAdapter(mAdapter);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

}
