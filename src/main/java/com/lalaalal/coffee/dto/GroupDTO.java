package com.lalaalal.coffee.dto;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.model.menu.Group;
import com.lalaalal.coffee.model.menu.Menu;
import lombok.Getter;

import java.util.Set;

@Getter
public class GroupDTO {
    private final String id;
    private final String name;
    private final Set<Menu> menus;

    public GroupDTO(Group group, Language language) {
        this.id = group.getId();
        this.menus = group;
        this.name = language.translate(group.getTranslationKey());
    }
}
