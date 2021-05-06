package com.example.restoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ListView lst;
    private EditText editPhone;

    ArrayList<String> allResto = new ArrayList<>();
    String selectedResto;
    int phoneNumber;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.lst = (ListView) findViewById(R.id.restoLst);
        //this.editPhone = (EditText) findViewById(R.id.editTextPhone);

        Intent j = getIntent();

        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurant");




        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedResto = ((TextView) view).getText().toString();
                //getPhoneNumber(selectedResto);
                //int x = getPhoneNumber(selectedResto);
                Intent k = new Intent();
                k.setAction(Intent.ACTION_SENDTO);
                k.setData(Uri.parse("smsto:"+getPhoneNumber(selectedResto)));
                startActivity(k);
                //Intent i = new Intent();
                //i.setAction(Intent.ACTION_SENDTO);
                //i.setAction(Intent.ACTION_SEND);
                //i.setData(Uri.parse("smsto:phoneNumber"));
                //startActivity(i);

                //Toast.makeText(MenuActivity.this, "jawek behi.", Toast.LENGTH_LONG).show();
                //Intent i = new Intent();
                //i.setAction(Intent.ACTION_SEND);
                //startActivity(i);



            }
        });



        




    }


    @Override
    protected void onResume() {
        super.onResume();

        //this.allResto.clear();

        this.getRestoName();

        ArrayAdapter ar = new ArrayAdapter(MenuActivity.this, android.R.layout.simple_list_item_1,this.allResto);
        lst.setAdapter(ar);

        ar.notifyDataSetChanged();



    }

    public int getPhoneNumber(final String restoName)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant r = new Restaurant();
                r = snapshot.child(restoName).getValue(Restaurant.class);
                if(r != null)
                {
                    if (r.getName().equals(restoName))
                    {
                        phoneNumber = r.getPhone();
                        Toast.makeText(MenuActivity.this, ""+phoneNumber, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MenuActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });
        return phoneNumber;
    }

    public void getRestoName()
    {
        this.allResto.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Restaurant r = ds.getValue(Restaurant.class);
                    allResto.add(r.getName());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MenuActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void goToAdd(View v)
    {

        Intent i = new Intent(this,AddActivity.class);
        i.putExtra("child_name","Restaurant");
        startActivityForResult(i,1);

    }

    public void goToAddAvis(View v)
    {
        Intent i = new Intent(this,AddAvisActivity.class);
        startActivityForResult(i,2);
    }

    public void SendSMS(View v)
    {
        String phone = editPhone.getText().toString();
        Intent j = new Intent();
        j.setAction(Intent.ACTION_SENDTO);
        j.setData(Uri.parse("smsto:phone"));
        j.putExtra("sms_body", "Bonjour");

        startActivity(j);
    }
}