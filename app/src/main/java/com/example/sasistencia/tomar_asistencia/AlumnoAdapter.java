package com.example.sasistencia.tomar_asistencia;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.FechaHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {

    Context context;
    boolean[] mEstadoCheck;
    List<Alumno> mAlumnos = new ArrayList<>();

    public AlumnoAdapter(Context context, int resource, List<Alumno> objects) {
        super(context, resource, objects);
        this.context = context;
        mEstadoCheck = new boolean[objects.size()];
        mAlumnos.addAll(objects);
        Arrays.fill(mEstadoCheck,true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_estudiante_asistencia,parent,false);
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
                    if(s.equals(FechaHelper.getFechaActual(FechaHelper.FECHA_CORTA))){
                        mEstadoCheck[position] = false;
                    }
                }
            }
        }
        checkAsistencia.setChecked(mEstadoCheck[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return mEstadoCheck.length;
    }

    @Override
    public Alumno getItem(int position) {
        return mAlumnos.get(position);
    }

    @Override
    public int getPosition(Alumno item) {
        return mAlumnos.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
