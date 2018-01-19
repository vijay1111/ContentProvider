package com.example.vijay.contentprovider.ContentProvider5;

import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vijay.contentprovider.R;

import java.util.ArrayList;

public class ContentProvider5 extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ContentProvider5";
    private boolean firstTimeLoaded=false;
    private TextView textViewQueryResult;
    private ContentResolver contentResolver;
    private EditText editTextContactName;
    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider5);
        textViewQueryResult = (TextView) findViewById(R.id.textViewQueryResult);
        editTextContactName=(EditText)findViewById(R.id.editTextContactName);
        contentResolver=getContentResolver();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i==1){
            return  new CursorLoader(this,ContactsContract.Contacts.CONTENT_URI,mColumnProjection, null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!=null && cursor.getCount()>0){
            StringBuilder stringBuilderQueryResult=new StringBuilder("");
            while (cursor.moveToNext()){
                stringBuilderQueryResult.append(cursor.getString(0)+" , "+cursor.getString(1)+" , "+cursor.getString(2)+" , "+cursor.getString(3)+"\n");
            }
            textViewQueryResult.setText(stringBuilderQueryResult.toString());
        }else{
            textViewQueryResult.setText("No Contacts in device");
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    private void addContact() {
        ArrayList<ContentProviderOperation> cops=new ArrayList<ContentProviderOperation>();
        cops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,"accountname@gmail.com")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, editTextContactName.getText().toString())
                .build());
        try{
            getContentResolver().applyBatch(ContactsContract.AUTHORITY,cops);
        }catch (Exception exception){
            Log.i(TAG,exception.getMessage());
        }
    }
    private void updateContact(){
        String [] updateValue=editTextContactName.getText().toString().split(" ");
        ContentProviderResult[] result=null;
        String targetString=null;
        String newString=null;
        if(updateValue.length==2){
            targetString=updateValue[0];
            newString=updateValue[1];
            String where= ContactsContract.RawContacts._ID + " = ? ";
            String [] params= new String[] {targetString};
            ContentResolver contentResolver=getContentResolver();
            ContentValues contentValues=new ContentValues();
            contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,newString);
            contentResolver.update(ContactsContract.RawContacts.CONTENT_URI,contentValues, where,params);
        }
    }

    private void removeContacts(){
        String whereClause=ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY+ " = '"+editTextContactName.getText().toString()+"'";
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,whereClause,null);
    }

    private void addContactsViaIntents(){
        String tempContactText=editTextContactName.getText().toString();
        if(tempContactText!=null && !tempContactText.equals("") && tempContactText.length()>0 ){
            Intent intent=new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME,tempContactText);
            startActivity(intent);
        }
    }

    public void view_click(View view) {
        switch (view.getId()) {
            case R.id.buttonLoadData:
                if(firstTimeLoaded==false){
                    getLoaderManager().initLoader(1, null,this);
                    firstTimeLoaded=true;
                }else{
                    getLoaderManager().restartLoader(1,null,this);
                }
                break;
            case R.id.buttonAddContact: addContact();
                break;
            case R.id.buttonRemoveContact:removeContacts();
                break;
            case R.id.buttonUpdateContact: updateContact();
                break;
            default:
                break;
        }
    }
}
