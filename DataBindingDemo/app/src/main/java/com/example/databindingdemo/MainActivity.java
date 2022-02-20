package com.example.databindingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.databindingdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setBook(createBook());

        MainActivityButtonHandler mainActivityButtonHandler = new MainActivityButtonHandler(this);
        activityMainBinding.setButtonHandler(mainActivityButtonHandler);


    }

    public Book createBook(){

        Book book = new Book();
        book.setTitle("Hamlet");
        book.setAuthor("Shakespeare");
        return book;
    }


    public class MainActivityButtonHandler{

        Context context;

        public MainActivityButtonHandler(Context context){
            this.context = context;
        }

        public void onOkClick(View view){
            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
        }

        public void onCancelClick(View view){
            Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
