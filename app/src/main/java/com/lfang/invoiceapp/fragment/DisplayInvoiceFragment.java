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
import com.lfang.invoiceapp.model.InvoiceManager;
import com.lfang.invoiceapp.model.Item;
import com.lfang.invoiceapp.util.CalculateUtil;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by lfang on 8/23/17.
 */

public class DisplayInvoiceFragment extends Fragment {

    private static final String INVOICE_ID = "invoice_id";
    private List<Item> items;
    private Invoice invoice;

    private RecyclerView mInvoiceRecyclerView;
    private InvoiceAdapter mInvoiceAdapter;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mDueDateTextView;
    private TextView mTotalTextView;

    public static DisplayInvoiceFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(INVOICE_ID, uuid);

        DisplayInvoiceFragment fragment = new DisplayInvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getArguments().getSerializable(INVOICE_ID);
        InvoiceManager invoiceManager = InvoiceManager.get(getActivity());
        invoice = invoiceManager.getInvoice(uuid);
        items = invoiceManager.getItems(uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_invoice_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNameTextView = (TextView) view.findViewById(R.id.name_text_view);
        mNameTextView.setText(getString(R.string.invoice_name) + " : " + invoice.getName());
        mEmailTextView = (TextView) view.findViewById(R.id.email_text_view);
        mEmailTextView.setText(getString(R.string.invoice_email) + " : " + invoice.getEmail());
        mDueDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        mDueDateTextView.setText(getString(R.string.invoice_dueday) + " : " + invoice.getDate());

        mInvoiceRecyclerView = (RecyclerView) view.findViewById(R.id.invoice_recycler_view);
        mInvoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mInvoiceAdapter = new InvoiceAdapter(items);
        mInvoiceRecyclerView.setAdapter(mInvoiceAdapter);

        mTotalTextView = (TextView) view.findViewById(R.id.total_text_view);
        mTotalTextView.setText(getString(R.string.total_price) + " : "+ CalculateUtil.getFormatPrice(invoice.getTotal()));
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

    private class InvoiceAdapter extends RecyclerView.Adapter<InvoiceHolder> {
        private List<Item> mItems;

        InvoiceAdapter(List<Item> items) {
            mItems = items;
        }

        @Override
        public InvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InvoiceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(InvoiceHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}
