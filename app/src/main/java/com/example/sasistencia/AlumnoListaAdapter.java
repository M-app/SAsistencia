package com.example.sasistencia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlumnoListaAdapter extends ArrayAdapter<Alumno> {

    TextView txtApellidos;
    TextView txtNombres;
    TextView txtClave;

    public AlumnoListaAdapter(Context context, int resource, List<Alumno> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_lista_alumno,parent,false);
        }
        txtClave = convertView.findViewById(R.id.txt_clave_alumno);
        txtApellidos = convertView.findViewById(R.id.txt_apellidos_alumno);
        txtNombres = convertView.findViewById(R.id.txt_nombres_alumno);

        Alumno alumno = getItem(position);
        txtClave.setText(alumno.getClave()+"");
        txtApellidos.setText(alumno.getApellidos());
        txtNombres.setText(alumno.getNombres());
        return convertView;
    }
}
