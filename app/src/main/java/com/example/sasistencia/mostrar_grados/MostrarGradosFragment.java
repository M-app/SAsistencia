package com.example.sasistencia.mostrar_grados;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sasistencia.lista_alumnos.ListaAlumnosFragment;
import com.example.sasistencia.R;
import com.example.sasistencia.domain.NombresGrados;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarGradosFragment extends Fragment implements MostrarGradosView{


    GradoAdapter mAdapter;

    ListView listaGrados;

    List<String> grados;

    MostrarGradosPresenter presenter;

    public MostrarGradosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        listaGrados = view.findViewById(R.id.lista_grados);
        grados = new ArrayList<>();

        presenter = new MostrarGradosPresenterImpl(this);
        presenter.mostrarGrados();


        listaGrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombreGrado = adapterView.getItemAtPosition(i).toString();
                Fragment listaAlumnosFragment = new ListaAlumnosFragment();
                // pasar argumentos al fragment
                Bundle bundle = new Bundle();
                bundle.putString("ID_GRADO", NombresGrados.getIdGradoByNombre(nombreGrado));
                listaAlumnosFragment.setArguments(bundle);
                // transaccion fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,listaAlumnosFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void mostrarListaGrados(ArrayList<String> listaGrados) {
        if (mAdapter != null){
            mAdapter.clear();
        }
        grados.addAll(listaGrados);
        if (mAdapter == null){
            mAdapter = new GradoAdapter(getActivity(),R.layout.item_grado,grados);
            this.listaGrados.setAdapter(mAdapter);
        }
    }
}
