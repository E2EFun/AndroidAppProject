package com.lfang.invoiceapp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lfang.invoiceapp.R;
import com.lfang.invoiceapp.fragment.InvoiceListFragment;
import com.lfang.invoiceapp.fragment.NewInvoiceFragment;
import com.lfang.invoiceapp.model.Invoice;

/**
 * Created by lfang on 8/26/17.
 */

public class InvoiceListActivity extends BaseInvoiceActivity {
    @Override
    protected Fragment createFragment() {
        return new InvoiceListFragment();
    }
}
