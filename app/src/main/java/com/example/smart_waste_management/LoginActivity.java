package com.example.smart_waste_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        dbHelper = new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Incorrect username/password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (dbHelper.authenticateUser(username, password)) {
                    String role = dbHelper.getUserRole(username);
                    Intent intent;
                    if (role.equals("collector")) {
                        intent = new Intent(LoginActivity.this, CollectorMainActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, UserMainActivity.class);
                    }
                    intent.putExtra("username",username);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, " Not Registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
  }
    }
