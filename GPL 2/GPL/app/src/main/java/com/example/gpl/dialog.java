package com.example.gpl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.InsetDialogOnTouchListener;

import java.util.ArrayList;

public class dialog extends AppCompatDialogFragment {
    EditText name;
    DialogInterface listener;
    Spinner spin;
    ArrayList<String> al = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);
        adapter.viewholder viewholder = new adapter.viewholder(view);

        name = view.findViewById(R.id.name_edittext);
        spin = view.findViewById(R.id.spinner);

        Database db = new Database(getContext());
        Cursor cur = db.view();
        al.add("");
        if(cur.getCount() > 0){
            while (cur.moveToNext()){
                al.add(cur.getString(1));
            }
        }

        ArrayAdapter ad
                = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                al);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spin.setAdapter(ad);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name.setText(al.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Enter name")
                .setNegativeButton("No", null)
                .setPositiveButton("Add", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialogInterface, int i) {
                        listener.applyText(name.getText().toString());
                    }
                });

        return  builder.create();

    }
    public interface DialogInterface{
        void applyText(String name);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DialogInterface) context;
        }
        catch (ClassCastException e){

        }
    }
}
