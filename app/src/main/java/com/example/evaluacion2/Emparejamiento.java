package com.example.evaluacion2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Emparejamiento extends AppCompatActivity {
    private EditText ingNombreDisp, ingClaveUnica;
    private Button btnParearDisp;
    private ListView listaDisp;
    private ArrayList<String> dispPareados = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private DatabaseReference Basededatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emparejamiento);

        Basededatos = FirebaseDatabase.getInstance().getReference();
        ingNombreDisp = findViewById(R.id.ingNombreDisp);
        ingClaveUnica = findViewById(R.id.ingClaveUnica);
        btnParearDisp = findViewById(R.id.btnParearDisp);
        listaDisp = findViewById(R.id.listaDisp);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dispPareados);
        listaDisp.setAdapter(adapter);


        btnParearDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceName = ingNombreDisp.getText().toString().trim();
                String deviceKey = ingClaveUnica.getText().toString().trim();

                if (!deviceName.isEmpty() && !deviceKey.isEmpty()) {
                    String deviceInfo = "Dispositivo: " + deviceName + " (Clave: " + deviceKey + ")";
                    dispPareados.add(deviceInfo);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(Emparejamiento.this, "Dispositivo emparejado", Toast.LENGTH_SHORT).show();

                    agregarDispositivoFirebase(deviceName, deviceKey);

                    // Limpiar los campos después de emparejar
                    ingNombreDisp.setText("");
                    ingClaveUnica.setText("");
                } else {
                    Toast.makeText(Emparejamiento.this, "Por favor, ingresa nombre y clave del dispositivo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listaDisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dispositivo = dispPareados.get(position);
                Toast.makeText(Emparejamiento.this, "Acción realizada sobre " + dispositivo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarDispositivoFirebase(String nombre, String clave) {
        // Obtener el nombre del usuario desde el intent
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            Toast.makeText(Emparejamiento.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto dispositivo
        Dispositivo nuevoDispositivo = new Dispositivo(nombre, clave, "activo");

        // Generar un ID único en Firebase
        String dispositivoId = Basededatos.child("usuarios").child(nombreUsuario).child("dispositivos").push().getKey();

        // Insertar en la base de datos bajo el nodo del usuario
        if (dispositivoId != null) {
            Basededatos.child("usuarios").child(nombreUsuario).child("dispositivos").child(dispositivoId).setValue(nuevoDispositivo)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Emparejamiento.this, "Dispositivo guardado en Firebase", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Emparejamiento.this, "Error al guardar en Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}