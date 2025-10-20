package com.proyecto.proyecto_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Perfil extends Fragment {

    private MyApplication myApplication;
    private TextView txt_Nombre, txt_Descripcion, txt_Correo;
    private ImageView imgLogo;
    private Button btnModificar;
    private Organizacion cuentaLogueada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();

        txt_Nombre = view.findViewById(R.id.txt_nombre);
        txt_Descripcion = view.findViewById(R.id.txt_descripcion);
        txt_Correo = view.findViewById(R.id.txt_correo);
        btnModificar = view.findViewById(R.id.btn_modificar);

        cargarDatosPerfil();

        // (Opcional) Funcionalidad para el botón de modificar
        btnModificar.setOnClickListener(v -> {
            // Aquí puedes navegar a otro fragmento para editar el perfil
            // o cambiar los TextViews por EditTexts para permitir la edición
            Toast.makeText(getContext(), "Funcionalidad de modificar no implementada.", Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    private void cargarDatosPerfil() {
        // ✅ Obtener la cuenta que ha iniciado sesión desde MyApplication
        cuentaLogueada = myApplication.getCuentaLogueada();

        if (cuentaLogueada != null) {
            txt_Nombre.setText(cuentaLogueada.getNombre());
            txt_Descripcion.setText(cuentaLogueada.getDescripcion());
            txt_Correo.setText(cuentaLogueada.getCorreo());
        } else {
            txt_Nombre.setText("Sin sesión iniciada");
            txt_Descripcion.setText("Inicia sesión para ver tu perfil.");
            txt_Correo.setText("");
            Toast.makeText(getContext(), "Por favor, inicia sesión.", Toast.LENGTH_LONG).show();
        }
    }
}