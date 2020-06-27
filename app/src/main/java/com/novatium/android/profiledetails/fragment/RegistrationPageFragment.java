package com.novatium.android.profiledetails.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.activities.MainActivity;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.model.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistrationPageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private InputMethodManager inputMethodManager;
    private TextInputEditText id, firstName, lastName, age, dobDate, city, destination;
    private RadioGroup gender;
    private DBHandler dbHandler;
    private MaterialButton selectDate;

    public RegistrationPageFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration_page, container, false);
        initViews();
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.progressDialog.dismiss();
    }

    private void initViews() {
        inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        id = view.findViewById(R.id.employeeId);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        age = view.findViewById(R.id.age);
        dobDate = view.findViewById(R.id.dobDate);
        dobDate.setFocusable(false);
        city = view.findViewById(R.id.location);
        destination = view.findViewById(R.id.destination);
        gender = view.findViewById(R.id.gender);
        MaterialButton submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        selectDate = view.findViewById(R.id.dobDateButton);
        selectDate.setOnClickListener(this);
        dbHandler = new DBHandler(requireContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (check()) {
                    UserDetails userDetails = new UserDetails();
                    userDetails.setId(id.getText().toString());
                    userDetails.setFirstName(firstName.getText().toString());
                    userDetails.setLastName(lastName.getText().toString());
                    userDetails.setAge(Integer.parseInt(age.getText().toString()));
                    userDetails.setLocation(city.getText().toString());
                    userDetails.setDestination(destination.getText().toString());
                    userDetails.setDOB(dobDate.getText().toString());
                    RadioButton choose = view.findViewById(gender.getCheckedRadioButtonId());
                    userDetails.setGender(choose.getText().toString());
                    System.out.println("//join Date " + dobDate.getText().toString());
                    if (dbHandler.getCheckId(userDetails.getId())) {
                        RegistrationPageFragment2 fragment2 = new RegistrationPageFragment2(userDetails);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment2);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        Toast.makeText(requireContext(), "Employee ID already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.dobDateButton:
                selectDate.setEnabled(false);
                selectDate.setClickable(false);
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);

                MaterialDatePicker<Long> materialDatePicker = builder.build();
                materialDatePicker.show(getChildFragmentManager(), materialDatePicker.toString());
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
                        selectDate.setEnabled(true);
                        selectDate.setClickable(true);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dobDate.setText(simpleDateFormat.format(new Date(Long.parseLong(selection.toString()))));
                        System.out.println("//join Date " + dobDate.getText().toString());
                    }

                });
        }
    }

    private boolean check() {
        boolean flag = true;
        if (TextUtils.isEmpty(id.getText())) {
            id.setError("Employee Id Required");
            flag = false;
        }

        if (TextUtils.isEmpty(firstName.getText())) {
            firstName.setError("First Name Required");
            flag = false;
        }

        if (TextUtils.isEmpty(lastName.getText())) {
            lastName.setError("Last Name Required");
            flag = false;
        }

        if (TextUtils.isEmpty(city.getText())) {
            city.setError("City Required");
            flag = false;
        }

        if (TextUtils.isEmpty(destination.getText())) {
            destination.setError("Destination Required");
            flag = false;
        }

        if (TextUtils.isEmpty(dobDate.getText())) {
            dobDate.setError("Date Of Birth Required");
            flag = false;
        }

        if (TextUtils.isEmpty(age.getText())) {
            age.setError("Age Required");
            flag = false;
        }

        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(requireContext(), " Select gender", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        return flag;
    }
}
