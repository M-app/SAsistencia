package com.example.sasistencia;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReporteAdapter extends ArrayAdapter<Reporte> {


    public ReporteAdapter(Context context, int resource, List<Reporte> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_reporte_asistencia,parent,false);
        }
        TextView txtNombreGrado = convertView.findViewById(R.id.txt_nombre_grado);
        TextView txtProfesorGrado = convertView.findViewById(R.id.txt_profesor_grado);
        ImageView imgEstatus = convertView.findViewById(R.id.img_status);

        final Reporte reporte = getItem(position);
        if(reporte != null){
            Log.v("NOMBRE DEL GRADO",reporte.getNombreGrado());
            txtNombreGrado.setText(reporte.getNombreGrado());
            txtProfesorGrado.setText(reporte.getProfesor());
            imgEstatus.setImageResource(reporte.asistenciaTomada ?
                    R.drawable.ic_check_box_black_24dp:
                    R.drawable.ic_clear_black_24dp);
        }
        return convertView;
    }

}
