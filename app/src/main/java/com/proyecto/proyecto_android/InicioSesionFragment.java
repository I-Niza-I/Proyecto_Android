package com.proyecto.proyecto_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class InicioSesionFragment extends Fragment {

    Button btn_iniciar_sesion;
    EditText edt_rut_empresa;
    EditText edt_password;
    MyApplication myApplication;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        myApplication = (MyApplication) requireActivity().getApplication();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Organizacion");

        btn_iniciar_sesion = (Button) view.findViewById(R.id.btn_iniciar_sesion);
        edt_rut_empresa = (EditText) view.findViewById(R.id.edt_rut_empresa);
        edt_password = (EditText) view.findViewById(R.id.edt_password);

        insertarDatosDeEjemplo();

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String rutIngresado = edt_rut_empresa.getText().toString().trim();
                String passwordIngresada = edt_password.getText().toString().trim();

                if (rutIngresado.isEmpty() || passwordIngresada.isEmpty()) {
                    Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference organizacionRef = databaseReference.child(rutIngresado);

                organizacionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Organizacion cuentaObtenida = dataSnapshot.getValue(Organizacion.class);

                            if (cuentaObtenida != null && cuentaObtenida.getPassword().equals(passwordIngresada)) {
                                myApplication.iniciarSesion(cuentaObtenida);
                                ((MainActivity) requireActivity()).actualizarMenuDrawer();
                                Toast.makeText(requireContext(), "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();

                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, new EventosMusicalesFragment())
                                        .commit();
                            } else {
                                Toast.makeText(requireContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "El RUT no está registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Ocurrió un error al leer de la base de datos
                        Toast.makeText(requireContext(), "Error al conectar con la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    private void insertarDatosDeEjemplo() {
        // Lista de las cuentas de ejemplo que solías tener
        ArrayList<Organizacion> cuentasDeEjemplo = new ArrayList<>();
        cuentasDeEjemplo.add(new Organizacion("12345678-9", "NombreGenerico", "ejemplo1@gmail.com", "descripcion", "1234"));
        cuentasDeEjemplo.add(new Organizacion("98765432-1", "NombreGenerico2", "ejemplo2@gmail.com", "descripcion2", "4321"));

        for (Organizacion cuenta : cuentasDeEjemplo) {
            // Para cada cuenta, verifica si ya existe en Firebase usando su RUT como clave
            DatabaseReference organizacionRef = databaseReference.child(cuenta.getRutEmpresa());

            organizacionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        // Si no existe, la creamos en Firebase
                        organizacionRef.setValue(cuenta)
                                .addOnSuccessListener(aVoid -> {
                                    // Opcional: Muestra un Toast si quieres saber que se insertó
                                    // Toast.makeText(getContext(), "Cuenta de ejemplo creada: " + cuenta.getRutEmpresa(), Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // Opcional: Muestra un error si falla la inserción
                                    // Toast.makeText(getContext(), "Error al crear cuenta de ejemplo.", Toast.LENGTH_SHORT).show();
                                });
                    }
                    // Si ya existe, no hacemos nada.
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar error de lectura si es necesario
                }
            });
        }
    }
}