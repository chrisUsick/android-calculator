package com.cu_dev.calculator;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.antlr.v4.runtime.misc.ObjectEqualityComparator;

/**
 * Created by chris on 21-Sep-16.
 */
public class CalculatorData {

    public static class UserDataChangedCallback extends Observable.OnPropertyChangedCallback {
        private CalculatorData data;
        @Override
        public void onPropertyChanged(Observable observable, int fieldId) {
            if (data.userName.get() != null && !data.userName.get().trim().isEmpty() && data.userAge.get() != 0) {
                data.showUserData.set(LinearLayout.VISIBLE);
            } else {
                data.showUserData.set(LinearLayout.INVISIBLE);
            }
        }
        public UserDataChangedCallback(CalculatorData data) {
            this.data = data;
        }
    }
    public final ObservableInt showUserData = new ObservableInt(LinearLayout.INVISIBLE);

    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableLong userAge = new ObservableLong();

    public final ObservableLong num1 = new ObservableLong();
    public final ObservableLong num2 = new ObservableLong();
    public final ObservableLong result = new ObservableLong();
    @BindingAdapter("android:text")
    public static void setText(TextView view, long value) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            int position = editText.getSelectionEnd();
            view.setText(Long.toString(value));
            try {
                editText.setSelection(position);
            } catch (IndexOutOfBoundsException e) {
                editText.setSelection(editText.getText().toString().length());
            }
        } else {
            view.setText(Long.toString(value));
        }
    }
//
    @InverseBindingAdapter(attribute = "android:text")
    public static long getText(TextView view) {
        String value = view.getText().toString().trim().isEmpty() ? "0" : view.getText().toString();
        return Long.parseLong(value);
    }

    public CalculatorData() {
        UserDataChangedCallback callback = new UserDataChangedCallback(this);
        userName.addOnPropertyChangedCallback(callback);
        userAge.addOnPropertyChangedCallback(callback);

    }


}
