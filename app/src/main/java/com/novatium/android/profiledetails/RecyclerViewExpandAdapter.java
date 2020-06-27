package com.novatium.android.profiledetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.novatium.android.profiledetails.activities.GenreticActivity;
import com.novatium.android.profiledetails.model.User;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class RecyclerViewExpandAdapter extends ExpandableRecyclerViewAdapter<RecyclerViewExpandAdapter.GroupView, RecyclerViewExpandAdapter.ChildView> {

    private Context context;

    public RecyclerViewExpandAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public GroupView onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new GroupView(LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_first, parent, false));
    }

    @Override
    public ChildView onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildView(LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_recycler_view, parent, false));
    }

    @Override
    public void onBindChildViewHolder(ChildView holder, int flatPosition, ExpandableGroup group, int childIndex) {
        RecyclerView recyclerView = holder.recyclerView;
        final String[] name = group.getTitle().split(",");
        System.out.println("//group "+group.getTitle());
        List<User> childUserList = new DBHandler(context).getUser(name[0]);
        RecyclerViewExpandAdapter adapter = new RecyclerViewExpandAdapter(context, childUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBindGroupViewHolder(GroupView holder, final int flatPosition, ExpandableGroup group) {
        final String[] name = group.getTitle().split(",");
        holder.first.setText(name[0]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GenreticActivity.class);
                intent.putExtra("type", "view");
                intent.putExtra("data", name[1]);
                context.startActivity(intent);
            }
        });
    }

    static class GroupView extends GroupViewHolder {
        MaterialTextView first;
        MaterialButton view, childExpand;

        public GroupView(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.textViewFirst);
            view = itemView.findViewById(R.id.viewData);
            childExpand = itemView.findViewById(R.id.childView);
            childExpand.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.childView) {
                super.onClick(v);
                if (childExpand.getText().toString().equalsIgnoreCase("expand")) {
                    childExpand.setText("Collapse");
                } else {
                    childExpand.setText("Expand");
                }
            }
        }
    }

    static class ChildView extends ChildViewHolder {
        RecyclerView recyclerView;

        public ChildView(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.expandRecyclerView);
        }
    }


}
