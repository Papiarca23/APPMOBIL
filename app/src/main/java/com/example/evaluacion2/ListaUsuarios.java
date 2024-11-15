package com.example.evaluacion2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.View;


import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {
    private DatabaseReference Basededatos;
    private ListView listViewUsuarios;
    private ArrayList<String> listaDatosUsuarios;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        Basededatos = FirebaseDatabase.getInstance().getReference().child("usuarios");
        listViewUsuarios = findViewById(R.id.listViewUsuarios);
        listaDatosUsuarios = new ArrayList<>();

        // Configurar el adaptador para el ListView
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDatosUsuarios);
        listViewUsuarios.setAdapter(adaptador);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        // Llamar al método para obtener los datos de todos los usuarios
        obtenerTodosLosUsuarios();
    }

    private void obtenerTodosLosUsuarios() {
        Basededatos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDatosUsuarios.clear(); // Limpiar lista antes de agregar datos

                // Iterar sobre todos los usuarios en la base de datos
                for (DataSnapshot usuarioSnapshot : dataSnapshot.getChildren()) {
                    String nombreUsuario = usuarioSnapshot.child("nombreUsuario").getValue(String.class);
                    String nombreReal = usuarioSnapshot.child("nombreReal").getValue(String.class);
                    String correo = usuarioSnapshot.child("email").getValue(String.class);

                    if (nombreUsuario != null && nombreReal != null && correo != null) {
                        String datosUsuario = "Nombre Usuario: " + nombreUsuario +
                                "\nNombre Real: " + nombreReal +
                                "\nCorreo: " + correo;
                        listaDatosUsuarios.add(datosUsuario);
                    }
                }

                if (listaDatosUsuarios.isEmpty()) {
                    Toast.makeText(ListaUsuarios.this, "No se encontraron usuarios", Toast.LENGTH_SHORT).show();
                }

                // Notificar al adaptador que los datos han cambiado
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListaUsuarios.this, "Error en la consulta: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


