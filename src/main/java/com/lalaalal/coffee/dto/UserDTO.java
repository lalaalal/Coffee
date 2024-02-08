package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.Permission;
import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.model.User;
import com.lalaalal.coffee.registry.LanguageRegistry;
import com.lalaalal.coffee.registry.PermissionRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    public static final UserDTO ANONYMOUS = new UserDTO("anonymous", Configurations.getConfiguration("language.default"), "none");

    @JsonProperty("id")
    private final String id;
    @JsonIgnore
    private final Language language;
    @JsonIgnore
    private final Permission permission;

    @JsonCreator
    public UserDTO(@JsonProperty("id") String id,
                   @JsonProperty("language") String language,
                   @JsonProperty("permission") String permission) {
        this.id = id;
        this.language = Registries.get(LanguageRegistry.class, language);
        this.permission = Registries.get(PermissionRegistry.class, permission);
    }

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getLanguage(), user.getPermission());
    }

    @JsonProperty("language")
    public String getLanguageName() {
        return language.getLanguage();
    }
}
