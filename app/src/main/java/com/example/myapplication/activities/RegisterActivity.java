package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPassEditText;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void RegisterFunc(View view)
    {
        //get input from user, and convert it to string for saving it into objects
        emailEditText = findViewById(R.id.emailEditText);
        String email = emailEditText.getText().toString();

        passwordEditText = findViewById(R.id.passwordEditText);
        String password = passwordEditText.getText().toString();

        nameEditText = findViewById(R.id.nameEditText);
        String name = nameEditText.getText().toString();

        confirmPassEditText = findViewById(R.id.confirmPassEditText);
        String confirmPassword = confirmPassEditText.getText().toString();

        if (!email.equals("") && !password.equals("") && confirmPassword.equals(password))
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "Register success.",
                                        Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();

                                //get user registration info, save if as person object and push it fire store database
                                Person person = new Person(name, email);
                                HashMap<String, Person> map = new HashMap<>();
                                map.put(uid, person);
                                db.collection("Users").document(uid).set(map);
                                db.collection("Users").document(uid).collection("Shopping List").document("Ingredients").set(new HashMap<String,Object>());
                                db.collection("Users").document(uid).collection("Internet Links").document("Links").set(new HashMap<String,Object>());
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Register failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(RegisterActivity.this, "Fill fields / Unmatched passwords",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void backToLogin(View view)
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}