package com.proyecto.proyecto_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private MyApplication myApplication;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        myApplication = (MyApplication) getApplication();

        // Inicializar los atributos creados
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);


        // Crea un ActionBarDrawerToggle
        // controla el estado de abierto y cerrado de la tarjeta
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_abrir, R.string.nav_cerrar);

        // Se agrega el toggle como un listener del drawer layout
        drawerLayout.addDrawerListener(toggle);

        // Synchronize the toggle's state with the linked DrawerLayout
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new EventosMusicalesFragment())
                    .commit();

            navigationView.setCheckedItem(R.id.nav_eventos);
        }
        // Set a listener for when an item in the NavigationView is selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle the selected item based on its ID
                if (item.getItemId() == R.id.nav_eventos) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new EventosMusicalesFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item.getItemId() == R.id.nav_historial) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new HistorialFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item.getItemId() == R.id.nav_favoritos) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new FavoritosFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item.getItemId() == R.id.nav_crear_evento) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new CrearEventoFragment())
                            .addToBackStack(null)
                            .commit();
                }

                if (item.getItemId() == R.id.nav_cerrarSesion) {
                    myApplication.cerrarSesion();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new EventosMusicalesFragment())
                            .addToBackStack(null)
                            .commit();

                }
                if (item.getItemId() == R.id.nav_inicioSesion) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new InicioSesionFragment())
                            .addToBackStack(null)
                            .commit();
                }
                //para maps, cambiar fragment
                if (item.getItemId() == R.id.nav_maps) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new InicioSesionFragment())
                            .addToBackStack(null)
                            .commit();
                }

                actualizarMenuDrawer();
                // Cierra la tarjeta despues de seleccionar
                drawerLayout.closeDrawers();
                return true;
            }

        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void actualizarMenuDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        boolean sesionActiva = myApplication.isSesionActiva();

        // Elementos visibles solo si hay sesion activa
        menu.findItem(R.id.nav_crear_evento).setVisible(sesionActiva).setEnabled(sesionActiva);
        menu.findItem(R.id.nav_cerrarSesion).setVisible(sesionActiva).setEnabled(sesionActiva);

        // Elementos usuario comun
        menu.findItem(R.id.nav_inicioSesion).setVisible(!sesionActiva).setEnabled(!sesionActiva);
    }
}