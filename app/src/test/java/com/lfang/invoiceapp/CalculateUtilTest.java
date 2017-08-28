package com.lfang.invoiceapp;

import com.lfang.invoiceapp.util.CalculateUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lfang on 8/26/17.
 */

public class CalculateUtilTest {

    @Test
    public void totalTest() {
        List<String> list = new ArrayList<>();
        list.add("8.76");
        list.add("13.54");
        list.add("0.01");
        assertEquals("22.31", CalculateUtil.getTotal(list).toString());
    }

    @Test
    public void currencyTest() {
        assertEquals("$34.12", CalculateUtil.getFormatPrice("34.12"));
    }
}
