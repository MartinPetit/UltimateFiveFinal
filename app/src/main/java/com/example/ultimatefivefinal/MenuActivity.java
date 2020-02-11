package com.example.ultimatefivefinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    Adapter adapter;
    Adapter2 adapter2;
    List<Matche> models;
    List<User> model;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private DatabaseReference MatcheRef;
    private DatabaseReference UserRef;
    private StorageReference matchePhoto;
    private StorageReference UserPhoto;

    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //firewalle
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        this.configeTollbar();

        MatcheRef = FirebaseDatabase.getInstance().getReference().child("Matches");
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        matchePhoto = FirebaseStorage.getInstance().getReference().child("matche image");
        UserPhoto = FirebaseStorage.getInstance().getReference().child("profile image");


        FirebaseRecyclerOptions<Matche> options = new FirebaseRecyclerOptions.Builder<Matche>()
                .setQuery(MatcheRef,Matche.class)
                .build();

        FirebaseRecyclerOptions<User> options2 = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(UserRef, User.class)
                .build();


        model = new ArrayList<>();
        adapter2 = new Adapter2(model, this);
        viewPager = findViewById(R.id.ViewPagerCard2);
        viewPager.setAdapter(adapter2);
        viewPager.setPadding(65, 0, 65, 0);

        models = new ArrayList<>();
        adapter = new Adapter(models, this);
        viewPager = findViewById(R.id.ViewPagerCard);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(65, 170, 65, 0);

        MatcheRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Matche matche = ds.getValue(Matche.class);
                    models.add(matche);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FireBase", "Failed to read value.", error.toException());
            }
        });

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    model.add(user);
                    adapter2.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBase", "Failed to read value.", databaseError.toException());
            }
        });



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        bottomNavigationView=findViewById(R.id.BoottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.recherche:
                        startActivity(new Intent(getApplicationContext(),ResulteActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(),AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(),MessageActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                return false;



            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser==null) {
            sendUserToLogin();
        }
    }

    private void sendUserToLogin()
    {
        Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_firewalle,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Logout:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                overridePendingTransition(0,0);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private  void configeTollbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }
}