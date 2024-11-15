package com.example.evaluacion2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {
    private EditText ingNombre, ingUsuario, ingPassword, ingEmail;
    private Button btnRegistrarUsuario;
    private DatabaseReference Basededatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar la referencia a Firebase Database
        Basededatos = FirebaseDatabase.getInstance().getReference();

        ingNombre = findViewById(R.id.ingNombre);
        ingUsuario = findViewById(R.id.ingUsuario);
        ingPassword = findViewById(R.id.ingPassword);
        ingEmail = findViewById(R.id.ingEmail);
        btnRegistrarUsuario = findViewById(R.id.btnRegistarUsuario);

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = ingNombre.getText().toString().trim();
                String usuario = ingUsuario.getText().toString().trim();
                String password = ingPassword.getText().toString().trim();
                String email = ingEmail.getText().toString().trim();


                if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(Registro.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si el usuario ya existe en Firebase
                agregarUsuarioFirebase(usuario, nombre, password, email);
            }
        });
    }

    // Método para agregar usuario a Firebase
    private void agregarUsuarioFirebase(String usuario, String nombre, String password, String email) {
        Usuario nuevoUsuario = new Usuario(usuario, nombre, password, email);
        Basededatos.child("usuarios").child(usuario).setValue(nuevoUsuario)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Registro.this, "Usuario registrado con éxito en Firebase", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad de registro
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Registro.this, "Error al registrar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
