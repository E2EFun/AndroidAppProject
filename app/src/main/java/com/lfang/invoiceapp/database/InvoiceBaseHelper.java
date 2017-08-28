package com.lfang.invoiceapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lfang.invoiceapp.database.InvoiceSchema.*;

/**
 * Created by lfang on 8/22/17.
 */

public class InvoiceBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "invoiceBase.db";

    public InvoiceBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + InvoiceTable.NAME + "(" +
                    "_id integer primary key autoincrement, " +
                    InvoiceTable.Cols.UUID + " TEXT , " +
                    InvoiceTable.Cols.Name + " TEXT , " +
                    InvoiceTable.Cols.Email + " TEXT , " +
                    InvoiceTable.Cols.DueDate + " TEXT , " +
                    InvoiceTable.Cols.Total + " TEXT " +
                    ")"
        );
        db.execSQL("create table " + ItemTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ItemTable.Cols.Description + " TEXT , " +
                ItemTable.Cols.Amount + " TEXT , " +
                ItemTable.Cols.UUID + " TEXT " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + InvoiceTable.NAME + ", " + ItemTable.NAME);
    }
}
