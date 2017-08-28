# Project Title

This is Android Invoice App

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

```
Android studio
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Install android studion in your local machine
Pull the codes from github
Install the required libraries using Android SDK manager

```
## Project structure

```
manifests:
    AndroidManifest.xml - This file provides essential information about app to the Android system, which the system must have before it can run any of app's code
  java:
    com.lfang.invoiceapp
      activity
        BaseInvoiceActivity.java - This is the abstract class and is parent class for all the activities
        DisplayInvoiceActivity.java - This activity is subclass of BaseInvoiceActivity.java, it is used to create DisplayInvoiceFragment
        InvoiceListActivity.java - This activity is subclass of BaseInvoiceActivity.java, it is used to create InvoiceListFragment
        NewInvoiceActivity - This activity is subclass of BaseInvoiceActivity.java, it is used to create NewInvoiceFragment
        
      fragment
        DisplayInvoiceFragment - Display invoice infomations from database after user click send button
        InvoiceListFragment - Display the list invoices from database
        NewInvoiceFragment - Let user input name, email, due day and line items, and at the same time calculate the total. 
                             There are two buttons in ActionBar: Send and Preview, click Send to send user's data to database
                             click Preview to preview the invoice
        PreviewInvoiceFragment - Preview the invoice information
        
       database
        InvoiceBaseHelper - User SQLite as Database, this class extends SQLiteOpenHelper to create two tables: invoices and items
        InvoiceCursorWrapper - This class extends CursorWrapper to get invoice and item data from database
        InvoiceSchema - This class provide table name and columns name
        
       model
        Invoice - implement Parcelable interface to send invoice object through intent to another activity
        Item - implement Parcelable interface to send items object through intent to another activity
        InvoiceManager - This class is used to provide methods to connect database, save data to database, and retrieve data from database
        
       util
        CalculateUtil - This class provide methods to calculate date, price and display currency
        EmailUtil - This class provide method to validate email valid
    
    com.lfang.invoiceapp(androidTest)
      activity
        NewInvoiceActivityTest - UI Espresso tests
        SQLiteDatabaeTest - database unit test
      util
        EspressoBaseClass - provide methods for espresso tests
    com.lfanginvoiceapp(test)
      CalculateUtilTest - unit tests for CalculateUtil.java
      EmailUnitTest - unit tests for EmailUnitTest.java

```


## Running the tests

```
In com.lfang.invoiceapp(androidTest)
Right click NewInvoiceActivityTest.java
Select "Run NewInvoiceActivity.."
Run Espresso UI tests

```
## Conditions

```
Name is required, if not, get alert
Email is optional, but if input invalid email, the user will get warning
Description is optional
User can only input positive item price

```

