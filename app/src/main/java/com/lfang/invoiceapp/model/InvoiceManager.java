package com.lfang.invoiceapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lfang.invoiceapp.database.InvoiceBaseHelper;
import com.lfang.invoiceapp.database.InvoiceCursorWrapper;
import com.lfang.invoiceapp.database.InvoiceSchema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lfang on 8/22/17.
 */

public class InvoiceManager {
    private static InvoiceManager mInvoiceManager;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static InvoiceManager get(Context context) {
        if (mInvoiceManager == null) {
            mInvoiceManager = new InvoiceManager(context);
        }

        return mInvoiceManager;
    }

    private InvoiceManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new InvoiceBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addInvoice(Invoice invoice) {
        ContentValues values = getContentValues(invoice);
        mDatabase.insert(InvoiceTable.NAME, null, values);
    }

    public void addItem(List<Item> items, UUID uuid) {
        for (Item item : items) {
            ContentValues values = getItemContentValues(item, uuid);
            mDatabase.insert(ItemTable.NAME, null, values);
        }
    }

    public List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        InvoiceCursorWrapper cursor = queryInvoices(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                invoices.add(cursor.getInvoice());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return invoices;
    }

    public List<Item> getItems(UUID uuid) {
        List<Item> items = new ArrayList<>();
        InvoiceCursorWrapper cursor = queryItems(
                ItemTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()}
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    public Invoice getInvoice(UUID uuid) {
        InvoiceCursorWrapper cursor =  queryInvoices(
                InvoiceTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getInvoice();
        } finally {
            cursor.close();
        }
    }

    private InvoiceCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new InvoiceCursorWrapper(cursor);
    }


    private InvoiceCursorWrapper queryInvoices(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                InvoiceTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new InvoiceCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put(InvoiceTable.Cols.UUID, invoice.getId().toString());
        values.put(InvoiceTable.Cols.Name, invoice.getName());
        values.put(InvoiceTable.Cols.Email, invoice.getEmail());
        values.put(InvoiceTable.Cols.DueDate, invoice.getDate());
        values.put(InvoiceTable.Cols.Total, invoice.getTotal());

        return values;
    }

    private static ContentValues getItemContentValues(Item item, UUID uuid) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.Cols.UUID, uuid.toString());
        values.put(ItemTable.Cols.Description, item.getDescription());
        values.put(ItemTable.Cols.Amount, item.getPrice());
        return values;
    }
}
