package com.cu_dev.calculator;

import android.database.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableLong;

/**
 * Created by chris on 21-Sep-16.
 */
public class UserInfoData {

    public final ObservableField<String> name =
            new ObservableField<>();

    public final ObservableLong age = new ObservableLong();
}
