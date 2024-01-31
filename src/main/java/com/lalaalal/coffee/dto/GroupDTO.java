package com.lalaalal.coffee.dto;

import com.lalaalal.coffee.model.menu.Group;
import com.lalaalal.coffee.model.menu.Menu;
import lombok.Getter;

import java.util.Set;

public class GroupDTO {
    @Getter
    public final int priority;
    public final String id;
    public final String name;
    public final Set<Menu> menus;

    public GroupDTO(Group group) {
        this.priority = group.getPriority();
        this.id = group.getId();
        this.menus = group;
        this.name = group.getTranslationKey();
    }
}
