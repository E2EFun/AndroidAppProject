package com.lfang.invoiceapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lfang.invoiceapp.fragment.NewInvoiceFragment;

/**
 * Created by lfang on 8/23/17.
 */

public class NewInvoiceActivity extends BaseInvoiceActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, NewInvoiceActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new NewInvoiceFragment();
    }

}
