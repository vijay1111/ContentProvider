package com.example.vijay.contentprovider.ContentProvider2;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vijay.contentprovider.R;
//to show particular data
public class ContentProvider2 extends AppCompatActivity {

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};
//    case 1
//    private String mSelectionCluse = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Anil'";

    // case 2 if there is  question mark then mSelectionArguments pass an array
    private String mSelectionCluse = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";
    private String[] mSelectionArguments = new String[]{"Vijay"};


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider2);
        textView = (TextView) findViewById(R.id.textview);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                //case 1
//                mColumnProjection, mSelectionCluse, null, null);
//case 2
                mColumnProjection, mSelectionCluse, mSelectionArguments, null);

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