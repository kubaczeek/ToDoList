package com.example.todolist;

import static com.example.todolist.R.menu.menu_main;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            View parentLayout = findViewById(android.R.id.content);

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar snackbar = Snackbar
                        .make(parentLayout, "Do you want delete:\"" + toDoList.get(i) + "\"?", Snackbar.LENGTH_LONG * 5)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Item \"" + toDoList.get(i) + "\" has been deleted!",
                                        Toast.LENGTH_LONG).show();
                                arrayAdapter.remove(toDoList.get(i));
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
                snackbar.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                TextView textView = (TextView) view;
                if (textView.getText().toString().equalsIgnoreCase("Feed shrimps!")) {
                    displayVideo();
                }
                return false;
            }
        });

        editText = findViewById(R.id.id_edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        View parentLayout = findViewById(android.R.id.content);
        if (id == R.id.idOptionMenu1) {
            showSnackbar(parentLayout, "Type data in input and click ADD button!");
            return true;
        } else if (id == R.id.idOptionMenu2) {
            showSnackbar(parentLayout, "Click on item, which You want to delete!");
            return true;
        } else if (id == R.id.idOptionMenu3) {
            Intent intent = new Intent(MainActivity.this, Logs.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayVideo() {
        String url = "https://www.youtube.com/watch?v=BPoDuf3eTvc";
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));

        startActivity(webIntent);
    }

    private void loadDataToList(List<String> toDoList) {
        toDoList.add("DO SOME EXERCISE");
        toDoList.add("DO SOME COURSES!");
        toDoList.add("FEED SHRIMPS!");
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