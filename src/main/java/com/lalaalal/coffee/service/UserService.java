package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.registry.LanguageRegistry;
import com.lalaalal.coffee.registry.Registries;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public Language getLanguage() {
        // TODO: 12/16/23 handle user language
        return Registries.get(LanguageRegistry.class, "ko");
    }
}
