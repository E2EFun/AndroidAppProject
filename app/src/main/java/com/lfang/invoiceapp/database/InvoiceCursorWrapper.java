package com.lfang.invoiceapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.database.InvoiceSchema.*;
import com.lfang.invoiceapp.model.Item;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lfang on 8/22/17.
 */

public class InvoiceCursorWrapper extends CursorWrapper {

    public InvoiceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Invoice getInvoice() {
        String uuidString = getString(getColumnIndex(InvoiceTable.Cols.UUID));
        String name = getString(getColumnIndex(InvoiceTable.Cols.Name));
        String email = getString(getColumnIndex(InvoiceTable.Cols.Email));
        String dueDate = getString(getColumnIndex(InvoiceTable.Cols.DueDate));
        String total = getString(getColumnIndex(InvoiceTable.Cols.Total));

        return new Invoice(UUID.fromString(uuidString), name, email, dueDate, total);
    }

    public Item getItem() {
        String uuidString = getString(getColumnIndex(ItemTable.Cols.UUID));
        String description = getString(getColumnIndex(ItemTable.Cols.Description));
        String amount = getString(getColumnIndex(ItemTable.Cols.Amount));

        return new Item(description, amount);
    }

}
