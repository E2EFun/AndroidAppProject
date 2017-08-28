package com.lfang.invoiceapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfang.invoiceapp.R;
import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.model.Item;
import com.lfang.invoiceapp.util.CalculateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfang on 8/24/17.
 */

public class PreviewInvoiceFragment extends Fragment {

    private static final String INVOICE_DATA= "invoice_data";
    private static final String INVOICE_ITEMS = "invoice_items";

    private RecyclerView mInvoiceRecyclerView;
    private PreviewInvoiceFragment.InvoiceAdapter mInvoiceAdapter;
    private TextView mInvoiceTextView;
    private TextView mBillTextView;
    private TextView mShipTextView;
    private TextView mEmailTextView;
    private TextView mDueDateTextView;
    private TextView mTotalTextView;
    private TextView mStatusTextView;

    private ArrayList<Item> items;
    private Invoice invoice;

    public static PreviewInvoiceFragment newInstance(
            Invoice invoice,
            ArrayList<Item> items) {
        Bundle args = new Bundle();
        args.putParcelable(INVOICE_DATA, invoice);
        args.putParcelableArrayList(INVOICE_ITEMS, items);

        PreviewInvoiceFragment fragment = new PreviewInvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoice = getArguments().getParcelable(INVOICE_DATA);
        items = getArguments().getParcelableArrayList(INVOICE_ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_invoice_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mInvoiceTextView = (TextView) view.findViewById(R.id.invoice_text_view);
        mInvoiceTextView.setText(getString(R.string.invoice_title));
        mBillTextView = (TextView) view.findViewById(R.id.bill_text_view);
        mBillTextView.setText(getString(R.string.invoice_bill) + " : " + invoice.getName());
        mShipTextView = (TextView) view.findViewById(R.id.ship_text_view);
        mShipTextView.setText(getString(R.string.invoice_ship) + " : " + invoice.getName());
        mEmailTextView = (TextView) view.findViewById(R.id.email_text_view);
        mEmailTextView.setText(getString(R.string.invoice_email) + " : " + invoice.getEmail());
        mDueDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        mDueDateTextView.setText(getString(R.string.invoice_dueday) + " : " + invoice.getDate());

        mInvoiceRecyclerView = (RecyclerView) view.findViewById(R.id.invoice_recycler_view);
        mInvoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mInvoiceAdapter = new PreviewInvoiceFragment.InvoiceAdapter(items);
        mInvoiceRecyclerView.setAdapter(mInvoiceAdapter);

        mTotalTextView = (TextView) view.findViewById(R.id.total_text_view);
        mTotalTextView.setText(getString(R.string.total_price) + ": " + CalculateUtil.getFormatPrice(invoice.getTotal()));
        mStatusTextView = (TextView) view.findViewById(R.id.status_text_view);
        mStatusTextView.setText(getString(R.string.invoice_status));
    }

    private class InvoiceHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;

        InvoiceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.invoice_item, parent, false));
            mItemTextView = (TextView) itemView.findViewById(R.id.item_text_view);
        }

        void bind(Item item) {
            mItemTextView.setText(item.getDescription() + " : " + CalculateUtil.getFormatPrice(item.getPrice()));
        }
    }

    private class InvoiceAdapter extends RecyclerView.Adapter<PreviewInvoiceFragment.InvoiceHolder> {
        private List<Item> mItems;

        InvoiceAdapter(List<Item> items) {
            mItems = items;
        }

        @Override
        public PreviewInvoiceFragment.InvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PreviewInvoiceFragment.InvoiceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PreviewInvoiceFragment.InvoiceHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            if (mItems != null && !mItems.isEmpty()) {
                return mItems.size();
            }
            return 0;
        }
    }
}
