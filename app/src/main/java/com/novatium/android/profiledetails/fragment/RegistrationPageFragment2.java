package com.novatium.android.profiledetails.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.model.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegistrationPageFragment2 extends Fragment implements View.OnClickListener {

    private View view;
    private DBHandler dbHandler;
    private AppCompatSpinner spinner;

    private TextInputEditText mobile, email, status,joinDate;
    private UserDetails userDetails;
    private MaterialButton selectDate;
    public RegistrationPageFragment2(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public RegistrationPageFragment2() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration_page_2, container, false);
        dbHandler = new DBHandler(requireContext());
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dbHandler.getAllSupervisor());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private boolean verifyNotEmptyView() {
        boolean flag = true;
        String mobileNumber = mobile.getText().toString();
        if (TextUtils.isEmpty(mobile.getText())) {
            mobile.setError("Mobile number required");
            flag = false;
        } else if (!(mobileNumber.length() >= 10 && mobileNumber.length() <= 14)) {
            mobile.setError("Valid Mobile Number");
            flag = false;
        } else if (mobileNumber.matches("(\\+91(-)?|91(-)?|0(-)?)?(6-9)[0-9]{9}")) {
            mobile.setError("Valid Mobile Number");
            flag = false;
        }

        if (TextUtils.isEmpty(email.getText())) {
            email.setError("EMail Id reqiured");
            flag = false;
        } else if (email.getText().toString().matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}")) {
            email.setError("Valid Email Address");
            flag = false;
        }

        if (TextUtils.isEmpty(status.getText())) {
            status.setError("Status Required");
            flag = false;
        }

        if (TextUtils.isEmpty(joinDate.getText())) {
            joinDate.setError("Join Date Required");
            flag = false;
        }

        return flag;

    }


    private void initViews() {
        spinner = view.findViewById(R.id.supervisor);
        mobile = view.findViewById(R.id.mobileNumber);
        email = view.findViewById(R.id.mailId);
        MaterialButton submit = view.findViewById(R.id.submit_go);
        status = view.findViewById(R.id.status);
        submit.setOnClickListener(this);
        joinDate = view.findViewById(R.id.joinDate);
        joinDate.setFocusable(false);
        selectDate = view.findViewById(R.id.joinDateButton);
        selectDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_go:
                if (verifyNotEmptyView()) {
                    userDetails.setId(userDetails.getId());
                    userDetails.setSupervisorName((String) Objects.requireNonNull(spinner.getSelectedItem()));
                    userDetails.setMobileNumber(Long.parseLong(Objects.requireNonNull(mobile.getText()).toString()));
                    userDetails.setEmailId(Objects.requireNonNull(email.getText()).toString());
                    userDetails.setStatus(Objects.requireNonNull(status.getText()).toString());
                    userDetails.setJoinDate(Objects.requireNonNull(joinDate.getText()).toString());
                    dbHandler.insertPage(userDetails);
                    Objects.requireNonNull(getActivity()).findViewById(R.id.mainButton).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.mainFragment).setVisibility(View.GONE);
                }
                break;
            case R.id.joinDateButton:
                selectDate.setClickable(false);
                selectDate.setEnabled(false);
                MaterialDatePicker.Builder<Long> builder= MaterialDatePicker.Builder.datePicker();
                builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
                MaterialDatePicker<Long> materialDatePicker=builder.build();
                materialDatePicker.show(getChildFragmentManager(),materialDatePicker.toString());
                materialDatePicker.setCancelable(false);
                materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDate.setEnabled(true);
                        selectDate.setClickable(true);
                    }
                });

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        selectDate.setClickable(true);
                        selectDate.setEnabled(true);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        joinDate.setText(simpleDateFormat.format(new Date(Long.parseLong(selection.toString()))));
                        System.out.println("//join Date "+joinDate.getText().toString());
                    }
                });
                break;
        }
    }
}
