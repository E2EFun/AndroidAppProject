package com.lfang.invoiceapp.activity;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.lfang.invoiceapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.lfang.invoiceapp.util.EspressoBaseClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewInvoiceActivityTest extends EspressoBaseClass{

    @Rule
    public ActivityTestRule<NewInvoiceActivity> mActivityTestRule = new ActivityTestRule<>(NewInvoiceActivity.class);

    /**
     * Verify the action bar menu, app title, icons, buttons exist
     */
    @Test
    public void verifyUILookTest() {
        onView(withId(R.id.send_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.preview_menu)).check(matches(isDisplayed()));
        onView(withText("InvoiceApp")).check(matches(isDisplayed()));
        onView(withId(R.id.due_date_value)).check(matches(isDisplayed()));
    }

    /**
     * Verify edit text input
     */

    @Test
    public void verifyInvoiceCorrectAfterSendTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.email_eidt_text)).perform(click()).perform(replaceText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.description_eidt_text)).perform(click()).perform(replaceText("water"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("1.25"), closeSoftKeyboard());
        onView(withId(R.id.add_button)).perform(scrollTo(), click());
    }

    /**
     * Verify Add and remove button
     */

    @Test
    public void verifyAddRemoveButtonTest() {
        onView(withId(R.id.add_button)).perform(scrollTo(), click());
        onView(withId(R.id.add_button)).perform(scrollTo(), click());
        onView(withId(R.id.add_button)).perform(scrollTo(), click());
        onView(withId(R.id.remove_button)).perform(scrollTo(), click());
        onView(withId(R.id.remove_button)).perform(scrollTo(), click());
        onView(withId(R.id.remove_button)).perform(scrollTo(), click());
        onView(withClassName(is("android.widget.EditText"))).check(doesNotExist());
    }

    /**
     * Verify total price
     */

    @Test
     public void verifyATotalPriceTest() {
         onView(withId(R.id.item_price)).perform(click()).perform(replaceText("1.25"), closeSoftKeyboard());
         onView(withId(R.id.total_text_view)).check(matches(withText(containsString("$1.25"))));
     }

    /**
     * Verify empty invoice, click send, get alert
     */
    @Test
    public void verifyEmptyInvoiceAlertTest() {
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }

    /**
     * Verify the invoice without amount, get alert
     */
    @Test
    public void verifyInvoiceWithoutAmountTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }

    /**
     * Verify input 6 digits after point number, but only display 2 digits after point
     */
    @Test
    public void verifyDisplayNumberOnlyShowTwoDigitsAfterPointTest() {
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("1.258976"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).check(matches(withText("1.258976")));
    }

    /**
     * Verify input amount is larger than max value(99999.99), get alert
     */
    @Test
    public void verifyAmountLargerThanMaxTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("10000000"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }

    /**
     * Verify invoice without name, get alert
     */
    @Test
    public void verifyInvoiceWithoutNameTest() {
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("100"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }

    /**
     * Verify invalid email, get error
     */
    @Test
    public void verifyInvalidEmailTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.email_eidt_text)).perform(click()).perform(replaceText("1testlk@"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("100"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withId(R.id.name_edit_text)).check(matches(isDisplayed()));
    }

    /**
     * Verify total price is Negative, get alert
     */
    @Test
    public void verifyNegativeTotalPriceTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("-0.98"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }

    /**
     * Verify total price is zero, get alert
     */
    @Test
    public void verifyZeroTotalPriceTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("0.00"), closeSoftKeyboard());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("something\'s not quite right"));
    }


    /**
     * Verify invoice after send
     */
    @Test
    public void verifyUIAfterSendTest() {
        onView(withId(R.id.name_edit_text)).perform(click()).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.email_eidt_text)).perform(click()).perform(replaceText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.date_container)).perform(click());
        onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Next month"),
                        withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                        isDisplayed())).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.description_eidt_text)).perform(click()).perform(replaceText("water"), closeSoftKeyboard());
        onView(withId(R.id.item_price)).perform(click()).perform(replaceText("1.25"), closeSoftKeyboard());
        onView(withId(R.id.add_button)).perform(scrollTo(), click());
        onView(withId(R.id.send_menu)).perform(click());
        onView(withText("Name : test")).check(matches(isDisplayed()));
        onView(withText("Email : test@gmail.com")).check(matches(isDisplayed()));
        onView(withText("water : $1.25")).check(matches(isDisplayed()));
        onView(withText("Total : $1.25")).check(matches(isDisplayed()));
    }
}
