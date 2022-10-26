package com.example.todolist;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> toDoList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = new ArrayList<>();
        loadDataToList(toDoList);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_layout, toDoList);
        listView = findViewById(R.id.id_list_view);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                if (textView.getText().toString().equalsIgnoreCase("Feed shrimps!")) {
                    displayVideo();
                }
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        editText = findViewById(R.id.id_edit_text);
    }

    private void displayVideo() {
        String url = "https://www.youtube.com/watch?v=BPoDuf3eTvc";
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));

        startActivity(webIntent);
    }

    private void loadDataToList(List<String> toDoList) {
        toDoList.add("Do some exercise!");
        toDoList.add("Do some courses!");
        toDoList.add("Feed shrimps!");
    }

    public void addItemToList(View view) {
        View parentLayout = findViewById(android.R.id.content);
        if (editText.getText().toString().equalsIgnoreCase("")) {
            showSnackbar(parentLayout, "I know that You are lazy, but You can't add empty task :D");
        } else {
            toDoList.add(editText.getText().toString());
            showSnackbar(parentLayout, editText.getText().toString() + " has been added to list!");
            arrayAdapter.notifyDataSetChanged();


            editText.setText("");
        }
    }

    private void showSnackbar(View parentLayout, String text) {
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}