package com.proyecto.proyecto_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class InicioSesionFragment extends Fragment {

    ArrayList<Organizacion> listaCuentas;
    Button btn_iniciar_sesion;
    EditText edt_rut_empresa;
    EditText edt_password;
    MyApplication myApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();
        listaCuentas = myApplication.getListaCuentas();
        btn_iniciar_sesion = (Button) view.findViewById(R.id.btn_iniciar_sesion);
        edt_rut_empresa = (EditText) view.findViewById(R.id.edt_rut_empresa);
        edt_password = (EditText) view.findViewById(R.id.edt_password);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String rut = edt_rut_empresa.getText().toString();
                String password = edt_password.getText().toString();
                Organizacion cuentaRecorrida;
                Organizacion cuentaObtenida = null;

                for(int i = 0 ; i < listaCuentas.size() ; i++){
                    cuentaRecorrida = listaCuentas.get(i);
                    if(cuentaRecorrida.getRutEmpresa().equals(rut) && cuentaRecorrida.getPassword().equals(password)) {
                        cuentaObtenida = cuentaRecorrida;
                        break;
                    }
                }
                if(cuentaObtenida != null){
                    myApplication.iniciarSesion(cuentaObtenida);
                    ((MainActivity) requireActivity()).actualizarMenuDrawer();
                    Toast.makeText(requireContext(), "Sesion iniciada correctamente", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    EventosMusicalesFragment eventosMusicalesFragment = new EventosMusicalesFragment();
                    fragmentTransaction.replace(R.id.fragment_container, eventosMusicalesFragment);

                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(requireContext(), "La cuenta no existe\nInicio de sesion rechazado", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}