package com.cu_dev.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cu_dev.calculator.databinding.ActivityUserInfoBinding;

import org.apache.commons.io.output.TaggedOutputStream;

import java.util.Calendar;
import java.util.Date;

public class UserInfoActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {
    private ActivityUserInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        UserInfoData data = new UserInfoData();
        binding.setData(data);

        initDatePicker();

        initIntent();
    }

    private void initIntent() {
    }

    private void initDatePicker() {
        DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        Calendar cal = Calendar.getInstance();
        datePicker.setMaxDate(cal.getTimeInMillis());
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        int now = cal.get(Calendar.YEAR);
        int years = now - year;
        if (month > cal.get(Calendar.MONTH)) {
            years--;
        } else if (month == cal.get(Calendar.MONTH)) {
            if (day > cal.get(Calendar.DAY_OF_MONTH)) {
                years--;
            }
        }
        binding.getData().age.set((long)(years));
    }

    @Override
    public void onBackPressed() {
        Log.i("chris", "Back pressed in UserInfoActivity");
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.ext_user_name), binding.getData().name.get());
        intent.putExtra(getString(R.string.ext_user_age), binding.getData().age.get());
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    public void saveData(View view) {
        Toast.makeText(this, "Saving data", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.user_info_preferences), Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(getString(R.string.pref_user_age), binding.getData().age.get());
        editor.putString(getString(R.string.pref_user_name), binding.getData().name.get());
        editor.commit();
    }

    public void loadData(View view) {
        Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = getSharedPreferences(
                getString(R.string.user_info_preferences), Context.MODE_PRIVATE
        );

        long age = pref.getLong(getString(R.string.pref_user_age), 0);
        String name = pref.getString(getString(R.string.pref_user_name), "");

        binding.getData().age.set(age);
        binding.getData().name.set(name);
    }




}
