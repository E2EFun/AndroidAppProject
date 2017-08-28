package com.lfang.invoiceapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfang.invoiceapp.R;
import com.lfang.invoiceapp.activity.NewInvoiceActivity;
import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.model.InvoiceManager;
import com.lfang.invoiceapp.model.Item;
import com.lfang.invoiceapp.util.CalculateUtil;

import java.util.List;

/**
 * Created by lfang on 8/26/17.
 */

public class InvoiceListFragment extends Fragment {

    private RecyclerView mInvoiceRecyclerView;
    private InvoiceAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.invoice_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInvoiceRecyclerView = (RecyclerView) view.findViewById(R.id.invoice_recycler_view);
        mInvoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_invoice, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_invoice:
                Intent newInvoiceIntent = NewInvoiceActivity.newIntent(getActivity());
                startActivity(newInvoiceIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {
        InvoiceManager invoiceManager = InvoiceManager.get(getActivity());
        List<Invoice> invoices = invoiceManager.getInvoices();

        if (mAdapter == null) {
            mAdapter = new InvoiceAdapter(invoices);
            mInvoiceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setInvoices(invoices);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class InvoiceHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mDateTextView;
        private TextView mPriceTextView;

        InvoiceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_invoice, parent, false));

            mNameTextView = (TextView) itemView.findViewById(R.id.invoice_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.invoice_date);
            mPriceTextView = (TextView) itemView.findViewById(R.id.invoice_price);
        }

        void bind(Invoice invoice) {
            mNameTextView.setText(invoice.getName());
            mDateTextView.setText(invoice.getDate());
            mPriceTextView.setText(CalculateUtil.getFormatPrice(invoice.getTotal()));
        }
    }

    private class InvoiceAdapter extends RecyclerView.Adapter<InvoiceHolder> {

        private List<Invoice> mInvoices;

        InvoiceAdapter(List<Invoice> invoices) { mInvoices = invoices; }

        @Override
        public InvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InvoiceListFragment.InvoiceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(InvoiceHolder holder, int position) {
            Invoice invoice = mInvoices.get(position);
            holder.bind(invoice);
        }

        @Override
        public int getItemCount() {
            return mInvoices.size();
        }

        void setInvoices(List<Invoice> invoices) { mInvoices = invoices; }
    }
}
