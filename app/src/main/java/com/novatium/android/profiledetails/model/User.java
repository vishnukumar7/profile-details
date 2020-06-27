package com.novatium.android.profiledetails.model;


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class User extends ExpandableGroup {
    private String id;

    public User(String title, List items, String id) {
        super(title, items);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
