package com.novatium.android.profiledetails.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.RecyclerViewExpandAdapter;
import com.novatium.android.profiledetails.model.User;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    ArrayList<UserDetails> userDetailsArrayList;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        dbHandler=new DBHandler(this);
        userDetailsArrayList=dbHandler.getAllUser(null);
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator instanceof DefaultItemAnimator)
            ((DefaultItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        ArrayList<User> arrayList=dbHandler.getUser("admin");
        RecyclerViewExpandAdapter adapter = new RecyclerViewExpandAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
