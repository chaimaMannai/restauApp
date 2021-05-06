package com.example.restoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText editLogin;
    private  EditText editPass;

    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.editLogin = findViewById(R.id.login);
        this.editPass = findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void goToMenu(View v)
    {
        //Intent i = new Intent(this,MenuActivity.class);
        //startActivity(i);

        String mail= editLogin.getText().toString();
        String pass = editPass.getText().toString();



        if(mail.isEmpty()|| pass.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"champ vide !!",Toast.LENGTH_LONG).show();
        }

        else
        {
            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"User connected",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"Error !"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            });
        }


    }
}