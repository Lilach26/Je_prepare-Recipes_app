package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailLogin;
    private  EditText passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        //take the file and save in phone
        //sharedPreferences = getPreferences(MODE_PRIVATE);

        SharedPreferences sharedPreferences = getSharedPreferences("MYKEY",0);
        if(sharedPreferences.getString("KeyEmail",null) != null)
        {
            // save the last user without Intent
            emailLogin.setText(sharedPreferences.getString("KeyEmail",null));
            passwordLogin.setText(sharedPreferences.getString("KeyPassword",null));

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            // click on Login button -> buttonLOGIN.performClick();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void LoginFunc(View view) {
        emailLogin = findViewById(R.id.emailLogin);
        String email = emailLogin.getText().toString();

        passwordLogin = findViewById(R.id.passwordLogin);
        String password = passwordLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(LoginActivity.this, "Login success.",
                                Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        SharedPreferences sharedPreferences = getSharedPreferences("MYKEY",0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("KeyEmail", email);
                        editor.putString("KeyPassword", password);
                        editor.apply();

                        Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Login failed.",
                                Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void MoveToRegister(View view) {
        Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}