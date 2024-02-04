package com.lalaalal.coffee.model.menu;

import lombok.Getter;

import java.util.HashSet;

@Getter
public class Group extends HashSet<Menu> {
    private final String id;

    public Group(String id) {
        this.id = id;
    }

    public String getTranslationKey() {
        return "group." + id;
    }

    @Override
    public boolean add(Menu menu) {
        if (menu.getGroup() == this)
            return super.add(menu);
        return false;
    }

    @Override
    public String toString() {
        return id + super.toString();
    }
}
