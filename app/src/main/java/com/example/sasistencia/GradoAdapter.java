package com.example.sasistencia;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GradoAdapter extends ArrayAdapter<String> {

    TextView txtNombreGrado;

    public GradoAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_grado,parent,false);
        }
        txtNombreGrado = convertView.findViewById(R.id.txt_nombre_grado_grados);
        txtNombreGrado.setText(getItem(position));
        return convertView;
    }
}
