package com.example.evaluacion2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActualizarDatos extends AppCompatActivity {
    private EditText ingNuevoNombre, ingNuevaContrasena;
    private Button btnActualizar;
    private DatabaseReference Basededatos;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);

        ingNuevoNombre = findViewById(R.id.ingNuevoNombre);
        ingNuevaContrasena = findViewById(R.id.ingNuevaContrasena);
        btnActualizar = findViewById(R.id.btnActualizar);
        Basededatos = FirebaseDatabase.getInstance().getReference("usuarios");

        nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        btnActualizar.setOnClickListener(v -> actualizarDatos());
    }

    private void actualizarDatos() {
        String nuevoNombre = ingNuevoNombre.getText().toString();
        String nuevaContrasena = ingNuevaContrasena.getText().toString();

        if (!nuevoNombre.isEmpty()) {
            Basededatos.child(nombreUsuario).child("nombreUsuario").setValue(nuevoNombre);
        }

        if (!nuevaContrasena.isEmpty()) {
            Basededatos.child(nombreUsuario).child("contrasena").setValue(nuevaContrasena);
        }

        Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}
