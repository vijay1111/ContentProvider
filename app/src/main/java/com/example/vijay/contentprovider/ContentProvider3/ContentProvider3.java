package com.example.vijay.contentprovider.ContentProvider3;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vijay.contentprovider.R;

//sorted order
public class ContentProvider3 extends AppCompatActivity {
    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    private TextView textView;
    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider3);
        textView = (TextView) findViewById(R.id.textview);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection, null, null, mOrderBy);

        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder stringBuilderQueryResult = new StringBuilder("");
            while (cursor.moveToNext()) {
                stringBuilderQueryResult.append(cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(2) + "\n");
            }
            textView.setText(stringBuilderQueryResult.toString());
        } else {
            textView.setText("No Contacts in device");
        }
    }
}