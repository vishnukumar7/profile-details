package com.novatium.android.profiledetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.novatium.android.profiledetails.databinding.ViewCollectionListBinding;
import com.novatium.android.profiledetails.model.UserDetails;

import java.util.ArrayList;

public class ViewCollectionAdapter extends RecyclerView.Adapter<ViewCollectionAdapter.ViewHolder> {
    private ArrayList<UserDetails> userDetailsArrayList;
    private OnClickViewButton clickViewButton;

    public ViewCollectionAdapter(ArrayList<UserDetails> userDetailsArrayList, OnClickViewButton clickViewButton) {
        this.userDetailsArrayList = userDetailsArrayList;
        this.clickViewButton = clickViewButton;
    }

    public void setUserDetails(ArrayList<UserDetails> userDetailsArrayList) {
        this.userDetailsArrayList = userDetailsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewCollectionListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_collection_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserDetails userDetails = userDetailsArrayList.get(position);
        System.out.println("//user details : " + userDetails.getFirstName());

        holder.binding.setUser(userDetails);
        holder.binding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickViewButton.onDetailView(userDetails);
            }
        });
        holder.binding.subGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickViewButton.onOpenView(userDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDetailsArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewCollectionListBinding binding;

        public ViewHolder(@NonNull ViewCollectionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
