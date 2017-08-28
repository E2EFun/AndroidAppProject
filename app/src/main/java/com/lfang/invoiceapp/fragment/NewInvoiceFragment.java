package com.lfang.invoiceapp.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lfang.invoiceapp.R;
import com.lfang.invoiceapp.activity.DisplayInvoiceActivity;
import com.lfang.invoiceapp.activity.PreviewInvoiceActivity;
import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.model.InvoiceManager;
import com.lfang.invoiceapp.model.Item;
import com.lfang.invoiceapp.util.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lfang on 8/23/17.
 */

public class NewInvoiceFragment extends Fragment {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mDueDayEditText;
    private EditText mDescription;
    private ImageView mDueDayTextView;
    private ImageView mAddButton;
    private ImageView mRemoveButton;
    private TextView mTotalTextView;
    private EditText mAmount;
    private LinearLayout rootLayout;
    private LinearLayout itemsLayout;
    private EditText editText1;
    private EditText editText2;

    private String name;
    private String email;
    private String dueDate;
    private Context context;
    private InvoiceManager invoiceManager;

    private UUID uuid;
    ArrayList<Item> items = new ArrayList<>();

    private BigDecimal totalAmount;
    private boolean aboveMax = false;

    List<EditText> priceEds = new ArrayList<>();
    List<EditText> desEds = new ArrayList<>();
    List<LinearLayout> layouts = new ArrayList<>();
    List<String> values = new ArrayList<>();

    private String DEFAULT_NAME = "Custom Amount";
    private String DATE_FORMAT = "MM/dd/yyyy";
    private double MAX_VALUE = 99999.99;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_invoice_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        mNameEditText = (EditText) view.findViewById(R.id.name_edit_text);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = mNameEditText.getText().toString();
            }
        });
        mEmailEditText = (EditText) view.findViewById(R.id.email_eidt_text);
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = mEmailEditText.getText().toString();
            }
        });
        mDueDayEditText = (EditText) view.findViewById(R.id.due_date_label);
        mDueDayEditText.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(CalculateUtil.futureDate()));
        mDueDayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        mDueDayTextView = (ImageView) view.findViewById(R.id.due_date_value);
        mDueDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        mDescription = (EditText) view.findViewById(R.id.description_eidt_text);
        mAmount = (EditText) view.findViewById(R.id.item_price);
        mAmount.addTextChangedListener(new TextWatcher() {
            String pre = null;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pre = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                calculateTotal(input);
            }

            private void calculateTotal(String itemPrice) {
                if (!pre.equals(itemPrice)) {
                    if (values.contains(pre)) {
                        values.remove(pre);
                    }
                    if (!itemPrice.isEmpty()) {
                        values.add(itemPrice);
                    }
                    BigDecimal sum = CalculateUtil.getTotal(values);
                    mTotalTextView.setText(getString(R.string.total_price) + " :  " + CalculateUtil.getFormatPrice(sum.toString()));
                }
            }
        });
        mAddButton = (ImageView) view.findViewById(R.id.add_button);
        mRemoveButton = (ImageView) view.findViewById(R.id.remove_button);
        mTotalTextView = (TextView) view.findViewById(R.id.total_text_view);
        mTotalTextView.setText(getString(R.string.total_price) + " :  " + getString(R.string.zero_price));
        rootLayout = (LinearLayout) view.findViewById(R.id.invoice_layout);

        invoiceManager = InvoiceManager.get(context);
        uuid = UUID.randomUUID();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemLineView();
                mRemoveButton.setVisibility(View.VISIBLE);
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemLineView();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (itemsLayout == null) {
            itemsLayout = new LinearLayout(context);
            itemsLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            itemsLayout.setLayoutParams(LLParams);
            rootLayout.addView(itemsLayout);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.send_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_menu:
                if (!isValidName()) {
                    showAlertDialog();
                    return false;
                }
                if (!isValidAmt()) {
                    showAlertDialog();
                    items.clear();
                    aboveMax = false;
                    return false;
                }
                if (!isEmailValid()) {
                    mEmailEditText.setError("Invalid email");
                    return false;
                }
                getInvoiceInfo();
                insertInvoice();
                Intent displayIntent = DisplayInvoiceActivity.newIntent(getActivity(), uuid);
                startActivity(displayIntent);
                clearText();
                uuid = UUID.randomUUID();
                return true;
            case R.id.preview_menu:
                if (!isValidName()) {
                    showAlertDialog();
                    return false;
                }
                if (!isValidAmt()) {
                    showAlertDialog();
                    items.clear();
                    aboveMax = false;
                    return false;
                }
                if (!isEmailValid()) {
                    mEmailEditText.setError("Invalid email");
                    return false;
                }
                String name = mNameEditText.getText().toString();
                String dueDate = mDueDayEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                Invoice invoice = new Invoice(uuid, name, email, dueDate, totalAmount.toString());
                Intent previewIntent = PreviewInvoiceActivity.newIntent(getActivity(), invoice, items);
                startActivity(previewIntent);
                items.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Calendar picker
    private void showDatePicker() {
        Calendar currentCal = Calendar.getInstance(Locale.US);
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCal = Calendar.getInstance();
                        newCal.set(year, month, dayOfMonth);
                        mDueDayEditText.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(newCal.getTime()));
                    }
                },
                currentCal.get(Calendar.YEAR),
                currentCal.get(Calendar.MONTH),
                currentCal.get(Calendar.DAY_OF_MONTH)).show();
    }

    //Add item line when user click add button, and also add listener to item price text field
    private void addItemLineView() {
        LinearLayout itemLayout = new LinearLayout(context);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParams.setMargins(32, 20, 32, 0);
        itemLayout.setLayoutParams(LLParams);
        itemsLayout.addView(itemLayout);
        layouts.add(itemLayout);
        editText1 = createDescriptionEditText();
        desEds.add(editText1);
        itemLayout.addView(editText1);
        editText2 = createItemPriceEditText();
        editText2.addTextChangedListener(new TextWatcher() {
            String pre = null;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pre = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                calculateTotal(input);
            }

            private void calculateTotal(String input) {
                if (!pre.equals(input)) {
                    if (values.contains(pre)) {
                        values.remove(pre);
                    }
                    if (!input.isEmpty()) {
                        values.add(input);
                    }
                    BigDecimal sum = CalculateUtil.getTotal(values);
                    mTotalTextView.setText(getString(R.string.total_price) + " :  " + CalculateUtil.getFormatPrice(sum.toString()));
                }
            }
        });
        priceEds.add(editText2);
        itemLayout.addView(editText2);
    }

    private void removeItemLineView() {
        int size = desEds.size()-1;
        reCalculateTotalAfterRemove(size);
        //get the edittext from arraylist and set to invisible, amd aslo remove parent LinearLayout when user click Remove button
        desEds.get(size).setVisibility(View.INVISIBLE);
        priceEds.get(size).setVisibility(View.INVISIBLE);
        itemsLayout.removeView(layouts.get(size));
        desEds.remove(size);
        priceEds.remove(size);
        layouts.remove(size);
        if (size == 0) { mRemoveButton.setVisibility(View.GONE); }
    }

    //Recalculate the total when user click the remove button
    private void reCalculateTotalAfterRemove(int size) {
        String price = priceEds.get(size).getText().toString();
        if (values.contains(price)) {
            values.remove(price);
            BigDecimal sum = CalculateUtil.getTotal(values);
            mTotalTextView.setText(getString(R.string.total_price) + " :  " + CalculateUtil.getFormatPrice(sum.toString()));
        }
    }

    //add description edit text when user click add button
    private EditText createDescriptionEditText() {
        EditText editText1 = new EditText(context);
        editText1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));
        editText1.setHint(R.string.hint_description);
        editText1.setInputType(InputType.TYPE_CLASS_TEXT);
        editText1.setMaxLines(1);
        editText1.setTextSize(20);

        return editText1;
    }

    //add price edit text when user click add button
    private EditText createItemPriceEditText() {
        EditText editText2 = new EditText(context);
        editText2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        editText2.setHint(R.string.hint_price);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText2.setMaxLines(1);
        editText2.setTextSize(20);

        return editText2;
    }

    //clear all the texts, edittexts and reset date is today date, total to $0.00 when user click back from preview panel
    private void clearText() {
        mNameEditText.setText("");
        mEmailEditText.setText("");
        mDueDayEditText.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(CalculateUtil.futureDate()));
        mDescription.setText("");
        mAmount.setText("");
        mTotalTextView.setText(getString(R.string.total_price) + " :  " + getString(R.string.zero_price));
        priceEds.clear();
        desEds.clear();
        rootLayout.removeView(itemsLayout);
        mRemoveButton.setVisibility(View.GONE);
        itemsLayout = null;
        items.clear();
        totalAmount = null;

    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getString(R.string.invoice_alert));
        alertDialog.setMessage(getString(R.string.alert_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void getInvoiceInfo() {
        name = mNameEditText.getText().toString();
        email = mEmailEditText.getText().toString();
        dueDate = mDueDayEditText.getText().toString();
    }

    private void prepareItems() {
        String amt = mAmount.getText().toString();
        String des = mDescription.getText().toString();
        putItemIntoList(des, amt);
        for (int i = 0; i < priceEds.size(); i++) {
            String amtDynamic = priceEds.get(i).getText().toString();
            String desDynamic = desEds.get(i).getText().toString();
            putItemIntoList(desDynamic, amtDynamic);
        }
    }

    private BigDecimal prepareTotalAmt() {
        BigDecimal totalAmt = new BigDecimal(BigInteger.ZERO);
        prepareItems();
        for (Item item : items) {
            if (item.getPrice() != null && !item.getPrice().isEmpty()) {
                totalAmt = totalAmt.add(new BigDecimal(item.getPrice()));
            }
        }
        return totalAmt;
    }

    private boolean isValidName() {
        return name != null && !name.isEmpty();
    }

    private boolean isEmailValid() {
        return email == null || email.isEmpty() || EmailUtil.isEmailValid(email);
    }

    private boolean isValidAmt() {
        totalAmount = prepareTotalAmt();
        return  totalAmount.compareTo(BigDecimal.ZERO) > 0 && items.size() > 0 && !aboveMax && totalAmount.compareTo(new BigDecimal(MAX_VALUE)) < 0;
    }

    private void insertInvoice() {
        Invoice invoice = new Invoice(uuid, name, email, dueDate, totalAmount.toString());
        invoiceManager.addInvoice(invoice);
        invoiceManager.addItem(items, uuid);
    }

    private void putItemIntoList(String des, String amt) {
        if (des == null) {
            des = DEFAULT_NAME;
        } else if (des.isEmpty()) {
            des = DEFAULT_NAME;
        }
        if (amt != null && !amt.isEmpty()) {
            if (Double.valueOf(amt) > MAX_VALUE) {
                aboveMax = true;
            } else {
                Item item1 = new Item(des, amt);
                items.add(item1);
            }
        }
    }
}
