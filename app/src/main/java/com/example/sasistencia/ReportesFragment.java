package com.example.sasistencia;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.seleccionar_grado.SeleccionarGradoActivity;
import com.example.sasistencia.tomar_asistencia.TomarAsistenciaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportesFragment extends Fragment{

    private TextView txtFecha;
    private Button btnCrearReporte;

    private String mFecha = "";
    private String mFechaCorta = "";

    private ListView mListaReportes;
    private ReporteAdapter mAdapter;
    private List<Reporte> reportes;

    //firebase
    DatabaseReference reportesRef;

    public ReportesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reportesRef = FirebaseDatabase.getInstance().getReference("reportes/"+getFechaHoyGuiones());
        mFecha = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        mFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);

        txtFecha = view.findViewById(R.id.txt_reporte_fecha);
        txtFecha.setText("Reporte " + mFecha);


        btnCrearReporte = view.findViewById(R.id.btn_crear_reporte);
        btnCrearReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeleccionarGradoActivity.class);
                startActivity(intent);
            }
        });

        mListaReportes = view.findViewById(R.id.lista_reportes);
        reportes = new ArrayList<>();
        mAdapter = new ReporteAdapter(getActivity(),R.layout.item_reporte_asistencia,reportes);

        reportesRef.addValueEventListener(gradosListener);

        mListaReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reporte reporte = (Reporte) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), TomarAsistenciaActivity.class);
                intent.putExtra("EXTRA_NOMBRE_GRADO", NombresGrados.getIdGradoByNombre(reporte.getNombreGrado()));
                intent.putExtra("EXTRA_FECHA",mFechaCorta);
                startActivity(intent
                );
            }
        });
        return view;
    }

    ValueEventListener gradosListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(mAdapter != null){
                mAdapter.clear();
            }
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                Reporte reporte = snapshot.getValue(Reporte.class);
                reportes.add(reporte);
            }
            mListaReportes.setAdapter(mAdapter);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    private String getFechaHoyGuiones(){
        String fechaActual = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        return fechaActual.replace("/","-");
    }
}
