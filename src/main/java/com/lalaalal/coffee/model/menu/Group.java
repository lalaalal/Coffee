package com.lalaalal.coffee.model.menu;

import lombok.Getter;

import java.util.HashSet;

@Getter
public class Group extends HashSet<Menu> {
    private final String id;

    private final int priority;

    public Group(String id, int priority) {
        this.id = id;
        this.priority = priority;
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
