package com.lizzardry.temporary.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.lizzardry.temporary.dao.beans.Contact;
import com.lizzardry.temporary.dao.tables.ContactTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SqlWrapper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "retro_android_database.db";
    private static final int DATABASE_VERSION = 3;
    private static SqlWrapper instance;
    public static synchronized SqlWrapper getInstance(Context context) {
        if (instance == null) {
            instance = new SqlWrapper(context.getApplicationContext());
        }
        return instance;
    }

    private SqlWrapper(Context context) {
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

    public List<Contact> selectFromDocuments(String criterions) {
        List<Contact> results = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        if (TextUtils.isEmpty(criterions)) {
            return results;
        }
        String criterionString;
        String[] criterionList = criterions.split("\\s");
        for (int i=0; i<criterionList.length; i++) {
            String criterion = ContactTable.COL_DOC + " LIKE '%" + criterionList[i]+ "%'";
            criterionList[i] = criterion;
        }
        if (criterionList.length == 1) {
            criterionString = criterionList[0];
        } else if (criterionList.length > 1) {
            criterionString = TextUtils.join(" AND ", criterionList);
        } else {
            return results;
        }

        Cursor cursor = sqLiteDatabase.rawQuery(
                String.format(ContactTable.SELECT_WITH_STRING_TICKLE_QUERY_FORMAT, criterionString),
                null
        );
        try {
            while (cursor.moveToNext()) {
                int nameColIdx = cursor.getColumnIndex(ContactTable.COL_NAME);
                int companyColIdx = cursor.getColumnIndex(ContactTable.COL_COMPANY);
                int deptColIdx = cursor.getColumnIndex(ContactTable.COL_DEPT);
                int positionColIdx = cursor.getColumnIndex(ContactTable.COL_POSITION);
                int phoneColIdx = cursor.getColumnIndex(ContactTable.COL_PHONE);
                int addressColIdx = cursor.getColumnIndex(ContactTable.COL_ADDRESS);
                results.add(
                        new Contact(
                                cursor.getString(nameColIdx),
                                cursor.getString(companyColIdx),
                                cursor.getString(deptColIdx),
                                cursor.getString(positionColIdx),
                                cursor.getString(phoneColIdx),
                                cursor.getString(addressColIdx)
                        )
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return results;
    }


    /**
     * never call on UI Thread 느릴듯 rebuild 가 -> 오 안느린데?
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
                String doc = line.replace(",", " ").toLowerCase();
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
        rebuildDocumentIndex();
    }

    private void rebuildDocumentIndex() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ContactTable.REBUILD_V_TEXT_TABLE_QUERY);
    }
}
