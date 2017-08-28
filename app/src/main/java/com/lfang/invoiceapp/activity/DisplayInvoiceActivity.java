package com.lfang.invoiceapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.lfang.invoiceapp.fragment.DisplayInvoiceFragment;

import java.util.UUID;

/**
 * Created by lfang on 8/23/17.
 */

public class DisplayInvoiceActivity extends BaseInvoiceActivity {

    private static final String EXTRA_INVOICE_ID =
            "com.lfang.invoiceapp.activity.invoice_id";

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, DisplayInvoiceActivity.class);
        intent.putExtra(EXTRA_INVOICE_ID, uuid);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_INVOICE_ID);
        return DisplayInvoiceFragment.newInstance(crimeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
