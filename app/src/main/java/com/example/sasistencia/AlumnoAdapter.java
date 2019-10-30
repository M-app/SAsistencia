package com.example.sasistencia;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {
    Context context;
    Calendar calendar;
    boolean[] mEstadoCheck;
    String mFecha;

    public AlumnoAdapter(Context context, int resource, List<Alumno> objects, String fecha) {
        super(context, resource, objects);
        this.context = context;
        calendar = Calendar.getInstance();
        mEstadoCheck = new boolean[objects.size()];
        Arrays.fill(mEstadoCheck,true);
        mFecha = fecha;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_estudiante,parent,false);
        }
        TextView claveTextView = convertView.findViewById(R.id.txt_clave);
        TextView apellidosTextView = convertView.findViewById(R.id.txt_apellidos);
        TextView nombresTextView = convertView.findViewById(R.id.txt_nombres);
        CheckBox checkAsistencia = convertView.findViewById(R.id.check_asistencia);

        final Alumno alumno = getItem(position);

        claveTextView.setText(""+alumno.getClave());
        apellidosTextView.setText(alumno.getApellidos());
        nombresTextView.setText(alumno.getNombres());
        checkAsistencia.setOnCheckedChangeListener((TomarAsistenciaActivity) context);
        if(alumno.getInasistencias() != null){
            if(alumno.getInasistencias().size() > 0){
                for (String s : alumno.getInasistencias()){
                    if(s.equals(mFecha)){
                        mEstadoCheck[position] = false;
                    }
                }
            }
        }
        checkAsistencia.setChecked(mEstadoCheck[position]);
        return convertView;
    }
}
