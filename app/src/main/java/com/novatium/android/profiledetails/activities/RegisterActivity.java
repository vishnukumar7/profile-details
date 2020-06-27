package com.novatium.android.profiledetails.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.novatium.android.profiledetails.R;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText firstName, lastName, dateOfBirth, age, mobileNumber, eMailAddress, fullAddress, city, state, zipCode, designation, reportingTo, employeeId, dateOfJoin;
    RadioGroup gender, status;
    RadioButton genderChoose, statusChoose;
    MaterialButton submit;
    boolean firstNameCheck = false, lastNameCheck = false, dobCheck = false, ageCheck = false, mobileCheck = false, eMailCheck = false, fullAddressCheck = false, cityCheck = false;
    boolean stateCheck = false, zipCodeCheck = false, designationCheck = false, reportingCheck = false, employeeIdCheck = false, joinCheck = false, genderCheck = false, statusCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initReference();
        valueValidation();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void valueValidation() {
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (firstName.getText().toString().trim().isEmpty()) {
                    firstName.setError("First Name is required");
                    firstNameCheck = false;
                } else
                    firstNameCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lastName.getText().toString().trim().isEmpty()) {
                    lastName.setError("Last name is required");
                    lastNameCheck = false;
                } else
                    lastNameCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mobileNumber.getText().toString().trim().isEmpty()) {
                    mobileCheck = false;
                    mobileNumber.setError("Mobile Number is required");
                } else if (mobileNumber.getText().toString().matches("(\\+91(-)?|91(-)?|0(-)?)?(6-9)[0-9]{9}")) {
                    mobileCheck = true;
                } else {
                    mobileNumber.setError("Valid mobile number");
                    mobileCheck = false;
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (age.getText().toString().trim().isEmpty()) {
                    age.setError("Age is required");
                    ageCheck = false;
                } else if (Integer.parseInt(age.getText().toString()) <= 120) {
                    age.setError("Valid Age");
                    ageCheck = false;
                } else
                    ageCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eMailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (eMailAddress.getText().toString().trim().isEmpty()) {
                    eMailAddress.setError("E-Mail Address is required");
                    eMailCheck = false;
                } else if (eMailAddress.getText().toString().matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}")) {

                    eMailCheck = true;
                } else {
                    eMailCheck = false;
                    eMailAddress.setError("Valid Email Address");
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fullAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fullAddress.getText().toString().trim().isEmpty()) {
                    fullAddress.setError("Address is required");
                    fullAddressCheck = false;
                } else
                    fullAddressCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (state.getText().toString().trim().isEmpty()) {
                    state.setError("State is required");
                    stateCheck = false;
                } else
                    stateCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (city.getText().toString().trim().isEmpty()) {
                    city.setError("Address is required");
                    cityCheck = false;
                } else
                    cityCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (zipCode.getText().toString().trim().isEmpty()) {
                    zipCode.setError("Address is required");
                    zipCodeCheck = false;
                } else
                    zipCodeCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        designation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (designation.getText().toString().trim().isEmpty()) {
                    designation.setError("Designation is required");
                    designationCheck = false;
                } else
                    designationCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        reportingTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (reportingTo.getText().toString().trim().isEmpty()) {
                    reportingTo.setError("Report To is required");
                    reportingCheck = false;
                } else
                    reportingCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (zipCode.getText().toString().trim().isEmpty()) {
                    zipCode.setError("Address is required");
                    zipCodeCheck = false;
                } else
                    zipCodeCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (zipCode.getText().toString().trim().isEmpty()) {
                    zipCode.setError("Address is required");
                    zipCodeCheck = false;
                } else
                    zipCodeCheck = true;
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dateOfBirth.getText().toString().trim().isEmpty()) {
                    dobCheck = false;
                    dateOfBirth.setError("Date of birth is required");
                } else if (dateOfBirth.getText().toString().trim().matches("\\d{2}-\\d{2}-\\d{4}")) {
                    dobCheck = true;
                } else {
                    dobCheck = false;
                    dateOfBirth.setError("Valid Date Of Birth");
                }
                check();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateOfJoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dateOfJoin.getText().toString().trim().isEmpty()) {
                    joinCheck = false;
                    dateOfJoin.setError("Date of Join is required");
                } else if (dateOfJoin.getText().toString().trim().matches("\\d{2}-\\d{2}-\\d{4}")) {
                    joinCheck = true;
                } else {
                    joinCheck = false;
                    dateOfJoin.setError("Valid Date Of Join");
                }
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genderChoose = findViewById(checkedId);
                genderCheck = true;
            }
        });

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                statusChoose = findViewById(checkedId);
                statusCheck = true;
            }
        });
    }

    private void check() {
        if (firstNameCheck && lastNameCheck && dobCheck && ageCheck && mobileCheck && employeeIdCheck && eMailCheck && fullAddressCheck && cityCheck
                && stateCheck && statusCheck && zipCodeCheck && designationCheck && reportingCheck && joinCheck && genderCheck)
            submit.setEnabled(true);
        else
            submit.setEnabled(false);
    }

    private void initReference() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        dateOfBirth = findViewById(R.id.dobDate);
        age = findViewById(R.id.age);
        mobileNumber = findViewById(R.id.contactNumber);
        eMailAddress = findViewById(R.id.eMailAddress);
        fullAddress = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zipCode = findViewById(R.id.zipCode);
        designation = findViewById(R.id.designation);
        reportingTo = findViewById(R.id.reportingTo);
        employeeId = findViewById(R.id.employeeId);
        dateOfJoin = findViewById(R.id.dateOfJoin);
        gender = findViewById(R.id.genderGroup);
        status = findViewById(R.id.status);
        submit = findViewById(R.id.submit);
    }

}
