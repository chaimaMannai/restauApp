package com.example.restoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editCity;
    EditText editPhone;

    DatabaseReference reff;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        this.editName = findViewById(R.id.editName);
        this.editCity = findViewById(R.id.editVille);
        this.editPhone = findViewById(R.id.editPhone);

        Intent i = getIntent();

        reff = FirebaseDatabase.getInstance().getReference().child(i.getStringExtra("child_name"));

        restaurant= new Restaurant();








    }

    public void CommitData(View v)
    {
        String name = editName.getText().toString();
        String city = editCity.getText().toString();
        int phone = Integer.parseInt(editPhone.getText().toString());

        restaurant.setName(name);
        restaurant.setCity(city);
        restaurant.setPhone(phone);

        reff.child(name).setValue(restaurant);

        Toast.makeText(getApplicationContext(), "Data inserted !", Toast.LENGTH_LONG).show();

        finish();
    }
}