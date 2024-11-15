package com.example.evaluacion2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnEmparejados, btnHistorial, btnEliminarCuenta, btnCerrarSesion, btnActualizarDatos, btnBuscarDispositivo;
    private DatabaseReference Basededatos;
    private String nombreUsuario;  // Para guardar el usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEmparejados = findViewById(R.id.btnEmparejados);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        Basededatos = FirebaseDatabase.getInstance().getReference("usuarios");
        btnActualizarDatos = findViewById(R.id.btnActualizarDatos);
        btnBuscarDispositivo = findViewById(R.id.btnBuscarDispositivo);


        // Recibimos el nombre de usuario desde PantallaLogin
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EliminarCuenta.class);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        btnEmparejados.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Emparejamiento.class);
            intent.putExtra("nombreUsuario", nombreUsuario); // Pasar nombreUsuario
            startActivity(intent);
        });

        btnHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Historial.class);
            startActivity(intent);
        });

        btnActualizarDatos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActualizarDatos.class);
            intent.putExtra("nombreUsuario", nombreUsuario); // Usa tu variable nombreUsuario
            startActivity(intent);
        });

        btnBuscarDispositivo = findViewById(R.id.btnBuscarDispositivo);
        btnBuscarDispositivo.setOnClickListener(v -> buscarDispositivo(nombreUsuario));
    }
    private void buscarDispositivo(String nombreUsuario) {
        Basededatos.child("usuarios").child(nombreUsuario).child("dispositivos")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<String> dispositivosEncontrados = new ArrayList<>();

                            for (DataSnapshot dispositivoSnapshot : dataSnapshot.getChildren()) {
                                String nombreDispositivo = dispositivoSnapshot.child("nombre").getValue(String.class);
                                String claveDispositivo = dispositivoSnapshot.child("clave").getValue(String.class);
                                String estadoDispositivo = dispositivoSnapshot.child("estado").getValue(String.class);

                                if (nombreDispositivo != null && claveDispositivo != null && estadoDispositivo != null) {
                                    dispositivosEncontrados.add(String.format("- %s (Clave: %s, Estado: %s)", nombreDispositivo, claveDispositivo, estadoDispositivo));
                                }
                            }

                            if (dispositivosEncontrados.isEmpty()) {
                                Toast.makeText(MainActivity.this, "No se encontraron dispositivos para este usuario", Toast.LENGTH_SHORT).show();
                            } else {
                                StringBuilder dispositivosEncontradosText = new StringBuilder("Dispositivos emparejados:\n");
                                for (String dispositivo : dispositivosEncontrados) {
                                    dispositivosEncontradosText.append(dispositivo).append("\n");
                                }
                                Toast.makeText(MainActivity.this, dispositivosEncontradosText.toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No se encontraron dispositivos para este usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Error en la consulta: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }





    private void cerrarSesion() {
        Intent intent = new Intent(MainActivity.this, PantallaLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Sesión cerrada automáticamente", Toast.LENGTH_SHORT).show();
    }
}