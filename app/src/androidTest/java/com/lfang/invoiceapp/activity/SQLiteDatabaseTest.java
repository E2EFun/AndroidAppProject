package com.lfang.invoiceapp.activity;

import android.support.test.InstrumentationRegistry;

import com.lfang.invoiceapp.model.Invoice;
import com.lfang.invoiceapp.model.InvoiceManager;
import com.lfang.invoiceapp.model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lfang on 8/22/17.
 */

public class SQLiteDatabaseTest {

    private InvoiceManager invoiceManager;

    @Before
    public void setUp() {
        invoiceManager = InvoiceManager.get(InstrumentationRegistry.getTargetContext());
    }
    @After
    public void tearDown() {
        invoiceManager = null;
    }

    @Test
    public void insertAndSelectTest() {
        UUID uuid = UUID.randomUUID();
        Invoice invoice = new Invoice(uuid, "unitInvoice", "unit@invoice.com", "09/13/2017", "$290.21");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Service", "54.23");
        items.add(item1);
        Item item2 = new Item("Produce", "235.98");
        items.add(item2);
        invoiceManager.addInvoice(invoice);
        invoiceManager.addItem(items, uuid);
        Invoice result = invoiceManager.getInvoice(uuid);
        List<Item> resultItems = invoiceManager.getItems(uuid);
        assertEquals("The invoice name is not same", invoice.getName(), result.getName());
        assertEquals("The invoice email is not same", invoice.getEmail(), result.getEmail());
        assertEquals("The invoice due day is not same", invoice.getDate(), result.getDate());
        assertEquals("The invoice total price is not same", invoice.getTotal(), result.getTotal());
        assertEquals("The item price is not correct", items.get(0).getPrice(), resultItems.get(0).getPrice());
        assertEquals("The item description is not correct", items.get(0).getDescription(), resultItems.get(0).getDescription());
    }
}
