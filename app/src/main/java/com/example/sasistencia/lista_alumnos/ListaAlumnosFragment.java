package com.example.sasistencia.lista_alumnos;


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
import android.widget.Toast;

import com.example.sasistencia.detalle_alumno.DetalleAlumnoFragment;
import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAlumnosFragment extends Fragment implements ListaAlumnosView {

    private static String ID_GRADO = "";

    AlumnoListaAdapter mAdapter;
    ListView listaAlumnos;
    List<Alumno> mAlumnos;
    ImageButton btnAgregarAlumno;

    ListaAlumnosPresenter presenter;

    @Override
    public void notificarAlumnoEliminado() {
        Toast.makeText(getActivity(),"Alumno Eliminado",Toast.LENGTH_SHORT).show();
    }

    public ListaAlumnosFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_alumnos, container, false);

        presenter = new ListaAlumnosPresenterImpl(this);
        ID_GRADO = getArguments().getString("ID_GRADO");

        mAlumnos = new ArrayList<>();
        listaAlumnos = view.findViewById(R.id.lista_alumnos);
        btnAgregarAlumno = view.findViewById(R.id.btn_agregar_alumno);

        presenter.mostrarAlumnos(ID_GRADO);

        registerForContextMenu(listaAlumnos);

        listaAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Alumno alumno = (Alumno) adapterView.getItemAtPosition(i);
                abrirDetalleAlumno(ID_GRADO,alumno.getClave()+"");
            }
        });

        btnAgregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment detalleAlumnoFragment = new DetalleAlumnoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ID_GRADO", ID_GRADO);
                detalleAlumnoFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.nav_host_fragment,detalleAlumnoFragment)
                        .addToBackStack("transDetalleAlumno")
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
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
        Alumno alumno = mAdapter.getItem(info.position);
        if (item.getItemId() == R.id.item_menu_eliminar_falta){
            presenter.eliminarAlumno(ID_GRADO,alumno.getClave()+"");
        }
        return true;
    }

    @Override
    public void abrirDetalleAlumno(String idGrado, String clave) {
        Fragment detalleAlumnoFragment = new DetalleAlumnoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID_GRADO", idGrado);
        bundle.putString("CLAVE_ALUMNO",clave);
        detalleAlumnoFragment.setArguments(bundle);
        // transaccion fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,detalleAlumnoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void mostrarAlumnos(ArrayList<Alumno> listaAlumnos) {
        if (mAdapter != null){
            mAdapter.clear();
        }
        if (!mAlumnos.isEmpty()){
            mAlumnos.clear();
        }else{
            mAlumnos.addAll(listaAlumnos);
        }
        if (mAdapter == null){
            mAdapter = new AlumnoListaAdapter(getActivity(),R.layout.item_lista_alumno, mAlumnos);
            this.listaAlumnos.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }
}
