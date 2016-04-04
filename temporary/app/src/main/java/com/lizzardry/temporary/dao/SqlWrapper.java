package com.lizzardry.temporary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.lizzardry.temporary.dao.beans.Contact;
import com.lizzardry.temporary.dao.tables.ContactTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlWrapper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "retro_android_database.db";
    private static final int DATABASE_VERSION = 3;


    public SqlWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContactTable.DROP_CONTACT_QUERY);
        sqLiteDatabase.execSQL(ContactTable.DROP_INDEX_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_CONTACT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_TRIGGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ContactTable.DROP_CONTACT_QUERY);
        sqLiteDatabase.execSQL(ContactTable.DROP_INDEX_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_CONTACT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_TRIGGER);
    }

    public void drop() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(ContactTable.DROP_CONTACT_QUERY);
        sqLiteDatabase.execSQL(ContactTable.DROP_INDEX_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_CONTACT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_QUERY);
        sqLiteDatabase.execSQL(ContactTable.CREATE_V_TEXT_TABLE_TRIGGER);
    }

    public void insert(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_NAME, contact.getName());
        values.put(ContactTable.COL_COMPANY, contact.getCompany());
        values.put(ContactTable.COL_DEPT, contact.getDepartment());
        values.put(ContactTable.COL_POSITION, contact.getPosition());
        values.put(ContactTable.COL_PHONE, contact.getPhone());
        values.put(ContactTable.COL_ADDRESS, contact.getAddress());
        values.put(ContactTable.COL_DOC, contact.getDocument());
        db.insert(ContactTable.CONTACT_TABLE_NAME, null, values);
    }


    /**
     * never call on UI Thread 느릴듯 rebuild 가
     * @param inputStream
     */
    public void bulkInsert(InputStream inputStream) {
        SQLiteDatabase db = null;
        SQLiteStatement contactInsertStatement;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            db = getWritableDatabase();
            db.beginTransaction();
            contactInsertStatement = db.compileStatement(ContactTable.INSERT_CONTACT_QUERY);
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                String doc = line.replace(",", "").replaceAll("\\s", "").toLowerCase();
                int i = 0;
                for (i=0; i<values.length; i++) {
                    contactInsertStatement.bindString(i+1, values[i]);
                }
                contactInsertStatement.bindString(i+1, doc);
                contactInsertStatement.execute();
            }
            contactInsertStatement.close();
            db.setTransactionSuccessful();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {}
        }
        rebuildDocument();
    }

    private void rebuildDocument() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ContactTable.REBUILD_V_TEXT_TABLE_QUERY);
    }
}
