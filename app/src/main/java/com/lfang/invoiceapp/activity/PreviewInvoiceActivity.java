package com.lfang.invoiceapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.lfang.invoiceapp.fragment.DisplayInvoiceFragment;
import com.lfang.invoiceapp.fragment.PreviewInvoiceFragment;
import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.model.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lfang on 8/24/17.
 */

public class PreviewInvoiceActivity extends BaseInvoiceActivity {

    private static final String INVOICE_DATA = "com.lfang.invoiceapp.activity.invoice_data";
    private static final String INVOICE_ITEMS = "com.lfang.invoiceapp.activity.invoice_items";

    public static Intent newIntent(
            Context packageContext,
            Invoice invoice,
            ArrayList<Item> items) {
        Intent intent = new Intent(packageContext, PreviewInvoiceActivity.class);
        intent.putExtra(INVOICE_DATA, invoice);
        intent.putExtra(INVOICE_ITEMS, items);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Invoice invoice = (Invoice) getIntent().getParcelableExtra(INVOICE_DATA);
        ArrayList<Item> items = getIntent().getParcelableArrayListExtra(INVOICE_ITEMS);
        return PreviewInvoiceFragment.newInstance(invoice,items);
    }
}
