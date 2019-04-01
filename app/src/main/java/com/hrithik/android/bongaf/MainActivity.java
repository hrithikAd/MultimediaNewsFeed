package com.hrithik.android.bongaf;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductsAdapter adapter;
    List<Product> productList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String teamcode,room,ann;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        //noti
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull";
                        if (!task.isSuccessful()) {
                            msg ="Failed";
                        }
                      //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        //firebase for board

        // Read from the database

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("board").child("teamcode");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teamcode = (String) dataSnapshot.getValue();

                if (teamcode.equalsIgnoreCase("null")) {
                    ((TextView) findViewById(R.id.teamcode)).setVisibility(View.GONE);
                } else {
                    ((CardView) findViewById(R.id.card2)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.teamcode)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.teamcode)).setText(teamcode);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("board").child("room");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                room = (String) dataSnapshot.getValue();

                if (room.equalsIgnoreCase("null")) {
                    ((TextView) findViewById(R.id.room)).setVisibility(View.GONE);
                } else {
                    ((CardView) findViewById(R.id.card2)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.room)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.room)).setText(room);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("board").child("announcement");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ann = (String) dataSnapshot.getValue();

                if (ann.equalsIgnoreCase("null")) {
                    ((TextView) findViewById(R.id.announcement)).setVisibility(View.GONE);
                } else {
                    ((CardView) findViewById(R.id.card2)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.announcement)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.announcement)).setText(ann);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("products");

        dbProducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                        Product p = productSnapshot.getValue(Product.class);
                        productList.add(p);
                    }
                    Collections.reverse(productList);
                    adapter = new ProductsAdapter(MainActivity.this,productList);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}