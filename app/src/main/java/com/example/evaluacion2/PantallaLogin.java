package com.example.evaluacion2;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class PantallaLogin extends AppCompatActivity {
    private EditText inUsuario, inPassword;
    private Button btnLogin, btnRegister;
    private DatabaseReference Basededatos;


    private void inicializarBaseDeDatos() {
        Basededatos = FirebaseDatabase.getInstance().getReference();
        Toast.makeText(this, "Base de datos inicializada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);

        inUsuario = findViewById(R.id.inUsuario);
        inPassword = findViewById(R.id.inPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        Basededatos = FirebaseDatabase.getInstance().getReference("usuarios");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = inUsuario.getText().toString();
                String password = inPassword.getText().toString();

                // Verificar si el usuario existe en Firebase
                Basededatos.child(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Usuario usuarioFirebase = dataSnapshot.getValue(Usuario.class);
                            if (usuarioFirebase.getContrasena().equals(password)) {
                                Intent intent = new Intent(PantallaLogin.this, MainActivity.class);
                                intent.putExtra("nombreUsuario", usuario);  // Pasamos el nombre de usuario
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(PantallaLogin.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PantallaLogin.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PantallaLogin.this, "Error de base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaLogin.this, Registro.class);
                startActivity(intent);
            }
        });
    }
}
