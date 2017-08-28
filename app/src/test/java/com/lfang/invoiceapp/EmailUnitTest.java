package com.lfang.invoiceapp;

import com.lfang.invoiceapp.util.EmailUtil;

import org.junit.Test;

import static org.junit.Assert.*;


public class EmailUnitTest {
    @Test
    public void emailValidationTest() throws Exception {
        assertFalse("not valid email", EmailUtil.isEmailValid("01"));
        assertFalse("not valid email", EmailUtil.isEmailValid(" "));
        assertFalse("not valid email", EmailUtil.isEmailValid("@test.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("test@@test.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("test.com@"));
        assertFalse("not valid email", EmailUtil.isEmailValid("test@.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("*@text.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid(".test@test.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("test@test..com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("test@[test}.com"));
        assertFalse("not valid email", EmailUtil.isEmailValid("6@test.com"));
        assertTrue("not valid email", EmailUtil.isEmailValid("test@test.com"));
    }
}