package com.example.gpl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter <adapter.viewholder>{
    Context context;
    ArrayList<String> al;

    public adapter(Context context, ArrayList<String> al){
        this.context = context;
        this.al = al;
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        TextView name;
        ImageView person, add;
        ImageButton cancel;
        EditText enter;
        Button btn;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            person = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView2);
            cancel = itemView.findViewById(R.id.cancel);
            add = itemView.findViewById(R.id.img_add);

        }

    }

    @NonNull
    @Override
    public adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.viewholder holder, int position) {
        holder.name.setText(al.get(position));
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(context);
                db.delete(al.get(position));
                al.remove(position);
                notifyDataSetChanged();
            }
        });

        if(al.get(position).equals("ADD NEW")){
            holder.cancel.setVisibility(View.GONE);
            holder.person.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
            holder.name.setVisibility(View.GONE);
        }
        else{
            holder.person.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.VISIBLE);
            holder.name.setVisibility(View.VISIBLE);

        }
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog dialog = new dialog();
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog");
            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    void setName(String name){
        al.remove(al.size()-1);
        al.add(name);
        al.add("ADD NEW");

        Database db = new Database(context);
        db.insert(name);
        notifyDataSetChanged();
    }


}