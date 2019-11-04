package com.example.sasistencia;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class AlumnosFragment extends Fragment {

    DatabaseReference gradosRef;
    GradoAdapter mAdapter;
    ListView listaGrados;
    List<String> grados;

    public AlumnosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        listaGrados = view.findViewById(R.id.lista_grados);

        grados = new ArrayList<>();

        gradosRef = FirebaseDatabase.getInstance().getReference().child("grados");
        gradosRef.addListenerForSingleValueEvent(getGradosListener);
        listaGrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombreGrado = adapterView.getItemAtPosition(i).toString();
                Fragment listaAlumnosFragment = new ListaAlumnosFragment();
                // pasar argumentos al fragment
                Bundle bundle = new Bundle();
                bundle.putString("ID_GRADO",Grados.getIdByGrado(nombreGrado));
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

    ValueEventListener getGradosListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(mAdapter != null)
                mAdapter.clear();
            for (DataSnapshot data : dataSnapshot.getChildren()){
                grados.add(Grados.getGradoById(data.getKey()));
            }
            mAdapter = new GradoAdapter(getActivity(),R.layout.item_grado, grados);
            listaGrados.setAdapter(mAdapter);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

}
