package com.example.ultimatefivefinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FindjoeursActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView FindFriendsRecyclerView;
    private DatabaseReference userRefernece;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findjoeurs);


        // base de donnes

        userRefernece = FirebaseDatabase.getInstance().getReference().child("Users");


        FindFriendsRecyclerView = findViewById(R.id.find_freinds_RecyclerView);
        FindFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // toolbar
        mToolbar = findViewById(R.id.find_friends_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tous les joueurs");
    }

    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(userRefernece,User.class)
                .build();

        FirebaseRecyclerAdapter<User,FindfreindsViewholder> adapter =
                new FirebaseRecyclerAdapter<User, FindfreindsViewholder>(options) {
                    @NonNull
                    @Override
                    public FindfreindsViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout,viewGroup,false);
                        FindfreindsViewholder viewholder = new FindfreindsViewholder(view);
                        return viewholder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FindfreindsViewholder holder, final int position, @NonNull User model)
                    {


                        holder.userName.setText(model.getPrenom());
                        holder.userVille.setText(model.getVille());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.profileImage);




                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_id = getRef(position).getKey();
                                Intent intent = new Intent(FindjoeursActivity.this,ProfilejoueurActivity.class);
                                intent.putExtra("userId",user_id);
                                startActivity(intent);
                            }
                        });


                    }
                };

        FindFriendsRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    public static class FindfreindsViewholder extends RecyclerView.ViewHolder
    {

        TextView userName,userVille;
        CircleImageView profileImage;

        public FindfreindsViewholder(@NonNull View itemView)
        {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name);
            userVille = itemView.findViewById(R.id.user_profile_ville);
            profileImage = itemView.findViewById(R.id.users_profile_image);

        }
    }


    private void JoeurSearche(String searche)
    {
        FirebaseRecyclerOptions<User> options;
        String query = searche.toLowerCase();
        Query firebaseSerachQuery = userRefernece.orderByChild("prenom").startAt(query).endAt(query + "\uf8ff");

        Log.i("sql2","resultat" + firebaseSerachQuery);
        options  = new FirebaseRecyclerOptions.Builder<User>().setQuery(firebaseSerachQuery,User.class).build();



        FirebaseRecyclerAdapter<User,FindfreindsViewholder> adapter =
                new FirebaseRecyclerAdapter<User, FindfreindsViewholder>(options) {
                    @NonNull
                    @Override
                    public FindfreindsViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout,viewGroup,false);
                        FindfreindsViewholder viewholder = new FindfreindsViewholder(view);
                        return viewholder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FindfreindsViewholder holder, final int position, @NonNull User model)
                    {
                        holder.userName.setText(model.getPrenom());
                        holder.userVille.setText(model.getVille());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.profileImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String user_id = getRef(position).getKey();
                                Intent intent = new Intent(FindjoeursActivity.this,ProfilejoueurActivity.class);
                                intent.putExtra("userId",user_id);
                                startActivity(intent);
                            }
                        });

                    }
                };

        FindFriendsRecyclerView.setAdapter(adapter);
        adapter.startListening();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_joeur, menu);
        MenuItem item = menu.findItem(R.id.menu_serache_joeur);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                JoeurSearche(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                JoeurSearche(newText);
                return false;
            }
        });


        return true;

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_serache_joeur) {
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
}
