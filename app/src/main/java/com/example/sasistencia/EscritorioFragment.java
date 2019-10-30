package com.example.sasistencia;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class EscritorioFragment extends Fragment {


    public EscritorioFragment() {
        // Required empty public constructor
    }

    ImageButton ibtnCerrarSesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_escritorio, container, false);
        ibtnCerrarSesion = rootView.findViewById(R.id.ibtn_cerrar_sesion_admin);
        ibtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                abrirLoginActivity();
            }
        });
        return rootView;
    }

    private void abrirLoginActivity(){
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
    }

}
