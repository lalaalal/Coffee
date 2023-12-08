package com.lalaalal.coffee.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;

@Getter
@AllArgsConstructor
public class Group extends HashSet<Menu> {
    private String id;

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
