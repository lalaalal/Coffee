package com.lalaalal.coffee.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.misc.SHA256;
import com.lalaalal.coffee.registry.LanguageRegistry;
import com.lalaalal.coffee.registry.PermissionRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIncludeProperties({"id", "hashed_password", "language", "permission"})
public class User {
    @JsonProperty("id")
    private String id;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    private Language language;
    private Permission permission;

    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("hashed_password") String hashedPassword,
                @JsonProperty("language") String language,
                @JsonProperty("permission") String permission) {
        this.id = id;
        this.hashedPassword = hashedPassword;
        this.language = Registries.get(LanguageRegistry.class, language);
        this.permission = Registries.get(PermissionRegistry.class, permission);
    }

    @JsonProperty("language")
    public String getLanguageCode() {
        return language.getLanguage();
    }

    @JsonProperty("permission")
    public String getPermissionName() {
        return permission.getName();
    }

    public boolean verify(String password) {
        return SHA256.encrypt(password).equals(hashedPassword);
    }
}
