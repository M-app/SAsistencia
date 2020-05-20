package com.example.sasistencia.mensajes;


import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.sasistencia.R;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.domain.NombresGrados;
import com.example.sasistencia.mensajes.MensajesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MensajesFragment extends Fragment implements MensajesView{

    private ExpandableListView listaExpandible;
    private MensajesAdapter mAdapter;
    private ArrayList<String> grados;
    private Map<String,ArrayList<Alumno>> alumnos;
    Button btnNotificar;


    private List<Alumno> primeroBasico;
    private List<Alumno> segundoBasico;
    private List<Alumno> terceroBasico;
    private List<Alumno> cuartoBiologicas;
    private List<Alumno> cuartoCCyLL;
    private List<Alumno> cuartoDisenio;
    private List<Alumno> cuartoCompu;
    private List<Alumno> quintoCCyLL;
    private List<Alumno> quintoBiologicas;
    private List<Alumno> quintoCompu;
    private List<Alumno> cuartoMagis;
    private List<Alumno> quintoMagisterio;
    private List<Alumno> sextoMagisterio;

    final int ENVIAR_MENSAJE_SMS = 1;

    MensajesPresenter presenter;

    public MensajesFragment() {
        // Required empty public constructor
    }

    @Override
    public void llenarListaGrados(List<String> grados) {
        if (this.grados.size() > 0)
            this.grados.clear();
        this.grados.addAll(grados);
        llenarFaltantes();
        configExpandibleList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mensajes, container, false);
        listaExpandible = view.findViewById(R.id.lista_expandible_mensajes);
        btnNotificar = view.findViewById(R.id.btn_notificar);
        grados = new ArrayList<>();
        alumnos = new HashMap<>();

        presenter = new MensajesPresenterImpl(this);

        presenter.getGrados();

        initListasAlumnos();

        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Alumno alumno : primeroBasico)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : segundoBasico)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : terceroBasico)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : cuartoCCyLL)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : cuartoBiologicas)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : cuartoDisenio)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : cuartoCompu)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : quintoCCyLL)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : quintoBiologicas)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : quintoCompu)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : cuartoMagis)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : quintoMagisterio)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
                for (Alumno alumno : sextoMagisterio)
                    enviarMensaje(alumno.getTelefono()+"",alumno.getNombres() + " " + alumno.getApellidos());
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }



    private void llenarFaltantes(){
        presenter.getAlumnosPrimeroBasico();
        presenter.getAlumnosSegundoBasico();
        presenter.getAlumnosTerceroBasico();
        presenter.getAlumnosCuartoBiologicas();
        presenter.getAlumnosCuartoCCyLL();
        presenter.getAlumnosCuartDisenio();
        presenter.getAlumnosCuartoCompu();
        presenter.getAlumnosQuintoCCyLL();
        presenter.getAlumnosQuintoBiologicas();
        presenter.getAlumnosQuintoCompu();
        presenter.getAlumnosCuartoMagis();
        presenter.getAlumnosQuintoMagis();
        presenter.getAlumnosSextoMagis();
    }

    private void initListasAlumnos(){
        primeroBasico = new ArrayList<>();
        segundoBasico = new ArrayList<>();
        terceroBasico = new ArrayList<>();
        cuartoBiologicas = new ArrayList<>();
        cuartoCCyLL = new ArrayList<>();
        cuartoDisenio = new ArrayList<>();
        cuartoCompu = new ArrayList<>();
        quintoCCyLL = new ArrayList<>();
        quintoBiologicas = new ArrayList<>();
        quintoCompu = new ArrayList<>();
        cuartoMagis = new ArrayList<>();
        quintoMagisterio = new ArrayList<>();
        sextoMagisterio = new ArrayList<>();
    }

    private void configExpandibleList(){
        alumnos.put(grados.get(0),(ArrayList<Alumno>) cuartoCCyLL);
        alumnos.put(grados.get(1),(ArrayList<Alumno>) cuartoCompu);
        alumnos.put(grados.get(2),(ArrayList<Alumno>) cuartoDisenio);
        alumnos.put(grados.get(3),(ArrayList<Alumno>) cuartoBiologicas);
        alumnos.put(grados.get(4),(ArrayList<Alumno>) cuartoMagis);
        alumnos.put(grados.get(5),(ArrayList<Alumno>) primeroBasico);
        alumnos.put(grados.get(6),(ArrayList<Alumno>) quintoBiologicas);
        alumnos.put(grados.get(7),(ArrayList<Alumno>) quintoCCyLL);
        alumnos.put(grados.get(8),(ArrayList<Alumno>) quintoCompu);
        alumnos.put(grados.get(9),(ArrayList<Alumno>) quintoMagisterio);
        alumnos.put(grados.get(10),(ArrayList<Alumno>) segundoBasico);
        alumnos.put(grados.get(11),(ArrayList<Alumno>) sextoMagisterio);
        alumnos.put(grados.get(12),(ArrayList<Alumno>) terceroBasico);
        mAdapter = new MensajesAdapter(grados,alumnos,getActivity());
        listaExpandible.setAdapter(mAdapter);
    }
    private void enviarMensaje(String numero, String nombre){
        if(!checkPermission(Manifest.permission.SEND_SMS)){
            ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.SEND_SMS,
                                                            Manifest.permission.READ_PHONE_STATE},ENVIAR_MENSAJE_SMS);
        }
        if (checkPermission(Manifest.permission.SEND_SMS) && checkPermission(Manifest.permission.READ_PHONE_STATE)){
            SmsManager manager = SmsManager.getDefault();
            PendingIntent sendPI;
            String SENT = "SMS_SENT";
            sendPI = PendingIntent.getBroadcast(getActivity(),0,new Intent(SENT),0);
            String antesDe = "Buenos días, Colegio Tecnológico de Huehuetenango le saluda, notifica e informa "
                    + "por este medio que su hijo/a ";
            String mensajeSuspencionClases = "El Colegio Tecnológico de Huehuetenango, en atención a las instrucciones " +
                    "brindadas por el Presidente de la República Dr. Alejandro Giammattei. Informamos a la comunidad educativa " +
                    "que se SUSPENDEN todas las actividades académicas y formativas PRESENCIALES a partir del lunes 16 de marzo " +
                    "de 2020. Exhortamos a toda la población a seguir todas las recomendaciones brindadas por el MSPAS, " +
                    "así como del gobierno central. ";
            ArrayList<String> mensajesArray = manager.divideMessage(mensajeSuspencionClases);
            try{
                manager.sendMultipartTextMessage(numero,null,mensajesArray,null,null);

                Toast.makeText(getActivity(),"Mensaje Enviado",Toast.LENGTH_LONG).show();
            }catch (Exception ex){
                Toast.makeText(getActivity(),"Error: " + ex.getMessage(),Toast.LENGTH_LONG).show();

            }
            //manager.sendTextMessage(numero,null,mensajeSuspencionClases,sendPI,null);
        }else{
            Toast.makeText(getActivity(),"Permiso Denegado",Toast.LENGTH_LONG);
        }
    }

    private void enviarSMS(String numero, String mensaje){
        String ENVIADO = "SMS_SENT";
        String ENTREGADO = "SMS_DELIVERED";

        PendingIntent enviadoPI = PendingIntent.getBroadcast(getActivity(),0,
                new Intent(ENVIADO),0);

        PendingIntent entregadoPI = PendingIntent.getBroadcast(getActivity(),0,
                new Intent(ENTREGADO),0);

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case -1:
                        Toast.makeText(getActivity(),"Mensaje Enviado",
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getActivity(),"Fallo Genérico",
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getActivity(), "No hay servicio",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getActivity(), "PDU null",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getActivity(), "Radio apagada",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(ENVIADO));

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case -1: // getActivity.RESULT_OK
                        Toast.makeText(getActivity(),"MENSAJE ENTREGADO",Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 0: // getAcitivity.RESULT_CANCELED
                        Toast.makeText(getActivity(),"MENSAJE NO ENTREGADO", Toast.LENGTH_SHORT)
                                .show();
                }
            }
        },new IntentFilter(ENTREGADO));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numero,null,mensaje,enviadoPI,entregadoPI);
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(getActivity(),permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void llenarPrimeroBasico(List<Alumno> alumnos) {
        if (primeroBasico.size() > 0)
            primeroBasico.clear();
        primeroBasico.addAll(alumnos);
    }

    @Override
    public void llenarSegundoBasico(List<Alumno> alumnos) {
        if (segundoBasico.size() > 0)
            segundoBasico.clear();
        segundoBasico.addAll(alumnos);
    }

    @Override
    public void llenarTerceroBasico(List<Alumno> alumnos) {
        if (terceroBasico.size() > 0)
            terceroBasico.clear();
        terceroBasico.addAll(alumnos);
    }

    @Override
    public void llenarCuartoBiologicas(List<Alumno> alumnos) {
        if (cuartoBiologicas.size() > 0)
            cuartoBiologicas.clear();
        cuartoBiologicas.addAll(alumnos);
    }

    @Override
    public void llenarCuartoCCyLL(List<Alumno> alumnos) {
        if (cuartoCCyLL.size() > 0)
            cuartoCCyLL.clear();
        cuartoCCyLL.addAll(alumnos);
    }

    @Override
    public void llenarCuartoDisenio(List<Alumno> alumnos) {
        if (cuartoDisenio.size() > 0)
            cuartoDisenio.clear();
        cuartoDisenio.addAll(alumnos);
    }

    @Override
    public void llenarCuartoCompu(List<Alumno> alumnos) {
        if (cuartoCompu.size() > 0)
            cuartoCompu.clear();
        cuartoCompu.addAll(alumnos);
    }

    @Override
    public void llenarQuintoCCyLL(List<Alumno> alumnos) {
        if (quintoCCyLL.size() > 0)
            quintoCCyLL.clear();
        quintoCCyLL.addAll(alumnos);
    }

    @Override
    public void llenarQuintoBiologicas(List<Alumno> alumnos) {
        if (quintoBiologicas.size() > 0)
            quintoBiologicas.clear();
        quintoBiologicas.addAll(alumnos);
    }

    @Override
    public void llenarQuintoCompu(List<Alumno> alumnos) {
        if (quintoCompu.size() > 0)
            quintoCompu.clear();
        quintoCompu.addAll(alumnos);
    }

    @Override
    public void llenarCuartoMagisterio(List<Alumno> alumnos) {
        if (cuartoMagis.size() > 0)
            cuartoMagis.clear();
        cuartoMagis.addAll(alumnos);
    }

    @Override
    public void llenarQuintoMagisterio(List<Alumno> alumnos) {
        if (quintoMagisterio.size() > 0)
            quintoMagisterio.clear();
        quintoMagisterio.addAll(alumnos);
    }

    @Override
    public void llenarSextoMagisterio(List<Alumno> alumnos) {
        if (sextoMagisterio.size() > 0)
            sextoMagisterio.clear();
        sextoMagisterio.addAll(alumnos);
    }
}
