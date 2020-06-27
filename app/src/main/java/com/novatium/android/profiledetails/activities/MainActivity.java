package com.novatium.android.profiledetails.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ProgressDialog progressDialog;
    AppCompatImageButton add, view, edit, screen, viewCollection;
    private RelativeLayout mainFragment, mainButton;
    private DBHandler dbHandler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);
        //addUserDefault();
        add();
        progressBar = findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Opening...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Please wait....!");
        progressDialog.setCancelable(false);
        add = findViewById(R.id.add_new_employee);
        add.setOnClickListener(this);
        view = findViewById(R.id.view_employee);
        view.setOnClickListener(this);
        edit = findViewById(R.id.edit_employee);
        edit.setOnClickListener(this);
        screen = findViewById(R.id.screen_cast);
        screen.setOnClickListener(this);
        mainButton = findViewById(R.id.mainButton);
        mainFragment = findViewById(R.id.mainFragment);
        viewCollection = findViewById(R.id.viewCollection);
        viewCollection.setOnClickListener(this);
    }

    private void add() {
        addUser(Collections.singletonList("Main"), "admin");
        addUser(Arrays.asList("Dinesh", "Subu", "Syed", "Thejo"), "Main Main");
        addUser(Arrays.asList("Vishnu", "Vel", "Harish"), "Dinesh Dinesh");
        addUser(Arrays.asList("Sriram", "Shakar"), "Thejo Thejo");
        addUser(Collections.singletonList("Prabhu"), "Shankar Shankar");
        addUser(Arrays.asList("Siva", "Roa"), "Syed Syed");
    }

    private void addUserDefault() {
        List<String> list = new ArrayList<>();
        List<String> names = Arrays.asList("sam", "vishnu", "kumar", "harish", "vel", "dinesh", "vindiesel", "walker",
                "ajith", "vijay", "surya", "jeeva", "prabhu", "siva", "shankar", "sriram", "syed", "subu", "vardha", "rao");
        String superVisor;
        for (int i = 0; i < 10; i++) {
            UserDetails userDetails = new UserDetails();
            userDetails.setId("" + i);
            userDetails.setFirstName(getRandom(names));
            userDetails.setLastName(getRandom(names));
            userDetails.setDestination("IT");
            if (list.size() == 0) {
                superVisor = "Admin";
                userDetails.setSupervisorName("Admin");
            } else {
                userDetails.setSupervisorName(getRandom(list));
                superVisor = userDetails.getFirstName() + " " + userDetails.getLastName();
            }
            userDetails.setStatus("active");
            userDetails.setMobileNumber(Long.parseLong("9999988888"));
            userDetails.setLocation("Chennai");
            userDetails.setJoinDate("01/01/2019");
            userDetails.setGender("male");
            userDetails.setEmailId("mail" + i + "@gmail.com");
            userDetails.setDOB("01/01/1990");
            userDetails.setAge(30);
            list.add(superVisor);
            dbHandler.insertPage(userDetails);
        }
    }

    private void addUser(List<String> user, String superVisor) {
        for (String userString : user) {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(userString);
            userDetails.setFirstName(userString);
            userDetails.setLastName(userString);
            userDetails.setDestination("IT");
            userDetails.setSupervisorName(superVisor);
            userDetails.setStatus("active");
            userDetails.setMobileNumber(Long.parseLong("9999988888"));
            userDetails.setLocation("Chennai");
            userDetails.setJoinDate("01/01/2019");
            userDetails.setGender("male");
            userDetails.setEmailId("mail" + userString + "@gmail.com");
            userDetails.setDOB("01/01/1990");
            userDetails.setAge(30);
            dbHandler.insertPage(userDetails);
        }
    }

    private String getRandom(List<String> getList) {
        Random random = new Random();
        return getList.get(random.nextInt(getList.size()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.add_new_employee:
            //    progressDialog.show();
              /*  mainButton.setVisibility(View.GONE);
                mainFragment.setVisibility(View.VISIBLE);
                RegistrationPageFragment fragment = new RegistrationPageFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.view_employee:
                intent = new Intent(MainActivity.this, GenreticActivity.class);
                intent.putExtra("type", "view");
                startActivity(intent);
                break;
            case R.id.edit_employee:
                intent = new Intent(MainActivity.this, GenreticActivity.class);
                intent.putExtra("type", "edit");
                startActivity(intent);
                break;
            case R.id.screen_cast:
                startActivity(new Intent(MainActivity.this, ScreenCastActivity.class));
                break;
            case R.id.viewCollection:
                intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                intent.putExtra("type", "viewCollection");
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainButton.setVisibility(View.VISIBLE);
        mainFragment.setVisibility(View.GONE);
    }
}
