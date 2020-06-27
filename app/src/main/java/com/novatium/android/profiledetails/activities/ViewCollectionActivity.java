package com.novatium.android.profiledetails.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novatium.android.profiledetails.DBHandler;
import com.novatium.android.profiledetails.OnClickViewButton;
import com.novatium.android.profiledetails.R;
import com.novatium.android.profiledetails.ViewCollectionAdapter;
import com.novatium.android.profiledetails.databinding.ActivityViewCollectionBinding;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCollectionActivity extends AppCompatActivity implements OnClickViewButton {

    private RecyclerView recyclerView;
    private DBHandler dbHandler;
    private ViewCollectionAdapter adapter;
    private HashMap<Integer, String> pageList;
    private int last_page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewCollectionBinding viewCollectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_collection);
        dbHandler = new DBHandler(this);
        pageList = new HashMap<>();
        recyclerView = viewCollectionBinding.viewCollectionRecyclerView;
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<UserDetails> arrayList = dbHandler.getAllUser(null);
        System.out.println("//size : " + arrayList.size());
        adapter = new ViewCollectionAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOpenView(Object object) {
        UserDetails userDetails = (UserDetails) object;
        if (last_page == 0)
            pageList.put(last_page, null);
        else
            pageList.put(last_page, userDetails.getFirstName());
        last_page++;
        adapter.setUserDetails(dbHandler.getAllUser(userDetails.getFirstName()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetailView(Object object) {
        UserDetails userDetails = (UserDetails) object;
        Intent intent = new Intent(ViewCollectionActivity.this, GenreticActivity.class);
        intent.putExtra("type", "view");
        intent.putExtra("data", userDetails.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(last_page==-1){
            super.onBackPressed();
        }
        else{
            adapter.setUserDetails(dbHandler.getAllUser(pageList.get(last_page)));
            recyclerView.setAdapter(adapter);
            last_page--;
        }
    }
}
