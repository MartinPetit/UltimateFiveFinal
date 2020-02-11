package com.example.ultimatefivefinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter2 extends PagerAdapter {

    private List<User> model;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter2(List<User> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view2, @NonNull Object object) {
        return view2.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view2 = layoutInflater.inflate(R.layout.item2, container, false);

        ImageView imageView2;
        TextView nom, prénom, age;

        imageView2 = view2.findViewById(R.id.image2);
        nom = view2.findViewById(R.id.nom);
        prénom = view2.findViewById(R.id.prenom);
        age = view2.findViewById(R.id.Age);


        nom.setText(model.get(position).getNom());
        prénom.setText(model.get(position).getPrenom());
        age.setText(model.get(position).getAge());
        Picasso.get().load(model.get(position).getImage()).placeholder(R.drawable.profile_image).into(imageView2);

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FicheActivity.class);
                intent.putExtra("param", model.get(position).getVille());
                context.startActivity(intent);
                // finish();
            }
        });



        container.addView(view2, 0);

        return view2;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}
