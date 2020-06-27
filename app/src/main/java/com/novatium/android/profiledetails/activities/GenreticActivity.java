package com.novatium.android.profiledetails.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.button.MaterialButton;
import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.databinding.ActivityEditBinding;
import com.novatium.android.profiledetails.databinding.ActivityViewBinding;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.List;
import java.util.Objects;

public class GenreticActivity extends AppCompatActivity {

    private AppCompatSpinner spinner;
    private ActivityViewBinding viewBinding;
    private ActivityEditBinding editBinding;
    private MaterialButton submit, delete;
    private DBHandler dbHandler;
    private List<String> employeeList;
    private String type, data;
    private String personName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        data = getIntent().getStringExtra("data");
        dbHandler = new DBHandler(this);
        if (type == null) {
            finish();
        } else if (type.equalsIgnoreCase("view")) {
            viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_view);
            spinner = viewBinding.employeeSpinner;
            delete = viewBinding.deleteQuery;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteUser(personName);
                    if (data == null) {
                        employeeList = dbHandler.getAllID();
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GenreticActivity.this, android.R.layout.simple_spinner_item, employeeList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapter);
                    }
                    else
                        finish();
                }
            });
        } else if (type.equalsIgnoreCase("edit")) {
            editBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
            spinner = editBinding.employeeSpinner;
            submit = editBinding.editQuery;
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetails userDetails = new UserDetails();
                    userDetails.setAge(Integer.parseInt(Objects.requireNonNull(editBinding.editAge.getText()).toString()));
                    userDetails.setDestination(Objects.requireNonNull(editBinding.editDestination.getText()).toString());
                    userDetails.setDOB(Objects.requireNonNull(editBinding.editDob.getText()).toString());
                    userDetails.setEmailId(Objects.requireNonNull(editBinding.editEmailId.getText()).toString());
                    userDetails.setFirstName(Objects.requireNonNull(editBinding.editFirstName.getText()).toString());
                    userDetails.setGender(Objects.requireNonNull(editBinding.editGender.getText()).toString());
                    userDetails.setId(personName);
                    userDetails.setJoinDate(Objects.requireNonNull(editBinding.editJoinDate.getText()).toString());
                    userDetails.setLastName(Objects.requireNonNull(editBinding.editLastName.getText()).toString());
                    userDetails.setLocation(Objects.requireNonNull(editBinding.editLocation.getText()).toString());
                    userDetails.setMobileNumber(Long.parseLong(Objects.requireNonNull(editBinding.editMobile.getText()).toString()));
                    userDetails.setStatus(Objects.requireNonNull(editBinding.editStatus.getText()).toString());
                    userDetails.setSupervisorName(Objects.requireNonNull(editBinding.editSupervisor.getText()).toString());
                    dbHandler.updateUser(userDetails);
                }
            });
        }
        if (data == null) {
            employeeList = dbHandler.getAllID();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        } else {
            personName = data;
            UserDetails userDetails = dbHandler.getUserDetails(personName);
            if (type.equalsIgnoreCase("view")) {
                viewBinding.setUser(userDetails);
            } else if (type.equalsIgnoreCase("edit")) {
                editBinding.setUser(userDetails);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data == null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    personName = employeeList.get(position);
                    UserDetails userDetails = dbHandler.getUserDetails(personName);
                    if (type.equalsIgnoreCase("view")) {
                        viewBinding.setUser(userDetails);
                    } else if (type.equalsIgnoreCase("edit")) {
                        editBinding.setUser(userDetails);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
