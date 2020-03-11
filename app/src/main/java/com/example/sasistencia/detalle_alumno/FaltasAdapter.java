package com.example.sasistencia.detalle_alumno;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sasistencia.R;

import java.util.List;

public class FaltasAdapter extends ArrayAdapter<String> {

    TextView txtFalta;

    public FaltasAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_falta_alumno,parent,false);
        }
        txtFalta = convertView.findViewById(R.id.txt_detalle_alumno_falta);
        String falta = getItem(position);
        txtFalta.setText(falta);
        return convertView;
    }
}
