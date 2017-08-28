package com.lfang.invoiceapp.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lfang on 8/23/17.
 */

public class CalculateUtil {

    public static Date futureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public static BigDecimal getTotal(List<String> prices) {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        if (prices != null && !prices.isEmpty()){
            for (String price : prices) {
                BigDecimal newPrice = new BigDecimal(price);
                total = total.add(newPrice);
            }
        }
        return total;
    }

    public static String getFormatPrice(String price) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(Double.valueOf(price));
    }
}
