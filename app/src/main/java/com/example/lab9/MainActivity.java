package com.example.lab9;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MapEntryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MapEntryDbHelper(this);

        dbHelper.addData("MSC 111 222");

        TextView tv = findViewById(R.id.textView);

        Cursor cursor = dbHelper.readDuom();

        String str = "abc";

        while(cursor.moveToNext()){
            str += cursor.getString(0) + " ";
            str += cursor.getString(1) + " ";
            str += cursor.getString(2) + " ";
            str += cursor.getString(3) + "\n";
        }

        tv.setText(str);

        //dbHelper.wipeData();
    }
}