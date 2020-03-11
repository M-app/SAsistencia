package com.example.sasistencia.mensajes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;

import java.util.ArrayList;
import java.util.Map;

public class MensajesAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> grados;
    private Map<String,ArrayList<Alumno>> mapAlumnos;
    private Context context;

    public MensajesAdapter(ArrayList<String> grados, Map<String, ArrayList<Alumno>> mapAlumnos, Context context) {
        this.grados = grados;
        this.mapAlumnos = mapAlumnos;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return grados.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mapAlumnos.get(grados.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return grados.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mapAlumnos.get(grados.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String tituloCategoria = getGroup(i).toString();
        view = LayoutInflater.from(context).inflate(R.layout.item_grupo_mensajes,null);
        TextView txtGrupo = view.findViewById(R.id.nombre_grupo_mensajes);
        txtGrupo.setText(tituloCategoria);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Alumno alumno = (Alumno) getChild(i,i1);
        view = LayoutInflater.from(context).inflate(R.layout.item_estudiante_asistencia,null);
        TextView txtClave = view.findViewById(R.id.txt_clave);
        TextView txtNombres = view.findViewById(R.id.txt_nombres);
        TextView txtApellidos = view.findViewById(R.id.txt_apellidos);
        CheckBox checkEnviarMensaje = view.findViewById(R.id.check_asistencia);
        txtClave.setText(alumno.getClave()+"");
        txtNombres.setText(alumno.getNombres());
        txtApellidos.setText(alumno.getApellidos());
        checkEnviarMensaje.setChecked(true);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
