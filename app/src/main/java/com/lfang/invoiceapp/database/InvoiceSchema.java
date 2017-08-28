package com.lfang.invoiceapp.database;

/**
 * Created by lfang on 8/22/17.
 */

public class InvoiceSchema {
    public static final class InvoiceTable {
        public static final String NAME = "invoices";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String Name = "name";
            public static final String Email = "email";
            public static final String DueDate = "due_date";
            public static final String Total = "total";
        }
    }

    public static final class ItemTable {
        public static final String NAME = "items";

        public static final class Cols {
            public static final String Description = "description";
            public static final String Amount = "amount";
            public static final String UUID = "uuid";
        }
    }
}
