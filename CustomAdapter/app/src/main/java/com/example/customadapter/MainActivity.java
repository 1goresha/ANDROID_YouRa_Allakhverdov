package com.example.customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.list_view);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
        arrayList.add("four");
        arrayList.add("five");
        arrayList.add("six");
        arrayList.add("seven");
        arrayList.add("eight");
        arrayList.add("nine");
        arrayList.add("ten");
        arrayList.add("eleven");
        arrayList.add("twelve");
        arrayList.add("thirteen");
        arrayList.add("fourteen");
        arrayList.add("fifteen");

        MyAdapter myAdapter = new MyAdapter(arrayList);

        listView.setAdapter(myAdapter);
    }
}
