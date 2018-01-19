package com.example.vijay.contentprovider.ContentProvider4;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vijay.contentprovider.R;

public class ContentProvider4 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private boolean firstTimeLoaded = false;
    private TextView textview;
    private Button loaddata;
    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider4);
        textview = (TextView) findViewById(R.id.textView);
        loaddata = (Button) findViewById(R.id.loaddata);
        loaddata.setOnClickListener(this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //2 check the loaderid
        //after that it will create the seperate worker thread
        if (id == 1) {
            return new CursorLoader(ContentProvider4.this, ContactsContract.Contacts.CONTENT_URI,
                    mColumnProjection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder stringBuilderQueryResult = new StringBuilder("");
            while (cursor.moveToNext()) {
                stringBuilderQueryResult.append(cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(2) + "\n");
            }
            textview.setText(stringBuilderQueryResult.toString());
        } else {
            textview.setText("No Contacts in device");
        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loaddata:
                if (firstTimeLoaded == false) {

                    //1 pass the id so the loader will uniquely identify
                    getLoaderManager().initLoader(1, null, this);
                    firstTimeLoaded = true;
                } else {
                    //to restart the loader
                    getLoaderManager().restartLoader(1, null, this);
                }
                break;
            default:
                break;
        }
    }
}