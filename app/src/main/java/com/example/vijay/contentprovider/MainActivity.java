package com.example.vijay.contentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vijay.contentprovider.ContentProvider1.ContentProvider1;
import com.example.vijay.contentprovider.ContentProvider2.ContentProvider2;
import com.example.vijay.contentprovider.ContentProvider3.ContentProvider3;
import com.example.vijay.contentprovider.ContentProvider4.ContentProvider4;
import com.example.vijay.contentprovider.ContentProvider5.ContentProvider5;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void view_click(View view) {
        switch (view.getId()) {
            case R.id.content_provider1:
                startActivity(new Intent(getApplicationContext(), ContentProvider1.class));
                break;
            case R.id.content_provider2:
                startActivity(new Intent(getApplicationContext(), ContentProvider2.class));
                break;
            case R.id.content_provider3:
                startActivity(new Intent(getApplicationContext(), ContentProvider3.class));
                break;
            case R.id.content_provider4:
                startActivity(new Intent(getApplicationContext(), ContentProvider4.class));
                break;
            case R.id.content_provider5:
                startActivity(new Intent(getApplicationContext(), ContentProvider5.class));
                break;




        }
    }
}
