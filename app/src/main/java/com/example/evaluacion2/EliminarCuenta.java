package com.example.evaluacion2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EliminarCuenta extends AppCompatActivity {
    private EditText ingPasswordBorrar;
    private Button btnEliminarCuenta;
    private DatabaseReference Basededatos;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cuenta);

        Basededatos = FirebaseDatabase.getInstance().getReference("usuarios");
        ingPasswordBorrar = findViewById(R.id.ingPasswordBorrar);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);

        // Recibimos el nombre de usuario
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordInput = ingPasswordBorrar.getText().toString();
                eliminarCuenta(passwordInput);
            }
        });
    }

    private void eliminarCuenta(String passwordInput) {
        // Verificar si el usuario existe y la contraseña es correcta
        Basededatos.child(nombreUsuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                String contrasenaActual = task.getResult().child("contrasena").getValue(String.class);
                if (contrasenaActual != null && contrasenaActual.equals(passwordInput)) {
                    // Eliminar el usuario de la base de datos
                    Basededatos.child(nombreUsuario).removeValue().addOnCompleteListener(taskEliminar -> {
                        if (taskEliminar.isSuccessful()) {
                            Toast.makeText(EliminarCuenta.this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();
                            cerrarSesionYSalir();
                        } else {
                            Toast.makeText(EliminarCuenta.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(EliminarCuenta.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EliminarCuenta.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cerrarSesionYSalir() {
        // Cerrar sesión y redirigir a la pantalla de login
        Intent intent = new Intent(EliminarCuenta.this, PantallaLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
