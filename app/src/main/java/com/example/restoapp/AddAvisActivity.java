package com.example.restoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAvisActivity extends AppCompatActivity {

    EditText editRestoName;
    EditText editNote;

    DatabaseReference reff;

    Avis avis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avis);

        this.editRestoName = findViewById(R.id.editRestoName);
        this.editNote = findViewById(R.id.editNote);


        Intent i = getIntent();

        reff = FirebaseDatabase.getInstance().getReference("Avis");

        avis = new Avis();
    }

    public void commitData(View v)
    {
        String restoName = editRestoName.getText().toString();
        String note = editNote.getText().toString();


        avis.setRestoName(restoName);
        avis.setNote(note);

        reff.child(restoName).setValue(avis);

        Toast.makeText(getApplicationContext(), "Data inserted !", Toast.LENGTH_LONG).show();
        finish();

    }
}