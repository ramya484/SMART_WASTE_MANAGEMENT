package com.example.smart_waste_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etName, etLocation, etRole;
    private Button btnRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        etLocation = findViewById(R.id.et_location);
        etRole = findViewById(R.id.et_role);
        btnRegister = findViewById(R.id.btn_register);
        dbHelper = new DBHelper(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String role = etRole.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || location.isEmpty() || role.isEmpty()) {
                    // Show error message
                    return;
                }
                dbHelper.addUser(username, password, name, location, role);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}